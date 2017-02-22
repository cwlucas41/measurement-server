package com.chriswlucas.echo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chriswlucas.client_server_arch.AppHandlerInegrationLogic;

public class EchoServerIntegrationTest extends AppHandlerInegrationLogic{

	@Before
	public void setup() throws IOException {
		commonSetup();
		handler = new EchoServerHandler(
				new BufferedReader(new FileReader(in)),
				actOutWriter
		);
	}
	
	@Test
	public void basicTest() {
		inWriter.println("hello world");
		
		expOutWriter.println("hello world");
	}
	
	@Test 
	public void sequenceTest() {
		for (int i = 0; i < 100; i++) {
			inWriter.println(i);
			expOutWriter.println(i);
		}
	}

	@After
	public void clean() throws IOException {
		commonClean();
	}
}
