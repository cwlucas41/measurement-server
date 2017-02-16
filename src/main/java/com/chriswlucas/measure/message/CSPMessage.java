package com.chriswlucas.measure.message;

public class CSPMessage extends Message{
	
	private MeasureType type;
	private int probes;
	private int payloadSize;
	private int delay;
	private String message;
	
	private static final String format = "s\\s(rtt||tput)\\s\\d+\\s\\d+\\s\\d+";
	private static final String errorMessage = "Invalid Connection Setup Message";

	public CSPMessage(String message) throws IllegalArgumentException {
		super(message);
		
		String[] fields = message.split("\\s");
		
		if (fields[1].equals("rtt")) {
			type = MeasureType.RTT;
		} else if (fields[1].equals("tput")) {
			type = MeasureType.TPUT;
		} else {
			throw new IllegalArgumentException(getErrorMessage());
		}
		
		try {
			probes = Integer.parseInt(fields[2]);
			payloadSize = Integer.parseInt(fields[3]);
			delay = Integer.parseInt(fields[4]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(getErrorMessage());
		}
		
		this.message = message;
	}
	
	public enum MeasureType {
		RTT, TPUT
	}

	public MeasureType getType() {
		return type;
	}

	public int getProbes() {
		return probes;
	}

	public int getPayloadSize() {
		return payloadSize;
	}
	
	public int getDelay() {
		return delay;
	}
	
	@Override
	public String toString() {
		return message;
	}

	@Override
	String getMessageFormat() {
		return format;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
