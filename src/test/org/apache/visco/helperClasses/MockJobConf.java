package helperClasses;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.jobcontrol.TestJobControl;

public class MockJobConf {

	JobConf theJob;
	public Configuration defaults;

	public MockJobConf() {

		defaults = new Configuration();
		theJob = new JobConf(defaults, TestJobControl.class);
		theJob.setJobName("DataMoveJob");
		Path outdir = new Path("output");

		FileInputFormat.setInputPaths(theJob, "input");
		theJob.setMapperClass(WordCountMapper.class);
		FileOutputFormat.setOutputPath(theJob, outdir);
		theJob.setOutputKeyClass(Text.class);
		theJob.setOutputValueClass(IntWritable.class);
		theJob.setReducerClass(SumReducer.class);
		theJob.setNumMapTasks(1);
		theJob.setNumReduceTasks(1);
		//theJob.setCombinerClass(WordCount.Reduce.class);

	}

	public JobConf returnNewJobConf() {
		return theJob;
	}

	public static class WordCountMapper extends MapReduceBase implements Mapper<IntWritable, Text, IntWritable, Text>{

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		@Override
		public void map(IntWritable key, Text value, OutputCollector<IntWritable, Text> output,
				Reporter reporter) throws IOException {

			String line = value.toString();
			StringTokenizer itr = new StringTokenizer(line);
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				output.collect(one, word);
			}
		}
	}

	public static class SumReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		public void reduce(Text key, Iterator<IntWritable> values,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			int sum = 0;
			while (values.hasNext()) {
				sum += ((IntWritable) values.next()).get();
			}
			output.collect(key, new IntWritable(sum));
		}

	}

	/* Simple Mapper and Reducer implementation which copies data it reads in. */
	public static class DataCopy extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text>,
			Reducer<Text, Text, Text, Text> {
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			output.collect(new Text(key.toString()), value);
		}

		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			Text dumbKey = new Text("");
			while (values.hasNext()) {
				Text data = (Text) values.next();
				output.collect(dumbKey, data);
			}
		}
	}
}
