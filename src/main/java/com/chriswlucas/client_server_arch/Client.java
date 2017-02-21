package com.chriswlucas.client_server_arch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Generic client that is injected with an AppHandler containing the client's
 * business logic. This class handles the client's communication logic.
 * @author cwlucas41
 *
 */
public class Client {
	
	private String hostname;
	private int port;
	private AppHandler clientHandler;
	private Socket clientSocket;

	/**
	 * Constructs Client with specified arguments
	 * @param hostname
	 * @param port
	 * @param clientHandler
	 */
	public Client(String hostname, int port, AppHandler clientHandler) {
		this.hostname = hostname;
		this.port = port;
		this.clientHandler = clientHandler;
	}
	
	/**
	 * Starts the handler runnable
	 */
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
