package com.chriswlucas.measure.message;

import org.junit.Test;

import com.chriswlucas.measure.message.CTPMessage;

public class CTPMessageTest {

	@Test
	public void checkValidMessage() {
		new CTPMessage("t");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkInvalidMessage() {
		new CTPMessage("");
	}

}
