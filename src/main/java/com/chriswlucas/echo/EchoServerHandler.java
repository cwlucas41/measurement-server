package com.chriswlucas.echo;

import java.io.IOException;

import com.chriswlucas.client_server_arch.AppHandler;

public class EchoServerHandler extends AppHandler {

	public void run() {
		try {
			
			String line;
			while ((line = readLine()) != null) {
				sendLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
