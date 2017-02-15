package com.chriswlucas.measure.message;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chriswlucas.measure.message.MPMessage;

public class MPMessageTest {
	
	MPMessage m;

	@Test(expected=IllegalArgumentException.class)
	public void checkMPInvalidPhase() {
		new MPMessage("wrong 2 a");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkMPInvalidSeqNumber() {
		new MPMessage("m wrong a");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkMPInvalidPayload() {
		new MPMessage("m 2 ");
	}
	
	@Test
	public void checkMPValidMessage() {
		m = new MPMessage("m 2 a");
		assertEquals(m.getSequenceNumber(), 2);
		assertEquals(m.getPayload(), "a");
		
		m = new MPMessage("m 10 payload");
		assertEquals(m.getSequenceNumber(), 10);
		assertEquals(m.getPayload(), "payload");
		
		m = new MPMessage("m 20\tpayload");
		assertEquals(m.getSequenceNumber(), 20);
		assertEquals(m.getPayload(), "payload");
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkCTPInvalidPhase() {
		new MPMessage("wrong 2 a");
	}

}
