package com.chriswlucas.measure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.chriswlucas.client_server_arch.AppHandler;

public class MeasureClientHandler extends AppHandler{
	
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	String mtype;
	int probes;
	int msize;

	public void run() {
		
		try {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			socketOut = new PrintWriter(getSocket().getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			
			String line;
			while ((line = stdin.readLine()) != null) {
				
				try {
				
				sendCSPRequest(line);
				handleCSPResponse();
				
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	void parseCSPRequest(String request) throws IllegalArgumentException {
		
		MeasureProtocol.parseCSPMessage(request);
		
		String[] fields = request.split(" ");
		
		mtype = fields[1];
		
		try {
			probes = Integer.parseInt(fields[2]);
			msize = Integer.parseInt(fields[3]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("One of the numbers did not parse");
		}
	}
	
	private void sendCSPRequest(String request) {
		socketOut.println(request);
	}
	
	private void handleCSPResponse() throws IOException {
		String response = socketIn.readLine();
		System.err.println(response);
	}
}
