package helperClasses;

import java.net.URL;

import org.apache.hadoop.mapred.TaskAttemptID;
import org.apache.hadoop.mapred.TaskID;

public class MockMapOutputLocation {
	TaskAttemptID taskAttemptId;
	TaskID taskId;
	String ttHost;
	URL taskOutput;

	public MockMapOutputLocation(TaskAttemptID taskAttemptId, String ttHost,
			URL taskOutput) {
		this.taskAttemptId = taskAttemptId;
		this.taskId = this.taskAttemptId.getTaskID();
		this.ttHost = ttHost;
		this.taskOutput = taskOutput;
	}

	public TaskAttemptID getTaskAttemptId() {
		return taskAttemptId;
	}

	public TaskID getTaskId() {
		return taskId;
	}

	public String getHost() {
		return ttHost;
	}

	public URL getOutputLocation() {
		return taskOutput;
	}
}
	
	
