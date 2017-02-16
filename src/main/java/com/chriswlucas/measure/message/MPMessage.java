package com.chriswlucas.measure.message;

public class MPMessage extends Message {
	
	private static final String format = "m\\s\\d+\\s\\w+";
	private static final String errorMessage = "Invalid Measurement Message";
	
	private int sequenceNumber;
	private String payload;
	private String message;

	public MPMessage(String message) throws IllegalArgumentException {
		super(message);
		String[] fields = message.split("\\s");	
		try {
			sequenceNumber = Integer.parseInt(fields[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(getErrorMessage());
		}
		payload = fields[2];
		this.message = message;
	}
	
	public MPMessage(int sequenceNumber, String payload) {
		this.sequenceNumber = sequenceNumber;
		this.payload = payload;
		this.message = "m " + sequenceNumber + " " + payload + '\n';
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public String getPayload() {
		return payload;
	}
	
	@Override
	String getMessageFormat() {
		return format;
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}


}
