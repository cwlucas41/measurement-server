package com.chriswlucas.client_server_arch;

import java.lang.reflect.Constructor;
import java.net.Socket;
import java.net.UnknownHostException;

public class GeneralClient<T extends GeneralHandler> {
	
	String hostname;
	int port;
	Class<T> clientHandlerClass;
	Socket clientSocket;

	public GeneralClient(String hostname, int port, Class<T> clientHandlerClass) {
		this.hostname = hostname;
		this.port = port;
		this.clientHandlerClass = clientHandlerClass;
	}
	
	public void start() {
		try {
			clientSocket = new Socket(hostname, port);
			
			Constructor<T> constructor = clientHandlerClass.getConstructor(new Class[] {Socket.class});
			T clientHandler = constructor.newInstance(new Object[] {clientSocket});		
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
