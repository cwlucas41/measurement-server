package com.chriswlucas.client_server_arch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public abstract class AppHandler implements Runnable{
	
	private Optional<PrintWriter> socketOut;
	private Optional<BufferedReader> socketIn;
	
	
	public void setSocket(Socket socket) {		
		try {
			setWriter(new PrintWriter(socket.getOutputStream(), true));
			setReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setWriter(PrintWriter writer) {
		socketOut = Optional.of(writer);
	}
	
	public void setReader(BufferedReader reader) {
		socketIn = Optional.of(reader);
	}
	
	public PrintWriter getWriter() {
		return socketOut.orElseThrow(() -> new IllegalStateException("Writer not set"));
	}
	
	public BufferedReader getReader() {
		return socketIn.orElseThrow(() -> new IllegalStateException("Reader not set"));
	}
	
	public void sendLine(String line) {
		line = line.trim();
		getWriter().println(line);
	}
	
	public String readLine() throws IOException {
		String line = getReader().readLine();
		return line.trim();
	}

}
