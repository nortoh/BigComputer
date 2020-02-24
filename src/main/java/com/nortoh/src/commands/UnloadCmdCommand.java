package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.Iterator;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;

public class UnloadCmdCommand extends Command {
	
	public UnloadCmdCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		String commandName = (String) params.get(0).value();
		ArrayList<Command> loadedCommands = getComputer().getCommands();
		Iterator<Command> commandIterator = loadedCommands.iterator();
		
		int index = 0;
		boolean found = false;
		while(commandIterator.hasNext() & !found) {
			Command currCommand = commandIterator.next();
			index++;
			if(currCommand.getCommandName().equalsIgnoreCase(commandName)) {
				getComputer().getCommands().remove(index);
				printConsole("Unloaded successfully!");
				found = true;
			}
		}
		
		if(!found) {
			printConsole("Could not find " + commandName);
		}
		
	}

}
