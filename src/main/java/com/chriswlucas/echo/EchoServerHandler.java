package com.chriswlucas.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.chriswlucas.client_server_arch.AppHandler;

public class EchoServerHandler extends AppHandler {

	public void run() {
		try {
			PrintWriter socketOut = new PrintWriter(getSocket().getOutputStream(), true);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			
			String line;
			while ((line = socketIn.readLine()) != null) {
				socketOut.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
