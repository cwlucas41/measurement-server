package com.chriswlucas.echo;

import java.io.IOException;
import java.net.Socket;

import com.chriswlucas.client_server_arch.GeneralHandler;

public class EchoServerHandler extends GeneralHandler {
		
	public EchoServerHandler(Socket clientSocket) {
		super(clientSocket);
	}

	public void run() {
		try {
			String line;
			while ((line = socketIn.readLine()) != null) {
				socketOut.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
