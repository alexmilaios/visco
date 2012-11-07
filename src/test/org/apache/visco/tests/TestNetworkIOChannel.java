package org.apache.visco.tests;

import static org.junit.Assert.fail;

import javax.crypto.SecretKey;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapred.Counters.Counter;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.ReduceTask;
import org.apache.hadoop.mapred.ReduceTask.ReduceCopier.MapOutputLocation;
import org.apache.visco.helperClasses.MockInteger;
import org.apache.visco.helperClasses.MockReporter;
import org.apache.visco.helperClasses.MockString;
import org.apache.visco.helperClasses.WordCount;
import org.junit.Before;
import org.junit.Test;

import visco.core.merge.NetworkIOChannel;
//import WordCount;
//import WordCount.MapClass;
//import WordCount.Reduce;

public class TestNetworkIOChannel {
	
	/* Constructor arguments */
	public JobConf jobConf;
	public ReduceTask.ReduceCopier<MockString, MockInteger> reduceCopier;
	public SecretKey jobTokenSecret;
	public CompressionCodec codec;
	public Counter counter;
	public int reduce;
	public MockReporter reporter; 
	

	@Before
	public void setUp() throws Exception {
		
		//jobConf
		jobConf = new JobConf(new Configuration(), WordCount.class);
		jobConf.setJobName("wordcount");
		jobConf.setOutputKeyClass(Text.class);
		jobConf.setOutputValueClass(IntWritable.class);
	    jobConf.setMapperClass(WordCount.MapClass.class);        
	    jobConf.setCombinerClass(WordCount.Reduce.class);
	    jobConf.setReducerClass(WordCount.Reduce.class);
	    
	    //inputLocation
	    //inputLocation = new reduceCopier.MapOutputLocation(null, null, null);
			
	    //jobTokenSecret
		
		//codec
		
		//counter
	    
	    //reduce
	    reduce = 0;
	    
		//reporter
		reporter = new MockReporter();
		
		MapOutputLocation inputLocation = null;
		//Construct our DiskToIOChannel!!!
		//realNetworkIOChannel = 
		new NetworkIOChannel(jobConf, inputLocation, jobTokenSecret, codec, counter, reduce, reporter);
		
		/*
		NetworkIOChannel(JobConf jobConf, MapOutputLocation inputLocation,
				SecretKey jobTokenSecret, CompressionCodec codec, 
				Counter counter, int reduce, Reporter reporter)
				*/
	}

	@Test
	public final void testNetworkIOChannel() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetEmpty() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSend() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testReceive() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRelease() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testClose() {
		fail("Not yet implemented"); // TODO
	}

}