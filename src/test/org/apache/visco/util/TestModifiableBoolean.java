package org.apache.visco.util;

import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Test;

import visco.util.ModifiableBoolean;

public class TestModifiableBoolean {

	boolean actualMB;
	boolean expectedMB;
	ModifiableBoolean MB = new ModifiableBoolean();
	
	@Before
	public void setUp() throws Exception {
	
		expectedMB = false;
		actualMB = MB.value;
		
	}

	@Test
	public void testValue(){
		
		
		//fail("Not yet implemented"); // TODO
		/*
		 * Test the default value
		 */
		assertEquals(actualMB, expectedMB);		
		
		
		/*
		 * Change value to false and test again
		 */
		
		MB.value = true;
		actualMB = MB.value;
		expectedMB = true;
		
		assertEquals(actualMB, expectedMB);
		
	}
	
}