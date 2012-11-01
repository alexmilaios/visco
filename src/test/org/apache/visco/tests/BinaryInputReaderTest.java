package tests;

import static org.junit.Assert.*;

import java.net.URL;

import helperClasses.MockJobConf;

import javax.crypto.SecretKey;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapred.Counters;
import org.apache.hadoop.mapred.ReduceTask;
import org.apache.hadoop.mapred.ReduceTask.ReduceCopier;
import org.apache.hadoop.mapred.ReduceTask.ReduceCopier.MapOutputLocation;
import org.apache.hadoop.mapred.Task.Counter;
import org.apache.hadoop.mapred.TaskAttemptID;
import org.junit.Before;
import org.junit.Test;

import visco.core.io.BinaryInputReader;

public class BinaryInputReaderTest {

	MockJobConf jobConf;
	BinaryInputReader test;
	Counters.Counter spilledRecordsCounter;
	Counters counters = new Counters();
	MapOutputLocation loc;
	SecretKey tokenSecret;
	ReduceCopier copier;
	ReduceTask task;
	
	@Before
	public void setUp() throws Exception {
	
		task = new ReduceTask();
		copier =  task.new ReduceCopier(null, jobConf.returnNewJobConf(), null);
		loc = copier.new MapOutputLocation(new TaskAttemptID(), "localhost", new URL("localhost"));
		spilledRecordsCounter = counters.findCounter(Counter.SPILLED_RECORDS);
		jobConf = new MockJobConf();
		test =  new BinaryInputReader(jobConf.returnNewJobConf(), null, spilledRecordsCounter, loc, null, 2);
		
	}
	@Test
	public final void testBinaryInputReader() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetLength() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetPosition() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testReadNextBlock() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testNext() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testClose() {
		fail("Not yet implemented"); // TODO
	}

}
