package com.chriswlucas.measure;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MeasureClientHandlerIntegrationTest {
	
	private MeasureClientHandler handler;
	private PrintWriter expOutWriter;
	private PrintWriter inWriter;
	private PrintWriter actOutWriter;
	private File in;
	private File actOut;
	private File expOut;
	
	@Before
	public void setup() throws IOException {
		in = File.createTempFile("input", ".txt");
		actOut = File.createTempFile("actOut", ".txt");
		expOut = File.createTempFile("expOut", ".txt");
		expOutWriter = new PrintWriter(expOut);
		inWriter = new PrintWriter(in);
		actOutWriter = new PrintWriter(actOut);
		handler = new MeasureClientHandler(
				new BufferedReader(new FileReader(in)),
				actOutWriter
		);
	}
	
	@Test
	public void valid3() throws IOException {
		handler.setUserIn(new BufferedReader(new StringReader("s rtt 3 1 0")));
		
		expOutWriter.println("s rtt 3 1 0");
		expOutWriter.println("m 1 g");
		expOutWriter.println("m 2 g");
		expOutWriter.println("m 3 g");
		expOutWriter.println("t");
			
		inWriter.println("200 OK: Ready");
		inWriter.println("m 1 g");
		inWriter.println("m 2 g");
		inWriter.println("m 3 g");
		inWriter.println("200 OK: Closing Connection");
	}
	
	@Test
	public void CSPnetworkError() throws IOException {		
		handler.setUserIn(new BufferedReader(new StringReader("s rtt 3 1 0")));
		
		expOutWriter.println("s rtt 3 1 0");
			
		inWriter.println("404 ERROR: Invalid Connection Setup Message");
	}
	
	@Test
	public void invalidCSP() throws IOException {		
		handler.setUserIn(new BufferedReader(new StringReader("wrong rtt 3 1 0")));	
		expOutWriter.println("hack");
		actOutWriter.println("hack");
	}
	
	@Test
	public void invalidMPMessage() throws IOException {
		handler.setUserIn(new BufferedReader(new StringReader("s rtt 3 1 0")));
		
		expOutWriter.println("s rtt 3 1 0");
		expOutWriter.println("m 1 g");
		expOutWriter.println("m 2 g");
			
		inWriter.println("200 OK: Ready");
		inWriter.println("m 1 g");
		inWriter.println("404 ERROR: Invalid Measurement Message");
	}
	
	@Test
	public void invalidCTPMessage() throws IOException {
		handler.setUserIn(new BufferedReader(new StringReader("s rtt 3 1 0")));
		
		expOutWriter.println("s rtt 3 1 0");
		expOutWriter.println("m 1 g");
		expOutWriter.println("m 2 g");
		expOutWriter.println("m 3 g");
		expOutWriter.println("t");
			
		inWriter.println("200 OK: Ready");
		inWriter.println("m 1 g");
		inWriter.println("m 2 g");
		inWriter.println("m 3 g");
		inWriter.println("404 ERROR: Invalid Connection Termination Message");
	}
	
	@After
	public void clean() throws IOException {
		
		expOutWriter.close();
		inWriter.close();

		PrintStream original = System.out;
		PrintStream nullStream = new PrintStream(File.createTempFile("null", ".out"));
		System.setOut(nullStream);
		
		handler.run();
		
		System.setOut(original);
		actOutWriter.close();
		
		compareFiles(actOut, expOut);
		
		in.delete();
		actOut.delete();
		expOut.delete();
	}
	
	void compareFiles(File actual, File f2) {
		int i = 0;
		
		try (
			BufferedReader r1 = new BufferedReader(new FileReader(actual));
			BufferedReader r2 = new BufferedReader(new FileReader(f2));
		) {
			String l1 = null;
			String l2 = null;
			while (((l1 = r1.readLine()) != null) && ((l2 = r2.readLine()) != null)) {
				if (!l1.equals(l2)) {
					System.out.println("Act: " + l1);
					System.out.println("Exp: " + l2);
					fail();
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (i == 0) {
			fail();
		}
	}

}
