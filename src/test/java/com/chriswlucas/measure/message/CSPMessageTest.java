package com.chriswlucas.measure.message;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chriswlucas.measure.message.CSPMessage;
import com.chriswlucas.measure.message.CSPMessage.MeasureType;

public class CSPMessageTest {
	
	CSPMessage m;
	
	@Test
	public void checkCSPValidRttMessage() {
		m = new CSPMessage("s rtt 3 1000 5");
		assertEquals(m.getType(), MeasureType.RTT);
		assertEquals(m.getProbes(), 3);
		assertEquals(m.getPayloadSize(), 1000);
		assertEquals(m.getDelay(), 5);
		assertNotNull(m.toString());
	}
	
	@Test
	public void checkCSPValidTputMessage() {
		m = new CSPMessage("s tput 10 16K 12");
		assertEquals(m.getType(), MeasureType.TPUT);
		assertEquals(m.getProbes(), 10);
		assertEquals(m.getPayloadSize(), 16000);
		assertEquals(m.getDelay(), 12);
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
	public void checkCSPInvalidRttMsize() {
		new CSPMessage("s rtt 3 3 5");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkCSPInvalidTputMsize() {
		new CSPMessage("s tput 3 3K 5");
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
