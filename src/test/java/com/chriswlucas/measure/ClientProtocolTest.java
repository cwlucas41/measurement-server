package com.chriswlucas.measure;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ClientProtocolTest {
	
	MeasureClientHandler handler;
	
	@Before
	public void setup() {
		handler = new MeasureClientHandler();
	}

	@Test(expected=IllegalArgumentException.class)
	public void checkNumberOfRequestArgs() {
		handler.parseCSPRequest("1 2 3 4 5 6");
	}
	
	@Test
	public void checkValidArgs() {
		handler.parseCSPRequest("s rtt 3 4 5");
		assertEquals(handler.mtype, "rtt");
		assertEquals(handler.probes, 3);
		assertEquals(handler.msize, 4);
		
		handler.parseCSPRequest("s tput 10 11 12");
		assertEquals(handler.mtype, "tput");
		assertEquals(handler.probes, 10);
		assertEquals(handler.msize, 11);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkOversizeInt() {
		handler.parseCSPRequest("s rtt 2147483648 4 5");
	}

}
