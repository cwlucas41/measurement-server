package com.chriswlucas.measure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chriswlucas.client_server_arch.AppHandler;
import com.chriswlucas.measure.message.*;


public class MeasureClientHandler extends AppHandler{
	
	private long tempTime;
	private List<Long> rtts;

	public void run() {		
		try {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String line = stdin.readLine();
			if (line.equals(line.trim())) {
				System.out.println("true");
			}				
			CSPMessage cspm = new CSPMessage(line);

			handleCSP(cspm);
			System.err.println("START MP");
			handleMP(cspm.getProbes(), generatePayload(cspm.getPayloadSize()));
			System.err.println("START CTP");
			handleCTP();
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void handleCSP(CSPMessage cspm) throws IOException {
		sendLine(cspm.toString());
		String response = readLine();
	}
	
	private void handleMP(int probes, String payload) throws IOException {	
		
		rtts = new ArrayList<Long>();
		for (int i = 0; i < probes; i++) {
			
			writeAndTime(new MPMessage(i+1, payload));
			long start = tempTime;
			
			readAndTime();
			long stop = tempTime;
			
			rtts.add(stop-start);
		}
	}
	
	private void handleCTP() throws IOException {
		sendLine(new CTPMessage().toString());
		String response = readLine();
		close();
	}
	
	synchronized void writeAndTime(Message message) {
		sendLine(message.toString());
		tempTime = System.currentTimeMillis();
	}
	
	synchronized String readAndTime() throws IOException {
		String response = readLine();
		tempTime = System.currentTimeMillis();
		return response;
	}
	
	String generatePayload(int msize) {
		char[] payloadArray = new char[msize];
		Arrays.fill(payloadArray, 'g');
		return new String(payloadArray);
	}
}
