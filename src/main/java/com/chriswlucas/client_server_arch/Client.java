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
public class Client <T extends AppHandler> {
	
	private String hostname;
	private int port;
	private Class<T> clientHandlerClass;
	private Socket clientSocket;

	/**
	 * Constructs Client with specified arguments
	 * @param hostname
	 * @param port
	 * @param clientHandlerClass
	 */
	public Client(String hostname, int port, Class<T> clientHandlerClass) {
		this.hostname = hostname;
		this.port = port;
		this.clientHandlerClass = clientHandlerClass;
	}
	
	/**
	 * Starts the handler runnable
	 */
	public void start() {
		try {
			clientSocket = new Socket(hostname, port);
			
			// reflection to create new instance of handler class and run it
			clientHandlerClass.getConstructor(
					new Class [] {
							BufferedReader.class, 
							PrintWriter.class
					}
			).newInstance(
					new Object [] {
							new BufferedReader(new InputStreamReader(clientSocket.getInputStream())), 
							new PrintWriter(clientSocket.getOutputStream(), true)
					}
			).run();
			
		} catch (UnknownHostException e) {
			System.err.println("Invalid hostname");
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid port number");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
