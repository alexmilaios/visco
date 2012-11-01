package org.apache.visco.tests;

import static org.junit.Assert.assertEquals;
import org.apache.visco.helperClasses.MockInteger;
import org.apache.visco.helperClasses.MockString;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import visco.core.merge.MergingTask;
import visco.core.merge.NonBlockingQueue;
import visco.util.ActionDelegate;
import visco.util.ModifiableBoolean;

public class TestNonBlockingQueue {

	public ActionDelegate enqueueMaybePossible;
	public ActionDelegate dequeueMaybePossible;
	public MergingTask<MockString, MockInteger> child;
	public int maxItems = 10;
	public NonBlockingQueue<MockInteger> myNBQ;
	public NonBlockingQueue<MockInteger> brokenNBQ;

	public MockInteger item = new MockInteger(0);

	public Executor threadPool;

	public ModifiableBoolean result;

	@Before
	public void setUp() throws Exception {

		result = new ModifiableBoolean();
		threadPool = Executors.newCachedThreadPool();

		child = new MergingTask<MockString, MockInteger>(null, null, null,
				threadPool, null, null, false);

		enqueueMaybePossible = new ActionDelegate() {

			@Override
			public void action() {
				//System.out.println("enqueue is possible");
			}
		};

		dequeueMaybePossible = new ActionDelegate() {

			@Override
			public void action() {
				//System.out.println("dequeue is possible");
			}
		};

		myNBQ = new NonBlockingQueue<MockInteger>(maxItems,
				enqueueMaybePossible, dequeueMaybePossible);

	}

	/*
	 * NonBlock: test with nitems < maxItems
	 */
	@Test
	public void testEnqueue0() {

		boolean expectedBoolean = true;

		// nitems = 8
		for (int i = 0; i < maxItems - 1; i++) {
			myNBQ.Enqueue(item);
		}

		// nitems = 9
		boolean actualBoolean = myNBQ.Enqueue(item);

		// return true because non block waiting since there is space in the
		// queue
		assertEquals(expectedBoolean, actualBoolean);
	}

	/*
	 * Block: test with nitems = maxItems
	 */
	@Test
	public void testEnqueue1() {

		boolean expectedBoolean = false;

		// nitems = 9
		for (int i = 0; i < maxItems; i++) {
			myNBQ.Enqueue(item);
		}

		// nitems = 10
		boolean actualBoolean = myNBQ.Enqueue(item);

		// return false because block waiting for space in the queue
		assertEquals(expectedBoolean, actualBoolean);
	}

	/*
	 * Block: test with nitems > maxItems
	 */
	@Test
	public void testEnqueue2() {

		boolean expectedBoolean = false;

		// nitems = 10
		for (int i = 0; i < maxItems + 1; i++) {
			myNBQ.Enqueue(item);
		}

		// nitems = 11
		boolean actualBoolean = myNBQ.Enqueue(item);

		// return false because block waiting for space in the queue
		assertEquals(expectedBoolean, actualBoolean);
	}

	/*
	 * Block: nitems
	 */
	@Test
	public void testDequeue0() {

		MockInteger actualItem;
		MockInteger expectedItem = null;

		actualItem = myNBQ.Dequeue(result);
		assertEquals(expectedItem, actualItem);
	}

	/*
	 * 
	 */
	@Test
	public void testDequeue1() {

		// nitems = 1
		myNBQ.Enqueue(item);

		MockInteger actualItem;
		MockInteger expectedItem = item;

		actualItem = myNBQ.Dequeue(result);
		assertEquals(expectedItem, actualItem);
	}

	/*
	 * 
	 */
	@Test
	public void testDequeue2() {

		// nitems = 10
		for (int i = 0; i < maxItems + 1; i++) {
			myNBQ.Enqueue(item);
		}

		MockInteger actualItem;
		MockInteger expectedItem = item;

		actualItem = myNBQ.Dequeue(result);
		assertEquals(expectedItem, actualItem);

	}

	@Test
	public void testCount() {

		int expectedNItems = 1;
		myNBQ.Enqueue(item);

		int actualNItems = myNBQ.Count();

		// return false because block waiting for space in the queue
		assertEquals(expectedNItems, actualNItems);

	}

	/*
	 * Catch maxItems <= 0 exception error
	 */
	@Test
	public void testBrokenNBQMaxItems() {

		try {
			brokenNBQ = new NonBlockingQueue<MockInteger>(-1,
					enqueueMaybePossible, dequeueMaybePossible);
		} catch (IllegalArgumentException e) {
		}

	}

	/*
	 * Catch enqueueMaybePossible = null
	 */
	@Test
	public void testBrokenNBQenqueue() {

		try {
			brokenNBQ = new NonBlockingQueue<MockInteger>(maxItems, null,
					dequeueMaybePossible);
		} catch (IllegalArgumentException e) {
		}

	}

	/*
	 * Catch dequeueMaybePossible = null
	 */
	@Test
	public void testBrokenNBQdequeu() {

		try {
			brokenNBQ = new NonBlockingQueue<MockInteger>(maxItems,
					enqueueMaybePossible, null);
		} catch (IllegalArgumentException e) {
		}

	}
}
