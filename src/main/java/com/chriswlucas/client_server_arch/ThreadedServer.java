package com.chriswlucas.client_server_arch;

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
public class ThreadedServer {
	
	private int port;
	private AppHandler serverHandler;
	private Executor pool = Executors.newCachedThreadPool();
	
	/**
	 * Creates server with supplied hanlder and port
	 * @param port
	 * @param serverHandler
	 */
	public ThreadedServer(int port, AppHandler serverHandler) {
		this.port = port;
		this.serverHandler = serverHandler;
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
				
				serverHandler.setSocket(clientSocket);
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
