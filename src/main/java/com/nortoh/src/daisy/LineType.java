package com.nortoh.src.daisy;

public enum LineType {

	EXPRESSION("expression"),
	VARIABLE_DECLERATION("variable_decleration"),
	LOOP("loop"),
	BRLOOP("brloop"),
	END("end"),
	UNKNOWN("unknown");
	
	String lineName;
	
	LineType(String lineName) {
		this.lineName = lineName;
	}
	
	public String getLineName() {
		return lineName;
	}
}
