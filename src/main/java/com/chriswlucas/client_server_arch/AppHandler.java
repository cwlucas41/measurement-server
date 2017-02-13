package com.chriswlucas.client_server_arch;

import java.net.Socket;
import java.util.Optional;

public abstract class AppHandler implements Runnable{
	
	private Optional<Socket> socket;
	
	public Socket getSocket() throws IllegalStateException {
		return socket.orElseThrow(() -> new IllegalStateException("Socket not set"));
	}
	
	public void setSocket(Socket socket) {
		this.socket = Optional.of(socket);
	}

}
