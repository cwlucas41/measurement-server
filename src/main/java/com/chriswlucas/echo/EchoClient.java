package com.chriswlucas.echo;

import com.chriswlucas.client_server_arch.Client;

/**
 * Main class for the client of the echo server. Must be passed hostname and
 * port number as command line arguments.
 * @author cwlucas41
 *
 */
public class EchoClient {
	
	public static void main(String[] args) {
		
		if (args.length != 2) {
			System.err.println("Invalid number of arguments");
			System.err.println("Usage: java EchoClient <hostname> <port>");
			System.exit(1);
		}
		
		String hostname = args[0];
		int port = 0;
		try {
			port = Integer.parseInt(args[1]);
		} catch (Exception e) {
			System.err.println("Port must be an integer");
			System.exit(1);
		}
	
		new Client<EchoClientHandler>(hostname, port, EchoClientHandler.class).start();
		
	}
	
}
