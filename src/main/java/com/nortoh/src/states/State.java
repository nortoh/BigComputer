package com.nortoh.src.states;

public class State {

	private Type currentState;
	
	public State(Type type) {
		this.currentState = type;
	}
	
	/**
	 * Set the state of the computer
	 * @param type
	 */
	public void setType(Type type) {
		this.currentState = type;
	}
	
	/**
	 * Return the current state of the computer
	 * @return
	 */
	public Type getType() {
		return currentState;
	}
	
	public boolean shutdownOveride() {
		boolean allowed = 
				(currentState == Type.HISTORY) ||
				(currentState == Type.TRUTH_TABLE_GENERATOR);
		return allowed;
	}
	
	public enum Type {
	
		NORMAL(1),
		HISTORY(10),
		TRUTH_TABLE_GENERATOR(11),
		ARITHMETIC(12);
		
		int typeMode;
		
		Type(int typeMode) {
			this.typeMode = typeMode;
		}
		
	}
}
