package com.chriswlucas.measure;

import com.chriswlucas.client_server_arch.Client;

/**
 * Main class for the measure client. Clint takes hostname and port
 * command line arguments.
 * @author cwlucas41
 *
 */
public class MeasureClient {
	
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
	
		new Client<MeasureClientHandler>(hostname, port, MeasureClientHandler.class).start();
		
	}
	
}
