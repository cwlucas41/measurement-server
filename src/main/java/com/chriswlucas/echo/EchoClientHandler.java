package com.chriswlucas.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.chriswlucas.client_server_arch.AppHandler;

/**
 * Runnable containing the business logic for the echo client. Reads lines from stdin
 * and prints them to the socket, then reads line from the socket and prints
 * to stdout.
 * @author cwlucas41
 *
 */
public class EchoClientHandler extends AppHandler{

	private BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
	
	public EchoClientHandler(BufferedReader reader, PrintWriter writer) {
		super(reader, writer);
	}

	public void run() {
		
		try {
			String line;
			while ((line = userIn.readLine()) != null) {
				sendLine(line);
				System.out.println(readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the user input so that something other than the default stdin
	 * can be used. Useful for testing.
	 * @param reader
	 */
	protected void setUserIn(BufferedReader reader) {
		userIn = reader;
	}
}
