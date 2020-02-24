package com.nortoh.src.exceptions;

public class ScriptSyntaxErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int line;
	private String message;
	
	public ScriptSyntaxErrorException(int line, String message) {
		this.line = line + 1; // push it up by 1 since we're starting from 0
		this.message = message;
	}
	
	public String toString() {
		return "Line " + line + ": " + message;
	}

	@Override
	public String getMessage() {
		return toString();
	}
}
