package com.nortoh.src.input;

import com.nortoh.src.input.InputHandler.InputType;


/**
 * TODO:
 * 
 * Have parameter optimization happen inside this 
 * class to then have values be easily passable to 
 * either command or function classes. Removes the 
 * workload from InputHandler.java - May 7th, 2019
 * [Removed on May 11, 2019 - Replaced with exp4j]
 * 
 * @author christian
 *
 */
public class Parameter {
	
	private Object value;
	private InputType type;
	
	public Parameter(Object value, InputType type) {
		this.value = value;
		this.type = type;
	}
	
	public Object value() {
		
		return value;
	} 
	
	public InputType type() {
		return type;
	}
}
