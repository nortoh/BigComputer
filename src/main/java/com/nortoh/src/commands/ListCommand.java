package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.Iterator;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;

public class ListCommand extends Command {

	public ListCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		ArrayList<Command> commands = getComputer().getCommands();
		Iterator<Command> iterator = commands.iterator();
		
		
		printConsole("~=~=~=~= Available Commands ~=~=~=~=");
		
		while(iterator.hasNext()) {
			Command command = (Command) iterator.next();
			printConsole(command.getCommandName() + " - " + command.getUsage());
		}
	}

}
