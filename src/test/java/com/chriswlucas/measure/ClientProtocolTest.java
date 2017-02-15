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
	
	@Test
	public void payloadGeneratorTest() {
		handler.generatePayload(5).equals("ggggg");
	}

}
