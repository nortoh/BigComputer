package com.nortoh.src.commands;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;

public class CConCommand extends Command {

	public CConCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
		this.setUsage("cCon [value] - returns the specific computer setting provided.");
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		String setting = (String) params.get(0).value();
		boolean hasValue = (params.get(1).value() != null);
	
			
		if(setting.equalsIgnoreCase("debug")) {
			if(hasValue) {
				return;
			}
			
		}
		
		
//		switch(setting) {
//		
//		/* Debug */
//		case "debug":
////			if(hasValue(params, "true") || hasValue(params, "false")) {
////				boolean state = (boolean) params.get(1).value();
////				Computer.DEBUG = state;
////				printConsole("Debug:" + Computer.DEBUG);
////				return;
////			}
//			printConsole("Debug:" + Computer.DEBUG);
//			break;
//		
//		
//		/* State */
//		case "state":
//			if(hasValue) {
//				return;
//			}
//			printConsole(getComputer().getState().getType());
//			break;
//			
//		/* Help */
//		case "help":
//			printConsole("=== Computer constant return ===");
//			printConsole(getUsage());
//			break;
//			
//			default:
//				printConsole("unknown: " + (String) params.get(1).value());
//				break;
//		}
	}
	
	private final boolean hasValue(ArrayList<Parameter> params, String value) {
		return ((String) params.get(1).value() == value) && params.get(1) != null;
	}

}
