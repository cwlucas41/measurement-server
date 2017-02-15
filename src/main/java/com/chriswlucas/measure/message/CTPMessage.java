package com.chriswlucas.measure.message;

public class CTPMessage extends Message {
	
	private String message = "t";
	private static final String format = "t";
	private static final String errorMessage = "Invalid Connection Termination Message";
	
	public CTPMessage() {
	}
	
	public CTPMessage(String message) {
		super(message);
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
