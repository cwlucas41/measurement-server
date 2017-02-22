package com.chriswlucas.measure;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chriswlucas.client_server_arch.AppHandlerInegrationLogic;

public class MeasureClientHandlerIntegrationTest extends AppHandlerInegrationLogic{
	
	@Before
	public void setup() throws IOException {
		commonSetup();
		MeasureClientHandler tempHandler = new MeasureClientHandler(
				inReader,
				actOutWriter
		);
		tempHandler.setUserIn(inReader);
		handler = tempHandler;
	}
	
	@Test
	public void valid3() throws IOException {		
		expOutWriter.println("s rtt 3 1 0");
		expOutWriter.println("m 1 g");
		expOutWriter.println("m 2 g");
		expOutWriter.println("m 3 g");
		expOutWriter.println("t");
			
		inWriter.println("s rtt 3 1 0");
		inWriter.println("200 OK: Ready");
		inWriter.println("m 1 g");
		inWriter.println("m 2 g");
		inWriter.println("m 3 g");
		inWriter.println("200 OK: Closing Connection");
	}
	
	@Test
	public void CSPnetworkError() throws IOException {				
		expOutWriter.println("s rtt 3 1 0");
			
		inWriter.println("s rtt 3 1 0");
		inWriter.println("404 ERROR: Invalid Connection Setup Message");
	}
	
	@Test
	public void invalidCSP() throws IOException {		
		expOutWriter.println("hack");
		actOutWriter.println("hack");
		inWriter.println("wrong rtt 3 1 0");
	}
	
	@Test
	public void invalidMPMessage() throws IOException {
		
		expOutWriter.println("s rtt 3 1 0");
		expOutWriter.println("m 1 g");
		expOutWriter.println("m 2 g");
			
		inWriter.println("s rtt 3 1 0");
		inWriter.println("200 OK: Ready");
		inWriter.println("m 1 g");
		inWriter.println("404 ERROR: Invalid Measurement Message");
	}
	
	@Test
	public void invalidCTPMessage() throws IOException {
		
		expOutWriter.println("s rtt 3 1 0");
		expOutWriter.println("m 1 g");
		expOutWriter.println("m 2 g");
		expOutWriter.println("m 3 g");
		expOutWriter.println("t");
			
		inWriter.println("s rtt 3 1 0");
		inWriter.println("200 OK: Ready");
		inWriter.println("m 1 g");
		inWriter.println("m 2 g");
		inWriter.println("m 3 g");
		inWriter.println("404 ERROR: Invalid Connection Termination Message");
	}
	
	@After
	public void clean() throws IOException {
		commonClean();
	}
}
