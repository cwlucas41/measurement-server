package com.chriswlucas.measure;

import static org.junit.Assert.*;

import org.junit.Test;

public class MeasureClientHandlerTest {

	@Test
	public void payloadSizeTest() {
		int msize = 100;
		String payload = MeasureClientHandler.generatePayload(msize);
		assertEquals(msize, payload.getBytes().length);
	}

}
