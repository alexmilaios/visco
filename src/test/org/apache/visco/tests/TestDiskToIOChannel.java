package org.apache.visco.tests;
//package visco.core.merge;

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


////////////////////////////////////////////////////////////////////
//Remember to try setting up the objects either in setUp,
//or in the respective test itself, since for some unknown reason
//they may work in one location but not the other. 
////////////////////////////////////////////////////////////////////


public class TestDiskToIOChannel {
	
	/* Constructor arguments */
	//public JobConf jobConf; //done
	public JobConf jobConf;
	public MockReporter reporter; //done
	public FileSystem fs;
	public Path inputPath;
	public CompressionCodec codec;
	public Counter counter;
	
	ModifiableBoolean someBool;
	//DiskToIOChannel<Text, IntWritable> realDiskToIOChannel;
	//IOChannelBuffer<Text, IntWritable> item;
	DiskToIOChannel realDiskToIOChannel;
	IOChannelBuffer item;
	
	@Before
	public void setUp() throws Exception {
		/* Constructor Arguments */
		jobConf = new JobConf(new Configuration(), WordCount.class);
		jobConf.setJobName("wordcount");
		jobConf.setOutputKeyClass(Text.class);
		jobConf.setOutputValueClass(IntWritable.class);
	    jobConf.setMapperClass(WordCount.MapClass.class);        
	    jobConf.setCombinerClass(WordCount.Reduce.class);
	    jobConf.setReducerClass(WordCount.Reduce.class);
			
		//reporter
		reporter = new MockReporter();
		
		//fs
		fs = FileSystem.getLocal(jobConf);
		
		//inputPath
		inputPath = new Path("/Users/jason/Desktop/input.txt");
		fs.makeQualified(inputPath);
		
		//codec
		
		//counter
		
		//MB
		someBool = new ModifiableBoolean();
		someBool.value = false;
		
		//Construct our DiskToIOChannel!!!
		realDiskToIOChannel = 
		new DiskToIOChannel(jobConf, fs, inputPath, codec, counter, reporter);
	}
		
	@Test
	public void testGetEmpty() {
		realDiskToIOChannel.GetEmpty(someBool);
	}

	@Test
	public void testSend() {
		realDiskToIOChannel.Send(item);
	}

	@Test
	public void testReceive() {
		realDiskToIOChannel.Receive(someBool);
	}

	@Test
	public void testRelease() {
		realDiskToIOChannel.Release(item);
	}

	@Test
	public void testClose() {
		realDiskToIOChannel.Close();
	}
}

