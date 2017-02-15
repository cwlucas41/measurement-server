package com.chriswlucas.measure.message;

abstract public class Message {
	
	public Message() {
	}
	
	public Message(String message) throws IllegalArgumentException {
		checkFormat(message);
	}

	abstract String getMessageFormat();
	
	abstract public String getErrorMessage();
	
	void checkFormat(String message) throws IllegalArgumentException {
		if (!message.matches(getMessageFormat())) {
			throw new IllegalArgumentException(getErrorMessage());
		}
	}
	
	abstract public String toString();
}
