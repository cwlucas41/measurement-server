package com.chriswlucas.echo;

import com.chriswlucas.client_server_arch.GeneralClient;

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
	
		new GeneralClient<EchoClientHandler>(hostname, port, EchoClientHandler.class).start();
		
	}
	
}
