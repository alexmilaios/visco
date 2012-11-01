package org.apache.visco.tests;
//package visco.core.merge;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.examples.WordCount;
import org.apache.hadoop.mapred.JobConf;
import org.apache.visco.helperClasses.MockReporter;
import org.junit.Before;
import org.junit.Test;

////////////////////////////////////////////////////////////////////
//Remember to try setting up the objects either in setUp,
//or in the respective test itself, since for some unknown reason
//they may work in one location but not the other. 
////////////////////////////////////////////////////////////////////


public class TestFinalOutputChannel {
	
	public JobConf jobConf;
	public MockReporter reporter;

	@Before
	public void setUp() throws Exception {
		
		reporter = new MockReporter();
		jobConf = new JobConf(new Configuration(), WordCount.class);
		
    }
	
	@Test
	public void someTest() {
	}

}

