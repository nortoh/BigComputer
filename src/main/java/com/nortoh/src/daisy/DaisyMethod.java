package com.nortoh.src.daisy;

public abstract class DaisyMethod {
	
	private String methodName;
	
	public DaisyMethod(String methodName) {
		this.methodName = methodName;
	}
	
	
	public abstract void execute();
	
	public String methodName() {
		return this.methodName;
	}
	
	public void printConsole(Object object) {
            System.out.println("[ds " + methodName() + "] " + object);
	}
}
