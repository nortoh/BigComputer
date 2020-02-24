package com.nortoh.src.daisy;

public class DaisyEnd extends DaisyMethod {

	private DaisyScript script;

	public DaisyEnd(DaisyScript script) {
		super("end");
		this.script = script;
	}

	@Override
	public void execute() {
		this.script.setExecuting(false);
		printConsole("SCRIPT ENDED");
	}

}
