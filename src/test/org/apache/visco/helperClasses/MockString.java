package org.apache.visco.helperClasses;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class MockString implements WritableComparable<MockString> {
    private String value;

    public MockString() {

    }

    public MockString(String v) {
        value = v;
    }

    public void set(String v) {
        value = v;
    }

    public String get() {
        return value; 
    }

    @Override
        public void write(DataOutput out) throws IOException {

        }

    @Override
        public void readFields(DataInput in) throws IOException {

        }

    @Override
        public int compareTo(MockString o) {
            return 0;
        }
    
    public String toString() {
    	return value;
    }
}
