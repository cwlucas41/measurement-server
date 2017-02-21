package com.chriswlucas.measure.message;

/**
 * Connection Termination Phase message - third phase of measurment server.
 * @author cwlucas41
 *
 */
public class CTPMessage extends Message {
	
	private String message = "t";
	private static final String format = "t";
	private static final String errorMessage = "Invalid Connection Termination Message";
	
	/**
	 * Creates new message
	 */
	public CTPMessage() {
	}
	
	/**
	 * Creates new message from String. Checks if String is valid.
	 * @param message
	 */
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
