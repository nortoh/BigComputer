package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.Map;

import com.nortoh.src.BigComputer;
import com.nortoh.src.datatypes.Hexadecimal;
import com.nortoh.src.input.Parameter;

public class GetHexCommand extends Command {

	public GetHexCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
		this.setUsage("getHex m - Where m is a positive integer.");
	}

	public void execCommand(Map<String, String> params) { 
		if(!params.containsKey("param0")) {
			printConsole(getUsage());
			return;
		}

		try {
			int value = Integer.parseInt(params.get("param0"));
			Hexadecimal hex = new Hexadecimal(value);

			if(params.containsKey("param1") && params.get("param1").equalsIgnoreCase("asBin")) {
				printConsole(hex.asDecimal());
			}

			printConsole(hex);
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		int value = (int) params.get(0).value();
		System.out.println(value);
	}
}
