package com.chriswlucas.client_server_arch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	String hostname;
	int port;
	AppHandler clientHandler;
	Socket clientSocket;

	public Client(String hostname, int port, AppHandler clientHandler) {
		this.hostname = hostname;
		this.port = port;
		this.clientHandler = clientHandler;
	}
	
	public void start() {
		try {
			clientSocket = new Socket(hostname, port);
			
			clientHandler.setWriter(new PrintWriter(clientSocket.getOutputStream(), true));
			clientHandler.setReader(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
			clientHandler.run();
			
		} catch (UnknownHostException e) {
			System.err.println("Invalid hostname");
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid port number");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
