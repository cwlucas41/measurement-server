package com.chriswlucas.measure;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MeasureServerHandlerIntegrationTest {

	private MeasureServerHandler handler;
	private PrintWriter expOutWriter;
	private PrintWriter inWriter;
	private PrintWriter actOutWriter;
	private File in;
	private File actOut;
	private File expOut;
	
	@Before
	public void setup() throws IOException {
		handler = new MeasureServerHandler();
		in = File.createTempFile("input", ".txt");
		actOut = File.createTempFile("actOut", ".txt");
		expOut = File.createTempFile("expOut", ".txt");
		expOutWriter = new PrintWriter(expOut);
		inWriter = new PrintWriter(in);
		actOutWriter = new PrintWriter(actOut);
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
		
		expOutWriter.close();
		inWriter.close();
		handler.setReader(new BufferedReader(new FileReader(in)));
		handler.setWriter(actOutWriter);
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
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
