package org.apache.visco.tests;
//package visco.core.merge;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.visco.helperClasses.MockInteger;
import org.apache.visco.helperClasses.MockReporter;
import org.apache.visco.helperClasses.MockString;
import org.junit.Before;
import org.junit.Test;

import visco.core.merge.IOChannelBuffer;
import visco.core.merge.MemoryIOChannel;
import visco.core.merge.MergingTask;


////////////////////////////////////////////////////////////////////
//Remember to try setting up the objects either in setUp,
//or in the respective test itself, since for some unknown reason
//they may work in one location but not the other. 
////////////////////////////////////////////////////////////////////


public class TestMergingTask {
	public MockString hello = new MockString("Hello");
	public MockInteger one = new MockInteger(1);
	public MockInteger two = new MockInteger(1);

	public Executor threadPool;
	public MockReporter reporter;
	
	public MergingTask<MockString, MockInteger> realMergingTask;
	
	MemoryIOChannel<MockString,MockInteger> mockMemoryIOChannel = mock(MemoryIOChannel.class);
		
	@Before
	public void setUp() throws Exception {
		threadPool = Executors.newCachedThreadPool();
		reporter = new MockReporter();
		
		//Mocks and stubs
		when( mockMemoryIOChannel.GetEmpty(null) ).thenReturn(
				new IOChannelBuffer<MockString, MockInteger>(2, null) );
		
		//Construct the MergingTask object
		
		realMergingTask = new MergingTask<MockString, MockInteger>(null, null, null, threadPool, null, reporter, false); 
		
    }
	
	@Test
	public void testRun() {
		//realMergingTask.run();
	}

}

