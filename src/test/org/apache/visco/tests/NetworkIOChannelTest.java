package tests;

import static org.junit.Assert.*;

import javax.crypto.SecretKey;

import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.ReduceTask;
import org.apache.hadoop.mapred.ReduceTask.ReduceCopier;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.Counters.Counter;
import org.apache.hadoop.mapred.ReduceTask.ReduceCopier.MapOutputLocation;
import org.junit.Before;
import org.junit.Test;
import junit.framework.Assert;
import helperClasses.MockInteger;
import helperClasses.MockJobConf;
import helperClasses.MockReporter;
import helperClasses.MockString;
import helperClasses.WordCount;
import visco.util.*;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.serializer.SerializationFactory;
import org.apache.hadoop.mapred.Counters.Counter;
import org.apache.hadoop.mapred.IFile;
import org.apache.hadoop.mapred.JobConf;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Text;

//import WordCount;
//import WordCount.MapClass;
//import WordCount.Reduce;

import visco.core.merge.*;

public class NetworkIOChannelTest {
	
	/* Constructor arguments */
	public JobConf jobConf;
	public MapOutputLocation inputLocation;
	public ReduceTask.ReduceCopier<MockString, MockInteger> reduceCopier;
	public SecretKey jobTokenSecret;
	public CompressionCodec codec;
	public Counter counter;
	public int reduce;
	public MockReporter reporter; 
	
	NetworkIOChannel realNetworkIOChannel;

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
