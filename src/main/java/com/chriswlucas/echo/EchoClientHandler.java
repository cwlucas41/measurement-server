package com.chriswlucas.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.chriswlucas.client_server_arch.AppHandler;

public class EchoClientHandler extends AppHandler{

	public void run() {
		
		try {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			
			String line;
			while ((line = stdin.readLine()) != null) {
				sendLine(line);
				System.out.println(readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
}
