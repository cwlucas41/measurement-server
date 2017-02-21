package com.chriswlucas.measure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.chriswlucas.client_server_arch.AppHandler;
import com.chriswlucas.measure.message.*;

public class MeasureServerHandler extends AppHandler {
	
	public MeasureServerHandler(BufferedReader reader, PrintWriter writer) {
		super(reader, writer);
	}

	int probes;
	int payloadSize;
	int delay;

	@Override
	public void run() {
		try {
			
			if (handleCSP() && handleMP() && handleCTP()) {}
			
		} catch (IllegalArgumentException e) {
			sendLine("404 ERROR: " + e.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	boolean handleCSP() throws IOException, IllegalArgumentException {
		
		String m = readLine();
		if (m == null) {
			return false;
		}
		CSPMessage message = new CSPMessage(m);
		
		probes = message.getProbes();
		payloadSize = message.getPayloadSize();
		delay = message.getDelay();
		sendLine("200 OK: Ready");
		return true;
	}
	
	boolean handleMP() throws IOException, InterruptedException, IllegalArgumentException {
		for (int i = 0; i < probes; i++) {
			String line = readLine();
			if (line == null) {
				return false;
			}
			MPMessage message = new MPMessage(line);
			if (message.getSequenceNumber() == (i+1) && message.getPayload().length() == payloadSize) {
				Thread.sleep(delay);
				sendLine(message.toString());
			} else {
				throw new IllegalArgumentException(message.getErrorMessage());
			}
		}
		return true;
	}
	
	boolean handleCTP() throws IOException, IllegalArgumentException {
		String line = readLine();
		if (line == null) {
			return false;
		}
		new CTPMessage(line);
		sendLine("200 OK: Closing Connection");
		return true;
	}

}
