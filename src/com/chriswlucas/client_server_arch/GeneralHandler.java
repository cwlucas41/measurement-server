package com.chriswlucas.client_server_arch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class GeneralHandler implements Runnable{
	
	public Socket socket;
	public PrintWriter socketOut;
	public BufferedReader socketIn;
	
	public GeneralHandler(Socket socket) {
		this.socket = socket;
		
		try {
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
