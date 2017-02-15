package com.chriswlucas.measure;

import com.chriswlucas.client_server_arch.ThreadedServer;

public class MeasureServer {

	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.err.println("Invalid number of arguments");
			System.err.println("Usage: java EchoServer <port>");
			System.exit(1);
		}
			
		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.err.println("Port must be an integer");
			System.exit(1);
		}
		
		new ThreadedServer(port, new MeasureServerHandler()).start();
	}
	
}
