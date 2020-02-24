package com.nortoh.src.commands.tests;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.commands.Command;
import com.nortoh.src.input.Parameter;

public class TestCommand extends Command {

	public TestCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		
	}

}
