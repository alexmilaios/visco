package helperClasses;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;


public class MockInteger implements Writable {
    private Integer value;

    public MockInteger() {

    }

    public MockInteger(Integer v) {
        value = v;
    }

    public void set(Integer v) {
        value = v;
    }

    public Integer get() {
        return value; 
    }

    @Override
        public void write(DataOutput out) throws IOException {

        }

    @Override
        public void readFields(DataInput in) throws IOException {

        }
    
    public String toString() {
    	return value.toString();
    }
}


