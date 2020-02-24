package com.nortoh.src.commands;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;

public class GetValueCommand extends Command {
	
	public GetValueCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {	
		boolean hasValue = checkForParam(params, "value");
		boolean hasSigned = checkForParam(params, "sign");

	}

	private boolean checkForParam(ArrayList<Parameter> params, String keyword) {
		return params.contains(keyword);
	}

}
