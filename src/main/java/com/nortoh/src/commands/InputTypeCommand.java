package com.nortoh.src.commands;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;

public class InputTypeCommand extends Command {

	public InputTypeCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
		this.setUsage("inputType m - where m is an object.");
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		if(params.get(0) != null) {
		
			return;
		}
		
		printConsole("Please provide an object to check.");
		
	}

}
