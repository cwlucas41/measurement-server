package com.chriswlucas.measure;

public class MeasureProtocol {
	
	static String CSPFormat = "s\\s+(rtt||tput)\\s+\\d+\\s+\\d+\\s+\\d+";

	static void parseCSPMessage(String message) throws IllegalArgumentException {
		if (!message.matches(CSPFormat)) {
			throw new IllegalArgumentException("Invalid string format");
		}
	}
	
}
