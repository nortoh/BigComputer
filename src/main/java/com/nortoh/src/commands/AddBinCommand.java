package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.Map;

import com.nortoh.src.BigComputer;
import com.nortoh.src.datatypes.Binary;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.input.InputHandler.InputType;

public class AddBinCommand extends Command {

	public AddBinCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
		setUsage("addBin m n - Where m and n are integer values and m || n < 65536");
	}


	public void execCommand(Map<String, String> params) {
		if(!params.containsKey("param0") || !params.containsKey("param1")) {
			printError(getUsage());
			return;
		}
		
		int inputA = Integer.parseInt(params.get("param0"));
		int inputB = Integer.parseInt(params.get("param1"));
		
		Binary firstValue = new Binary(inputA, InputType.DECIMAL);
		Binary secondValue = new Binary(inputB, InputType.DECIMAL);
		Binary result = firstValue.addBinary(secondValue);
		System.out.println(result);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		// TODO Auto-generated method stub
		
	}
}