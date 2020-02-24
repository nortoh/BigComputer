package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.function.Function;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.math.ComplexFunctions;
import com.nortoh.src.math.RiemannSummation;
import com.nortoh.src.math.Summation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SumTestCommand extends Command {

	public SumTestCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		String expressionStr = (String) params.get(0).value();

		Expression expression = new ExpressionBuilder(expressionStr).variables("x").build();
		
		try {
			Summation summation = new Summation(expression, 1, 10000).setVariable('x');
			printConsole(summation.calculate());
		} catch(Exception e) {
			printError(e.getMessage());
		}

	}
}
