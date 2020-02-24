package com.nortoh.src.daisy;

import com.nortoh.src.BigComputer;

public class DaisySetOption extends DaisyMethod {

	private String option;
	private boolean optionVal;

	public DaisySetOption(String option, boolean optionVal) {
		super("set_o");
		this.option = option;
		this.optionVal = optionVal;
	}

	@Override
	public void execute() {
		if(BigComputer.options.containsKey(option) && BigComputer.options.get(option) != optionVal) {
			BigComputer.options.put(option, optionVal);
		}
	}

}
