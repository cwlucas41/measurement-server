package com.chriswlucas.measure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chriswlucas.client_server_arch.AppHandler;
import com.chriswlucas.measure.message.*;
import com.chriswlucas.measure.message.CSPMessage.MeasureType;

public class MeasureClientHandler extends AppHandler{
	
	private int probes;
	private int payloadSize;
	private MeasureType mtype;
	private long tempTime;
	private List<Long> rtts;
	private BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

	public void run() {		
		try {

			if (handleCSP() && 
					handleMP(probes, generatePayload(payloadSize)) &&
					handleCTP()) {
				System.out.println(calculateResult(mtype));
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	void setUserIn(BufferedReader reader) {
		userIn = reader;
	}
	
	double calculateResult(MeasureType type) {	
		double averageRTTSeconds = rtts.stream().mapToDouble(a -> a).average().getAsDouble() / 1000;
		
		if (type == MeasureType.RTT) {
			return averageRTTSeconds;
		} else if (type == MeasureType.TPUT) {
			return payloadSize / averageRTTSeconds;
		} else {
			throw new IllegalArgumentException("Invalid measurment type");
		}
	}
	
	private boolean handleCSP() throws IOException {
		String line = userIn.readLine();
		CSPMessage cspm = new CSPMessage(line);
		probes = cspm.getProbes();
		payloadSize = cspm.getPayloadSize();
		mtype = cspm.getType();
		sendLine(cspm.toString());
		String response = readLine();
		if (response.contains("404 ERROR:")) {
			System.out.println("Invalid response for CSP");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean handleMP(int probes, String payload) throws IOException {	
		
		rtts = new ArrayList<Long>();
		for (int i = 0; i < probes; i++) {
			MPMessage message = new MPMessage(i+1, payload);
			writeAndTime(message);
			long start = tempTime;
			String response = readAndTime();
			long stop = tempTime;
			rtts.add(stop-start);
			if (response.contains("404 ERROR:") || !response.equals(message.toString())) {
				System.out.println("Invalid response for MP");
				return false;
			}
		}
		return true;
	}
	
	private boolean handleCTP() throws IOException {
		sendLine(new CTPMessage().toString());
		String response = readLine();
		if (response.contains("200 OK:")) {
			return true;
		} else {
			System.out.println("Invalid response for CTP");
			return false;
		}
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
