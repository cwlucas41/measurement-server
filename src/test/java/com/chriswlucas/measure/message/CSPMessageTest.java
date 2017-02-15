package com.chriswlucas.measure.message;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chriswlucas.measure.message.CSPMessage;

public class CSPMessageTest {
	
	CSPMessage m;
	
	@Test
	public void checkCSPValidMessage() {
		m = new CSPMessage("s rtt 3 4 5");
		assertEquals(m.getType(), "rtt");
		assertEquals(m.getProbes(), 3);
		assertEquals(m.getPayloadSize(), 4);
		assertEquals(m.getDelay(), 5);
		assertNotNull(m.toString());
		
		m = new CSPMessage("s tput 10 11 12");
		assertEquals(m.getType(), "tput");
		assertEquals(m.getProbes(), 10);
		assertEquals(m.getPayloadSize(), 11);
		assertEquals(m.getDelay(), 12);
		assertNotNull(m.toString());
		
		m = new CSPMessage("s rtt\t3\r4\n5");
		assertEquals(m.getType(), "rtt");
		assertEquals(m.getProbes(), 3);
		assertEquals(m.getPayloadSize(), 4);
		assertEquals(m.getDelay(), 5);	
		assertNotNull(m.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkNumberOfRequestArgs() {
		new CSPMessage("1 2 3 4 5 6");
	}

	@Test(expected=IllegalArgumentException.class)
	public void checkCSPInvalidPhase() {
		new CSPMessage("wrong rtt 3 4 5");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkCSPInvalidMtype() {
		new CSPMessage("s wrong 3 4 5");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkCSPInvalidProbes() {
		new CSPMessage("s rtt wrong 4 5");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkCSPInvalidMsize() {
		new CSPMessage("s rtt 3 wrong 5");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkCSPInvalidDelay() {
		new CSPMessage("s rtt 3 4 wrong");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkOversizeInt() {
		new CSPMessage("s rtt 2147483648 4 5");
	}

}
