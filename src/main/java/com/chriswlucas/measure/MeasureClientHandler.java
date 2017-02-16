package com.chriswlucas.measure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.chriswlucas.client_server_arch.AppHandler;
import com.chriswlucas.measure.message.*;
import com.chriswlucas.measure.message.CSPMessage.MeasureType;

public class MeasureClientHandler extends AppHandler{
	
	int probes;
	int payloadSize;
	MeasureType mtype;
	private long tempTime;
	private List<Long> rtts;

	public void run() {		
		try {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String line = stdin.readLine();
			
			CSPMessage cspm = new CSPMessage(line);
			probes = cspm.getProbes();
			payloadSize = cspm.getPayloadSize();
			mtype = cspm.getType();
			
			handleCSP(cspm);
			handleMP(probes, generatePayload(payloadSize));
			handleCTP();
			
			System.out.println(calculateResult(mtype));
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	double calculateResult(MeasureType type) {	
		double averageRTTSeconds = rtts.stream().mapToDouble(a -> a).average().getAsDouble() / 1000;
		
		if (type == MeasureType.RTT) {
			return averageRTTSeconds;
		} else if (type == MeasureType.TPUT) {
			return payloadSize / averageRTTSeconds;
		} else {
			throw new IllegalArgumentException();
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
			String response = readAndTime();
			long stop = tempTime;
			rtts.add(stop-start);
		}
	}
	
	private void handleCTP() throws IOException {
		sendLine(new CTPMessage().toString());
		String response = readLine();
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
