package com.chriswlucas.echo;

import java.io.IOException;

import com.chriswlucas.client_server_arch.AppHandler;

/**
 * Runnable containing the business logic for an echo server. It reads from 
 * the socket and prints the read message back to the socket with no delay.
 * @author cwlucas41
 *
 */
public class EchoServerHandler extends AppHandler {

	public void run() {
		try {
			
			String line;
			while ((line = readLine()) != null) {
				sendLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
