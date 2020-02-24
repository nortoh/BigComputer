package com.nortoh.src.input;

/*
 * 
 * 
 * 
 */
public class WordCorrector {

	
	private String compareFrom;
	private String compareWith;
	
	public WordCorrector(String compareFrom, String compareWith) {
		this.compareFrom = compareFrom;
		this.compareWith = compareWith;
	}
	
	public boolean isClose() {
		boolean isClose = false;
		
		int compareFromLen = compareFrom.length();
		int compareWithLen = compareWith.length();
		
		for(int cfi = 0; cfi < compareFromLen; cfi++) {
			
		}
		
		
		
		return isClose;
	}
	
	
	
}
