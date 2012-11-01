package tests;
//package visco.core.merge;

import static org.junit.Assert.*;

import helperClasses.MockReporter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RawLocalFileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapred.Counters.Counter;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.IFile;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.Task;
import org.apache.hadoop.examples.WordCount;
import org.apache.hadoop.conf.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//merge tree imports
import visco.core.merge.*;
import visco.util.ActionDelegate;
//merge tree imports

//mockito imports
import static org.mockito.Mockito.*;
//mockito imports


////////////////////////////////////////////////////////////////////
//Remember to try setting up the objects either in setUp,
//or in the respective test itself, since for some unknown reason
//they may work in one location but not the other. 
////////////////////////////////////////////////////////////////////


public class FinalOutputChannelTest {
	
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

