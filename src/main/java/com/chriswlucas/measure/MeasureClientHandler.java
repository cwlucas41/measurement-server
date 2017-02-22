package com.chriswlucas.measure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chriswlucas.client_server_arch.AppHandler;
import com.chriswlucas.measure.message.*;
import com.chriswlucas.measure.message.CSPMessage.MeasureType;

/**
 * Business logic for measure client. The client takes a connection setup
 * phase message from the user and performs the requested measurement if
 * the input message is valid.
 * @author cwlucas41
 *
 */
public class MeasureClientHandler extends AppHandler{

	private int probes;
	private int payloadSize;
	private MeasureType mtype;
	private long tempTime;
	private List<Long> rtts;
	private BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Creates the handler with a reader and writer
	 * @param reader
	 * @param writer
	 */
	public MeasureClientHandler(BufferedReader reader, PrintWriter writer) {
		super(reader, writer);
	}
	
	public void run() {		
		try {

			if (
					handleCSP() && 
					handleMP(probes, generatePayload(payloadSize)) &&
					handleCTP()
			) {
				System.out.format(getFormat(mtype), calculateResult(mtype));
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Sets the user input so that something other than the default stdin
	 * can be used. Useful for testing.
	 * @param reader
	 */
	protected void setUserIn(BufferedReader reader) {
		userIn = reader;
	}
	
	/**
	 * Calculates the average rtt time for rtt calulations, and average 
	 * throughput for tput calculations.
	 * @param measure type
	 * @return calculation
	 */
	double calculateResult(MeasureType type) {	
		double averageRTTSeconds = rtts.stream().mapToDouble(a -> a).average().getAsDouble() / 1000;
		
		if (type == MeasureType.RTT) {
			return averageRTTSeconds;
		} else if (type == MeasureType.TPUT) {
			return payloadSize * 8 / 1000 / averageRTTSeconds;
		} else {
			throw new IllegalArgumentException("Invalid measurment type");
		}
	}
	
	String getFormat(MeasureType type) {
		if (type == MeasureType.RTT) {
			return "average rtt: %.3d s";
		} else if (type == MeasureType.TPUT) {
			return "average tput: %.3d kbps";
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
		// saves rtts to array
		rtts = new ArrayList<Long>();
		for (int i = 0; i < probes; i++) {
			MPMessage message = new MPMessage(i+1, payload);
			writeAndTime(message);
			long start = tempTime;
			String response = readAndTime();
			long stop = tempTime;
			rtts.add(stop-start);
			if (!response.equals(message.toString())) {
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
	
	private synchronized void writeAndTime(Message message) {
		// synchronized to ensure accurate measurements
		sendLine(message.toString());
		tempTime = System.currentTimeMillis();
	}
	
	private synchronized String readAndTime() throws IOException {
		// synchronized to ensure accurate measurements
		String response = readLine();
		tempTime = System.currentTimeMillis();
		return response;
	}
	
	/**
	 * Generates a dummy payload of a given size
	 * @param msize
	 * @return
	 */
	static public String generatePayload(int msize) {
		char[] payloadArray = new char[msize];
		Arrays.fill(payloadArray, 'g');
		String payload = new String(payloadArray);
		return payload;
	}
}
