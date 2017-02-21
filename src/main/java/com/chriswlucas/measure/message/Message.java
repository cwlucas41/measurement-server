package com.chriswlucas.measure.message;

/**
 * Generic message with format checking
 * @author cwlucas41
 *
 */
abstract public class Message {
	
	/**
	 * Default constructor
	 */
	public Message() {
	}
	
	/**
	 * Constructor that parses message for proper format
	 * @param message
	 * @throws IllegalArgumentException
	 */
	public Message(String message) throws IllegalArgumentException {
		checkFormat(message);
	}

	/**
	 * Gets a regular expression for the message format
	 * @return regex
	 */
	abstract String getMessageFormat();
	
	/**
	 * Gets error message for improper format
	 * @return
	 */
	abstract public String getErrorMessage();
	
	/**
	 * Checks the format of a string and throws exception if it doesn't match.
	 * @param message
	 * @throws IllegalArgumentException
	 */
	private void checkFormat(String message) throws IllegalArgumentException {
		if (!message.matches(getMessageFormat())) {
			throw new IllegalArgumentException(getErrorMessage());
		}
	}
	
	/**
	 * Printable version of message. Should be the input string if message
	 * was constructed with Constructor with string paramater for message.
	 */
	abstract public String toString();
}
