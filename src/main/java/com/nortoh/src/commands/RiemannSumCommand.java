package com.nortoh.src.commands;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.math.RiemannSummation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class RiemannSumCommand extends Command {

	public RiemannSumCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
		this.setFunctionName("rSum");
		this.setUsage("rSum expr a b"
				+	 "\nexpr = Math Expression");
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		String expressionStr = (String) params.get(0).value();
		double a = Double.parseDouble((String) params.get(1).value());
		double b = Double.parseDouble((String) params.get(2).value()); 

		Expression expression = new ExpressionBuilder(expressionStr).variables("x").build();

		RiemannSummation riemannSum = new RiemannSummation(expression, a, b);
		printConsole(riemannSum.calculate());
	}
}
