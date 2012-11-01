package tests;

import static org.junit.Assert.assertNotNull;
import helperClasses.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.crypto.SecretKey;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapred.Counters;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.ReduceTask;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.Task;
import org.apache.hadoop.mapred.TaskTracker;
import org.apache.hadoop.mapred.ReduceTask.ReduceCopier;
import org.apache.hadoop.mapred.ReduceTask.ReduceCopier.MapOutputLocation;
import org.apache.hadoop.mapred.TaskAttemptID; 
import org.apache.hadoop.mapreduce.Counter;
import org.junit.Before;
import org.junit.Test;

import visco.core.merge.MergingTask;
import visco.core.merge.MergingTree;
import visco.util.ActionDelegate;

public class MergingTreeTest {
	// create jobConf
	MockJobConf jobConf;
	// create fs
	FileSystem fs;
	
	// create inputPaths - (akram) - new path system using List<T>
	List<MapOutputLocation> inputPaths;
	
	// create threadpool
	Executor threadPool;
	//create security key
	

	@Before
	public void setUp() throws Exception {
		// initialize jobConf
		jobConf = new MockJobConf();
		// initialize fs
		fs = FileSystem.getLocal(jobConf.defaults).getRaw();
		
		//initialize list (akram) = new for List<T>
		inputPaths = new ArrayList<MapOutputLocation>(1);

		ReduceTask reduce = new ReduceTask();
		TaskTracker tracker = new TaskTracker(jobConf.returnNewJobConf());
		
		ReduceCopier redCopier = reduce.new ReduceCopier(tracker, jobConf.returnNewJobConf(), new ReduceTask().new TaskReporter(null, null, null));
		
		MapOutputLocation mapLoc = redCopier.new MapOutputLocation(new TaskAttemptID()," http://www.google.com" , new URL(" http://www.google.com:80"));
		
		inputPaths.add(mapLoc);
		
		
		
//		new TaskAttemptID(), 
//		"www.visco.co.uk", new URL("http","www.visco.co.uk", 5000, "/test.txt")

		// initialize input file paths
		/*
		for (int i = 0; i < inputPaths.size(); i++) {
			//akram - trying to replace adding paths
			//inputPaths.add("/testPath" + i);
			inputPaths[i] = new Path("/testPath" + i);
		}*/
		
		// initialize threadpool
		threadPool = Executors.newCachedThreadPool();

		// generate merging-tree
		MergingTree.createMergingTree(jobConf.returnNewJobConf(),
			inputPaths, null,
			threadPool, 
			null,  null,
			null,
			null, null, 
			null, null, 
			null);
	}

	@Test
	public void test() {

		System.out.println("mergingTasks size: "
				+ MergingTree.mergingTasks.size());

		for (int i = 0; i < MergingTree.mergingTasks.size(); i++) {

			@SuppressWarnings("rawtypes")
			MergingTask task = MergingTree.mergingTasks.get(i);

			// Results should not be null as we've assigned multiple paths
			assertNotNull("tx channel is null", task.getTXChannel());
			assertNotNull("rx0 channel is null", task.getRXChannel(0));
			assertNotNull("rx1 channel is null", task.getRXChannel(1));
			
			// 

		}

	}

}
