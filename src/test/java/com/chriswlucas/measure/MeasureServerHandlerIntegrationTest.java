package com.chriswlucas.measure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chriswlucas.client_server_arch.AppHandlerInegrationLogic;

public class MeasureServerHandlerIntegrationTest extends AppHandlerInegrationLogic{
	
	@Before
	public void setup() throws IOException {
		commonSetup();
		handler = new MeasureServerHandler(
				new BufferedReader(new FileReader(in)), 
				actOutWriter
		);
}
	
	@Test
	public void valid3() {
		inWriter.println("s rtt 3 1 0");
		inWriter.println("m 1 g");
		inWriter.println("m 2 g");
		inWriter.println("m 3 g");
		inWriter.println("t");
		
		expOutWriter.println("200 OK: Ready");
		expOutWriter.println("m 1 g");
		expOutWriter.println("m 2 g");
		expOutWriter.println("m 3 g");
		expOutWriter.println("200 OK: Closing Connection");
	}
	
	@Test
	public void invalidCSP() {
		inWriter.println("wrong rtt 3 1 0");
		
		expOutWriter.println("404 ERROR: Invalid Connection Setup Message");
	}
	
	@Test
	public void invalidMPSeqNum() {
		inWriter.println("s rtt 3 1 0");
		inWriter.println("m 1 g");
		inWriter.println("m 3 g");
		
		expOutWriter.println("200 OK: Ready");
		expOutWriter.println("m 1 g");
		expOutWriter.println("404 ERROR: Invalid Measurement Message");
	}
	
	@Test
	public void invalidMPMissingMessage() {
		inWriter.println("s rtt 3 1 0");
		inWriter.println("m 1 g");
		inWriter.println("m 2 g");
		inWriter.println("wrong");
		
		expOutWriter.println("200 OK: Ready");
		expOutWriter.println("m 1 g");
		expOutWriter.println("m 2 g");
		expOutWriter.println("404 ERROR: Invalid Measurement Message");
	}
	
	@Test
	public void invalidCTPMessage() {
		inWriter.println("s rtt 3 1 0");
		inWriter.println("m 1 g");
		inWriter.println("m 2 g");
		inWriter.println("m 3 g");
		inWriter.println("wrong");
		
		expOutWriter.println("200 OK: Ready");
		expOutWriter.println("m 1 g");
		expOutWriter.println("m 2 g");
		expOutWriter.println("m 3 g");
		expOutWriter.println("404 ERROR: Invalid Connection Termination Message");
	}
	
	@After
	public void clean() throws IOException {
		commonClean();
	}

}
