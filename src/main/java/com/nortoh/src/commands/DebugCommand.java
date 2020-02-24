package com.nortoh.src.commands;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;

public class DebugCommand extends Command {

	public DebugCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {

		BigComputer.DEBUG = !BigComputer.DEBUG;
		
	}

}
