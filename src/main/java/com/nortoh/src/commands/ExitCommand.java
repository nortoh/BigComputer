package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.Map;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.states.State;

public class ExitCommand extends Command {
	
	public ExitCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	public void execCommand(Map<String, String> params) {
		if(getComputer().getState().getType() == State.Type.NORMAL) { // not in normal mode
			System.out.println("Computer is already in state 0.");
		} else {
			System.out.println("Exiting state " + this.getComputer().getState());
			getComputer().getState().setType(State.Type.NORMAL);
		}
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		// TODO Auto-generated method stub
		
	}
}
