package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.Map;

import com.nortoh.src.BigComputer;
import com.nortoh.src.datatypes.Binary;
import com.nortoh.src.input.InputHandler.InputType;
import com.nortoh.src.input.Parameter;

/**
 * 
 * Syntax: getBin value
 * 
 * Parameters: [value=integer]
 * 
 * Description: Returns the binary representation of the inputted value
 * 
 * @author christian
 *
 */
public class GetBinCommand extends Command {

	public GetBinCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
		this.setUsage("getBin m where m is an integer");
	}

	/*
	public void execCommand(Map<String, String> params) { 
		if(!params.containsKey("param0")) {
			printConsole(getUsage());
			return;
		}

		boolean isNegative = params.get("param0").startsWith("-");
		System.out.println(getComputer().getInputHandler().getInputType( params.get("param0")));
		try {
			int value = Integer.parseInt(params.get("param0"));

			if(isNegative) {
				Binary signedBin = new Binary(value);
				System.out.println(signedBin);
				return;
			}

			Binary unsignedBin = new Binary(value);
			System.out.println(unsignedBin);
		} catch(NumberFormatException e) {
			printError("[value] must be a integer.");
		}
	}
	*/

	@Override
	public void execCommand(ArrayList<Parameter> params) {
			String value = (String) params.get(0).value();
			InputType type = params.get(0).type();
			Binary binaryValue = null;
			
			if(type.equals(InputType.BINARY)) {
				int valueBinary = trimToBinary(value);
				binaryValue = new Binary(valueBinary, InputType.BINARY);
			} else if(type.equals(InputType.HEXADECIMAL)) {
				
			} else if(type.equals(InputType.DECIMAL)) {
				int valueDecimal = Integer.parseInt(value);
				binaryValue = new Binary(valueDecimal, InputType.DECIMAL);
			}
			
			System.out.println(binaryValue);
	}

	private int trimToBinary(Object value) {
		String valueString = (String) value;
		return Integer.parseInt(valueString.substring(1, valueString.length()));
	}
}
