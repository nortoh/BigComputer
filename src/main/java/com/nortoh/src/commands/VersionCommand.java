package com.nortoh.src.commands;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;

public class VersionCommand extends Command {

	public VersionCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		printConsole("=-=-=-=-=-= Christian's Java-Based Machine =-=-=-=-=-=");
		printConsole("Version: " + BigComputer.VERSION);
		printConsole("\n");
		printConsole("Source Code: N/A");
	}

}
