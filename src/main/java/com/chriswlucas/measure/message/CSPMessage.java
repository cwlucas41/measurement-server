package com.chriswlucas.measure.message;

/**
 * Connection Setup Phase message - first message phase of measurment server.
 * @author cwlucas41
 *
 */
public class CSPMessage extends Message{
	
	private MeasureType type;
	private int probes;
	private int payloadSize;
	private int delay;
	private String message;
	
	private static final String rttFormat = "s\\srtt\\s\\d+\\s\\d+(\\s\\d+)?";
	private static final String tputFormat = "s\\stput\\s\\d+\\s\\d+K(\\s\\d+)?";
	private static final String errorMessage = "Invalid Connection Setup Message";

	/**
	 * Constructs message from parsed String. Checks if String is valid.
	 * @param message
	 * @throws IllegalArgumentException
	 */
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
			if (fields.length == 5) {
				delay = Integer.parseInt(fields[4]);
			} else {
				delay = 0;
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(getErrorMessage());
		}
		
		// payload field is different for different message types
		String rawPayloadSize = fields[3];
		if (type == MeasureType.TPUT) {
			String withoutK = rawPayloadSize.substring(0,  rawPayloadSize.length()-1);
			payloadSize = Integer.parseInt(withoutK);
			if (!(payloadSize == 1 || payloadSize == 2 || payloadSize == 4 ||
					payloadSize == 8 || payloadSize == 16 || payloadSize == 32)) {
				throw new IllegalArgumentException(getErrorMessage());
			}
			payloadSize *= 1000;
		} else {
			payloadSize = Integer.parseInt(rawPayloadSize);
			if (!(payloadSize == 1 || payloadSize == 100 || payloadSize == 200 ||
					payloadSize == 400 || payloadSize == 800 || payloadSize == 1000)) {
				throw new IllegalArgumentException(getErrorMessage());
			}
		}
		
		this.message = message;
	}
	
	/**
	 * Enumerates the types of CSP messages
	 * @author cwlucas41
	 *
	 */
	public enum MeasureType {
		RTT, TPUT
	}

	/**
	 * gets the messages MeasureType
	 * @return
	 */
	public MeasureType getType() {
		return type;
	}

	/**
	 * Gets the number of probes to be sent
	 * @return
	 */
	public int getProbes() {
		return probes;
	}

	/**
	 * Gets the byte size of the payload of messages
	 * @return
	 */
	public int getPayloadSize() {
		return payloadSize;
	}
	
	/**
	 * Gets the server delay for the messages
	 * @return
	 */
	public int getDelay() {
		return delay;
	}
	
	@Override
	public String toString() {
		return message;
	}

	@Override
	String getMessageFormat() {
		return rttFormat + "||" + tputFormat;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
