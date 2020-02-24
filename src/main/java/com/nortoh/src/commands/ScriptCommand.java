package com.nortoh.src.commands;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;

public class ScriptCommand extends Command {

	public ScriptCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		String script = (String) params.get(0).value();
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			Object result = engine.eval(script);
			printConsole(result);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
}
