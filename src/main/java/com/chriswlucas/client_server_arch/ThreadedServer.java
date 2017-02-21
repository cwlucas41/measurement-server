package com.chriswlucas.client_server_arch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Generic threaded server that is injected with and AppHandler containing
 * the business logic for the server. This class handles the communication
 * logic for the server. The AppHandlers are started as different threads
 * with different accepted connections.
 * @author cwlucas41
 *
 */
public class ThreadedServer <T extends AppHandler> {
	
	private int port;
	private Class<T> serverHandlerClass;
	private Executor pool = Executors.newCachedThreadPool();
	
	/**
	 * Creates server with supplied hanlder and port
	 * @param port
	 * @param serverHandlerClass
	 */
	public ThreadedServer(int port, Class<T> serverHandlerClass) {
		this.port = port;
		this.serverHandlerClass = serverHandlerClass;
	}
	
	/**
	 * Start the server. The server will accept new connections and handle
	 * the connection with the specified handler
	 */
	public void start() {
		
		ServerSocket serverSocket;
		
		try {
			serverSocket = new ServerSocket(port);
			
			while (true) {
				Socket clientSocket = serverSocket.accept();
				
				// reflection to create new instance of handler class
				T serverHandler = serverHandlerClass.getConstructor(
						new Class [] {
								BufferedReader.class, 
								PrintWriter.class
						}
				).newInstance(
						new Object [] {
								new BufferedReader(new InputStreamReader(clientSocket.getInputStream())), 
								new PrintWriter(clientSocket.getOutputStream(), true)
						}
				);
				
				pool.execute(serverHandler);
			}
			
		} catch (UnknownHostException e) {
			System.err.println("Invalid hostname");
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid port number");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
