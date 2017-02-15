package com.chriswlucas.client_server_arch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public abstract class AppHandler implements Runnable{
	
	private Optional<Socket> socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	private Socket getSocket() throws IllegalStateException {
		return socket.orElseThrow(() -> new IllegalStateException("Socket not set"));
	}
	
	public void setSocket(Socket socket) {
		this.socket = Optional.of(socket);
		
		try {
			socketOut = new PrintWriter(getSocket().getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendLine(String line) {
		line = line.trim();
		socketOut.println(line);
		System.out.println("Sent: " + line);
	}
	
	public String readLine() throws IOException {
		String line = socketIn.readLine();
		System.out.println("Rcvd: " + line);
		return line.trim();
	}
	
	public void close() {
		try {
			getSocket().close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
