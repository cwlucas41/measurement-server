package com.chriswlucas.measure;

import org.junit.Test;

public class ProtocolTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void checkInvalidPhase() {
		MeasureProtocol.parseCSPMessage("wrong rtt 3 4 5");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkInvalidMtype() {
		MeasureProtocol.parseCSPMessage("s wrong 3 4 5");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkInvalidProbes() {
		MeasureProtocol.parseCSPMessage("s rtt wrong 4 5");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkInvalidMsize() {
		MeasureProtocol.parseCSPMessage("s rtt 3 wrong 5");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkInvalidDelay() {
		MeasureProtocol.parseCSPMessage("s rtt 3 4 wrong");
	}
	
	@Test
	public void whitespaceTest() {
		MeasureProtocol.parseCSPMessage("s rtt  3\t4 \t 5");
	}

}
