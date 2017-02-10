package com.chriswlucas.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.chriswlucas.client_server_arch.AppHandler;

public class EchoClientHandler extends AppHandler{

	public void run() {
		
		try {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter socketOut = new PrintWriter(getSocket().getOutputStream(), true);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			
			String line;
			while ((line = stdin.readLine()) != null) {
				socketOut.println(line);
				System.out.println(socketIn.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
}
