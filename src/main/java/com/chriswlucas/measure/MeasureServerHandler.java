package com.chriswlucas.measure;

import java.io.IOException;

import com.chriswlucas.client_server_arch.AppHandler;
import com.chriswlucas.measure.message.*;

public class MeasureServerHandler extends AppHandler {
	
	int probes;
	int payloadSize;
	int delay;

	@Override
	public void run() {
		try {
			
			handleCSP();
			handleMP();
			handleCTP();
			
		} catch (IllegalArgumentException e) {
			sendLine("404 ERROR: " + e.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void handleCSP() throws IOException, IllegalArgumentException {
		
		String m = readLine();
		CSPMessage message = new CSPMessage(m);
		
		probes = message.getProbes();
		payloadSize = message.getPayloadSize();
		delay = message.getDelay();
		sendLine("200 OK: Ready");
	}
	
	void handleMP() throws IOException, InterruptedException, IllegalArgumentException {
		for (int i = 0; i < probes; i++) {
			MPMessage message = new MPMessage(readLine());
			
			System.err.println("Seq: " + (i+1) + " " + message.getSequenceNumber());
			if (message.getSequenceNumber() == (i+1) && message.getPayload().length() == payloadSize) {
				Thread.sleep(delay);
				sendLine(message.toString());
				System.err.println("Echo: " + message.toString());
			} else {
				throw new IllegalArgumentException(message.getErrorMessage());
			}
		}
	}
	
	void handleCTP() throws IOException, IllegalArgumentException {
		new CTPMessage(readLine());
		sendLine("200 OK: Closing Connection");
	}

}
