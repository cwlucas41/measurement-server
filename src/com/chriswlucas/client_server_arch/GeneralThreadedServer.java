package com.chriswlucas.client_server_arch;

import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GeneralThreadedServer<T extends GeneralHandler> {
	
	int port;
	Class<T> serverHandlerClass;
	
	public GeneralThreadedServer(int port, Class<T> serverHandlerClass) {
		this.port = port;
		this.serverHandlerClass = serverHandlerClass;
	}
	
	public void start() {
		
		ServerSocket serverSocket;
		
		try {
			serverSocket = new ServerSocket(port);
			
			while (true) {
				Socket clientSocket = serverSocket.accept();
				
				Constructor<T> constructor = serverHandlerClass.getConstructor(new Class[] {Socket.class});
				T clientHandler = constructor.newInstance(new Object[] {clientSocket});
				new Thread(clientHandler).start();
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
