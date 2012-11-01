package tests;

//package visco.core.merge;

import static org.junit.Assert.*;

import helperClasses.*;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.junit.Before;
import org.junit.Test;

import visco.core.merge.IOChannelBuffer;
import visco.core.merge.MemoryIOChannel;
import visco.core.merge.NonBlockingQueue;
import visco.util.*;

public class MemoryIOChannelTest {

	public IOChannelBuffer<MockString, MockInteger> item = new IOChannelBuffer<MockString, MockInteger>(
			1);

	public Executor threadPool;
	public JobConf jobConf; 
	public MockReporter reporter;
	public int maxItems = 10;

	public TaskAttemptID taskId;
	public ActionDelegate consumerUnblocked;
	public ActionDelegate producerUnblocked;
	public ActionDelegate NOP;

	public MemoryIOChannel<MockString, MockInteger> testMemIOChannel;

	public FuncDelegate<IOChannelBuffer<MockString, MockInteger>> teeSource;
	public NonBlockingQueue<IOChannelBuffer<MockString, MockInteger>> tx;
	public NonBlockingQueue<IOChannelBuffer<MockString, MockInteger>> rx;

	public ModifiableBoolean testMB = new ModifiableBoolean();

	public MockString mockString;
	public MockInteger one;
	public MockInteger two;
	public ArrayList<MockInteger> mockIntegers;

	@Before
	public void setUp() throws Exception {

		MockJobConf jobConfGenerator = new MockJobConf();

		jobConf = jobConfGenerator.returnNewJobConf();

		reporter = new MockReporter();

		threadPool = Executors.newCachedThreadPool();
		
		taskId = new TaskAttemptID();

		// create MockString as Key
		mockString = new MockString("Hello");
		// create MockIntegers as Values
		one = new MockInteger(1);
		two = new MockInteger(2);
		// create an array to hold MockIntegers
		mockIntegers = new ArrayList<MockInteger>();
		// add MockIntegers to this array
		mockIntegers.add(one);
		mockIntegers.add(two);

		item.AddKeyValues(mockString, mockIntegers);

		consumerUnblocked = new ActionDelegate() {

			@Override
			public void action() {
				// System.out.println("consumer is unblocked");
			}
		};

		producerUnblocked = new ActionDelegate() {

			@Override
			public void action() {
				// System.out.println("produced is unblocked");
			}
		};

		NOP = new ActionDelegate() {

			@Override
			public void action() {
				// nothing goes here
			}
		};

		teeSource = new FuncDelegate<IOChannelBuffer<MockString, MockInteger>>() {

			@Override
			public IOChannelBuffer<MockString, MockInteger> Func() {
				return new IOChannelBuffer<MockString, MockInteger>(10);
			}

		};

		tx = new NonBlockingQueue<IOChannelBuffer<MockString, MockInteger>>(
				maxItems, NOP, consumerUnblocked);

		rx = new NonBlockingQueue<IOChannelBuffer<MockString, MockInteger>>(
				maxItems, NOP, consumerUnblocked);

		testMemIOChannel = new MemoryIOChannel<MockString, MockInteger>(
				jobConf, taskId, maxItems, teeSource, producerUnblocked,
				consumerUnblocked, reporter);
					
	}

	@Test
	public void testGetEmpty() {

		IOChannelBuffer<MockString, MockInteger> actualItem;
	
		for (int i = 0; i < maxItems; i++)
			testMemIOChannel.setRXDequeue(testMB);
		
		actualItem = testMemIOChannel.GetEmpty(testMB);
		assertFalse(testMB.value);
		
		assertNull(actualItem);
		
	}

	@Test
	public void testSend() {

		Throwable re = null;
		Throwable re2 = null;

		try {
			testMemIOChannel.Send(item);
		} catch (Exception e) {
			re = e;
		}

		assertNull(re);

		try {

			for (int i = 0; i < maxItems; i++) {
				testMemIOChannel.setTXEnqueue(item);
			}
			testMemIOChannel.Send(item);

		} catch (Exception e) {
			re2 = e;
		}

		assertTrue(re2 instanceof Exception);

	}

	@Test
	public void testReceive() {

		IOChannelBuffer<MockString, MockInteger> actualItem;
		IOChannelBuffer<MockString, MockInteger> expectedItem = item;
		
		/*
		 * Try with MB value set to false
		 */
		
		// Add something to tx non-blocking queue
		testMemIOChannel.setTXEnqueue(item);		
		
		actualItem = testMemIOChannel.Receive(testMB);	
		assertEquals(expectedItem, actualItem);
		
		/*
		 * Try with MB value set to true
		 */
		testMB.value = false;
		actualItem = testMemIOChannel.Receive(testMB);
		assertFalse(testMB.value); // test the value has been left as false
		assertNull(actualItem); // should return null
		
		/*
		 * Try with isClosed value set to false
		 */
		testMemIOChannel.Close();
		
		actualItem = testMemIOChannel.Receive(testMB); //set return to actualItem
		
		assertTrue(testMB.value); // test the value has also been changed to true
		assertNull(actualItem);  // should return null	
		
	}

	@Test
	public void testRelease() {

		/*
		 * Test for successful enqueue on rx buffer for Release function
		 */

		Throwable re = null;
		Throwable re2 = null;

		try {
			// System.out.println(testMemIOChannel.rx.nitems);
			testMemIOChannel.Release(item);
		} catch (Throwable e) {
			// System.out.println("Caught the Runtime Exception");
			re = e;
		}

		assertTrue(re instanceof RuntimeException);

		try {
			// Rest should run fine
			for (int i = 0; i < maxItems; i++) {
				testMemIOChannel.setRXDequeue(testMB);
			}

			// nitems is a private variable, need to change to print
			// System.out.println(testMemIOChannel.rx.nitems);

			testMemIOChannel.Release(item);

		} catch (Throwable e2) {
			re2 = e2;
		}

		assertNull(re2);
	}

	@Test
	public void testClose() {

		boolean actualClosed;
		boolean expectedClosed;

		/*
		 * Check default closed value of MemoryIOChannel
		 */
		actualClosed = testMemIOChannel.getIsClosed();
		expectedClosed = false;

		assertEquals(expectedClosed, actualClosed);

		/*
		 * Test the other way around now after we've modified it
		 */

		testMemIOChannel.Close();
		actualClosed = testMemIOChannel.getIsClosed();

		expectedClosed = true;
		assertEquals(expectedClosed, actualClosed);

	}

}
