package com.chriswlucas.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.chriswlucas.client_server_arch.GeneralHandler;

public class EchoClientHandler extends GeneralHandler{
	
	public EchoClientHandler(Socket socket) {
		super(socket);
	}

	public void run() {
		
		try {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			
			String line;
			while ((line = stdin.readLine()) != null) {
				socketOut.println(line);
				System.out.println(socketIn.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
