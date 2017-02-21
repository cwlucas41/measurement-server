package com.chriswlucas.client_server_arch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Parent class for all handlers, both client and server. Provides dependency
 * injection by letting the PrintWriter and BufferedReader be set by the user
 * and provides a convenience method to set them both by adding a socket.
 * 
 * @author cwlucas41
 *
 */
public abstract class AppHandler implements Runnable{
	
	private PrintWriter writer;
	private BufferedReader reader;
	
	public AppHandler(BufferedReader reader, PrintWriter writer) {
		this.writer = writer;
		this.reader = reader;
	}
	
	/**
	 * Trims line and prints it to the PrintWriter
	 * @param line
	 */
	public void sendLine(String line) {
		line = line.trim();
		writer.println(line);
//		System.err.println("sent: " + line);
	}
	
	/**
	 * Reads a line from the BufferedReader and trims it
	 * @return trimmed line
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		String line = reader.readLine();
		if (line != null) {
			line = line.trim();
//			System.err.println("rcvd: " + line);
		}
		return line;
	}

}
