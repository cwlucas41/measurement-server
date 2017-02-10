package com.chriswlucas.client_server_arch;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadedServer {
	
	int port;
	AppHandler serverHandler;
	Executor pool = Executors.newCachedThreadPool();
	
	public ThreadedServer(int port, AppHandler serverHandler) {
		this.port = port;
		this.serverHandler = serverHandler;
	}
	
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
