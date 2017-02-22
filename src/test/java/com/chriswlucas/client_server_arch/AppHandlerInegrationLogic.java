package com.chriswlucas.client_server_arch;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class AppHandlerInegrationLogic {

	 protected AppHandler handler;
	 protected PrintWriter expOutWriter;
	 protected PrintWriter inWriter;
	 protected PrintWriter actOutWriter;
	 protected File in;
	 protected File actOut;
	 protected File expOut;
	 protected BufferedReader inReader;
	
	public void commonSetup() throws IOException {
		in = File.createTempFile("input", ".txt");
		actOut = File.createTempFile("actOut", ".txt");
		expOut = File.createTempFile("expOut", ".txt");
		expOutWriter = new PrintWriter(expOut);
		inWriter = new PrintWriter(in);
		inReader = new BufferedReader(new FileReader(in));
		actOutWriter = new PrintWriter(actOut);
	}
	
	public void commonClean() throws IOException {
		
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
				// assert lines in files are same
				if (!l1.equals(l2)) {
					System.out.println("Act: " + l1);
					System.out.println("Exp: " + l2);
					fail();
				}
				i++;
			}
			// assert there are no more lines
			assertNull(r1.readLine());
			assertNull(r2.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// assert there was at least one line
		if (i == 0) {
			fail();
		}
		
		
	}
	
}
