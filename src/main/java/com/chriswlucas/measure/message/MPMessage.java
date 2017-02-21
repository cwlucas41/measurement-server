package com.chriswlucas.measure.message;

/**
 * Message Phase message - second phase of measurment server.
 * @author cwlucas41
 *
 */
public class MPMessage extends Message {
	
	private static final String format = "m\\s\\d+\\s\\w+";
	private static final String errorMessage = "Invalid Measurement Message";
	
	private int sequenceNumber;
	private String payload;
	private String message;

	/**
	 * Creates message from a string and checks string for valid format
	 * @param message
	 * @throws IllegalArgumentException
	 */
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
	
	/**
	 * Creates message from parameters and creates proper string
	 * @param sequenceNumber
	 * @param payload
	 */
	public MPMessage(int sequenceNumber, String payload) {
		this.sequenceNumber = sequenceNumber;
		this.payload = payload;
		this.message = "m " + sequenceNumber + " " + payload + '\n';
	}

	/**
	 * Gets the sequence number for the message.
	 * @return
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * Gets the payload fort the message
	 * @return
	 */
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
