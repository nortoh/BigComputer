package com.nortoh.src.commands;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import com.nortoh.src.BigComputer;
import com.nortoh.src.daisy.DaisyScript;
import com.nortoh.src.exceptions.ScriptSyntaxErrorException;
import com.nortoh.src.input.Parameter;

public class LoadDSCommand extends Command {

	public LoadDSCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		String filePath = (String) params.get(0).value();
		DaisyScript dScript = new DaisyScript(filePath, this.getComputer());
		
		// Check if file exists
		if(!dScript.exists()) {
			printError("File not found.");
			return;
		}
		
		printConsole("Accessing " + dScript.getAbsolutePath());
		
		// Check the file extension
		String extension = FilenameUtils.getExtension(filePath);
                
		if(!extension.equalsIgnoreCase("ds")) {
			printError("File is not a daisy script (.ds)");
			return;
		}
		
		printConsole("Found Daisy Script.");
		
		printConsole("Evaluating...");
//		dScript.evaluateScript();
		
		try {
			dScript.processScript();
		} catch (IOException e) { 
			e.printStackTrace();
		} catch (ScriptSyntaxErrorException e) {
			System.out.println(e.getMessage());
		}
		dScript.evaluateScript();
	}
}


