package com.chriswlucas.client_server_arch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

/**
 * Parent class for all handlers, both client and server. Provides dependency
 * injection by letting the PrintWriter and BufferedReader be set by the user
 * and provides a convenience method to set them both by adding a socket.
 * 
 * Stateful - reader and writer must be set after construction. Done so that
 * user can inject the handlers into the client and server classes directly, 
 * without the socket being yet constructed.
 * @author cwlucas41
 *
 */
public abstract class AppHandler implements Runnable{
	
	private Optional<PrintWriter> socketOut;
	private Optional<BufferedReader> socketIn;
	
	/**
	 * convenience method to set both reader and writer with socket
	 * @param socket
	 */
	public void setSocket(Socket socket) {		
		try {
			setWriter(new PrintWriter(socket.getOutputStream(), true));
			setReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the PrintWriter of the handler
	 * @param writer
	 */
	public void setWriter(PrintWriter writer) {
		socketOut = Optional.of(writer);
	}
	
	/**
	 * Sets the BufferedReader of the handler
	 * @param reader
	 */
	public void setReader(BufferedReader reader) {
		socketIn = Optional.of(reader);
	}
	
	/**
	 * Gets the PrintWriter for the handler
	 * @return
	 * @throws IllegalStateException
	 */
	public PrintWriter getWriter() throws IllegalStateException {
		return socketOut.orElseThrow(() -> new IllegalStateException("Writer not set"));
	}
	
	/**
	 * Gets the BufferedReader for the handler
	 * @return
	 * @throws IllegalStateException
	 */
	public BufferedReader getReader() throws IllegalStateException {
		return socketIn.orElseThrow(() -> new IllegalStateException("Reader not set"));
	}
	
	/**
	 * Trims line and prints it to the PrintWriter
	 * @param line
	 */
	public void sendLine(String line) {
		line = line.trim();
		getWriter().println(line);
	}
	
	/**
	 * Reads a line from the BufferedReader and trims it
	 * @return trimmed line
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		String line = getReader().readLine();
		return line.trim();
	}

}
