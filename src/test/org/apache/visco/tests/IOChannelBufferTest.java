package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import helperClasses.MockInteger;
import helperClasses.MockString;

import java.util.ArrayList;

import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import visco.core.merge.IOChannelBuffer;


////////////////////////////////////////////////////////////////////
//Remember to try setting up the objects either in setUp,
//or in the respective test itself, since for some unknown reason
//they may work in one location but not the other. 
////////////////////////////////////////////////////////////////////


public class IOChannelBufferTest {
	//mockPath created to verify that Mockito works
	Path mockPath = mock(Path.class);

	//create real working IOChannelBuffers to test on.
	//MockString and MockInteger are two classes which are basic implementations of WritableComparables, class definitions are below
	IOChannelBuffer<MockString, MockInteger> realIOChannelBufferOne;
	IOChannelBuffer<MockString, MockInteger> realIOChannelBufferTwo;
	IOChannelBuffer<MockString, MockInteger> realIOChannelBufferThree;
	IOChannelBuffer<MockString, MockInteger> realIOChannelBufferFour;
	IOChannelBuffer<MockString, MockInteger> realIOChannelBufferFive;
	IOChannelBuffer<MockString, MockInteger> realIOChannelBufferFalse;
	
	//declare variables to setup IOChannelBuffer, they will be defined in setUp()
	//they need to be setup here to be in scope for the following methods
	//(cannot declare them in setUp())
	MockString mockString;
	MockInteger one;
	MockInteger two;
	ArrayList<MockInteger> mockIntegers;
	
	@Before
	public void setUp() throws Exception {
		//for mockito
		when( mockPath.toString() ).thenReturn("Working?");
		
		//create MockString as Key
		mockString = new MockString("Hello");
		//create MockIntegers as Values
		one = new MockInteger(1); 
		two = new MockInteger(2);
		//create an array to hold MockIntegers 
		mockIntegers = new ArrayList<MockInteger>();
		//add MockIntegers to this array
		mockIntegers.add(one);
		mockIntegers.add(two);
		
		realIOChannelBufferOne = new IOChannelBuffer<MockString, MockInteger>(2);
		realIOChannelBufferTwo = new IOChannelBuffer<MockString, MockInteger>(2);
		realIOChannelBufferThree = new IOChannelBuffer<MockString, MockInteger>(2);
		realIOChannelBufferFour = new IOChannelBuffer<MockString, MockInteger>(2);
		realIOChannelBufferFive = new IOChannelBuffer<MockString, MockInteger>(2);
		realIOChannelBufferFalse = new IOChannelBuffer<MockString, MockInteger>(2);
		
	}
	
	///////////////////////////////////////////////////////////////
	//Test methods
	///////////////////////////////////////////////////////////////
	@Test
	public void testPath() {
		//for mockito
		assertEquals(mockPath.toString(), "Working?");
	}
		
	@Test
	public void testIOChannelBufferAddKeyValuesAndPeekKey() {
		//Test member AddKeyValues() by adding our key and values 
		realIOChannelBufferOne.AddKeyValues(mockString, mockIntegers);
		//expectedKey is what we expect the key to be inside our IOChannelBuffer
		MockString expectedKey = new MockString("Hello");
		//actualKey is what is actually inside our IOChannelBuffer
		MockString actualKey = realIOChannelBufferOne.peekKey();
		
		//compare expectedKey with actualKey, if they are the same, test will past.
		assertEquals(expectedKey.get() , actualKey.get() );
		//or use assertEquals("Hello", actualKey.get() );
		
		//System.out.println("Testing assertEquals(): " + expectedKey.get() + " " + actualKey.get() );
	}
	
	@Test
	public void testIOChannelBufferRemoveKeyAndRemoveValues() {
		//AddKeyValues not as a test, but to populate IOChannelBuffer, so that we can test removeKey()
		realIOChannelBufferTwo.AddKeyValues(mockString, mockIntegers);
		
		//removeKey() removes top most Key from IOChannelBuffer and returns it
		//we assign the returned key to a MockString called returnKey
		MockString returnKey = realIOChannelBufferTwo.removeKey();
		
		//removeValues() removes top most Values from IOChannelBuffer and returns them as an ArrayList
		//we assign the returned Values to returnValues
		ArrayList<MockInteger> returnValues = realIOChannelBufferTwo.removeValues();
		
		//test that objects are exist
		assertTrue(returnValues != null);
		assertTrue(returnKey != null);
		
		//test that the key we removed is as expected
		assertEquals("Hello", returnKey.get());
		//test that the values ArrayList is as expected
		assertEquals(2, returnValues.size());
		//test aluesthe value of each 'value' in the array of values.
		assertTrue(1 == returnValues.get(0).get());
		assertTrue(2 == returnValues.get(1).get());		
	}
	
	@Test 
	public void testIOChannelBufferSizeAndHasRemaining() {
		//AddKeyValues not as a test, but to populate IOChannelBuffer, so that we can test other methods
		realIOChannelBufferThree.AddKeyValues(mockString, mockIntegers);
		
		//size() method returns size of 'keys' which is a member of IOChannelBuffer, should be 1
		int expectedSize = 1;
		int actualSize = realIOChannelBufferThree.size();
		assertEquals(expectedSize, actualSize);
		
		//hasRemaining() returns a bool ( this.keys.size() < this.capacity() ) 
		//test to see if keys.size() (which is 1) is less than capacity(which is 2)
		//should return true
		assertEquals(true, realIOChannelBufferThree.hasRemaining() );
					
		//add one more key and value, hasRemaining should return false
		//since (2 < 2) does not hold... hence capacity is reached
		MockString mockString2 = new MockString("World");
		MockInteger three = new MockInteger(3);
		ArrayList<MockInteger> mockIntegers2 = new ArrayList<MockInteger>();
		mockIntegers2.add(three);
		realIOChannelBufferThree.AddKeyValues(mockString2, mockIntegers2);
		assertEquals(false, realIOChannelBufferThree.hasRemaining() );		
	}
	
	@Test 
	public void testIOChannelBufferClear() {
		//AddKeyValues not as a test, but to populate IOChannelBuffer, so that we can test other methods
		realIOChannelBufferFour.AddKeyValues(mockString, mockIntegers);
		
		//Check to see that number of keys is 1.
		assertEquals(1, realIOChannelBufferFour.size());
		
		//Clear buffer
		realIOChannelBufferFour.clear();
		
		//Check to see if number of keys is now 0. 
		assertEquals(0, realIOChannelBufferFour.size());
	}
	
	@Test
	public void testIOChannelBufferToString(){
		String expectedString = "Hello [1, 2]"+"\n"; 
		
		realIOChannelBufferFive.AddKeyValues(mockString, mockIntegers);
		//System.out.println(realIOChannelBufferFive.toString());	
		
		String actualString = realIOChannelBufferFive.toString();
		assertEquals(expectedString, actualString);
		
	}
	
	// not currently testing asserts, just null pointer exceptions
	/*@Test
	public void testAsserts(){
			
			
		try{
			MockString falseString = null;
			realIOChannelBufferFalse.AddKeyValues(falseString, mockIntegers);
			realIOChannelBufferFalse.peekKey();
		}
		
		catch(NullPointerException expected){
			
			assertEquals(NullPointerException.class, expected.getClass());  
				
		}

	}
	
	*/
	
}
