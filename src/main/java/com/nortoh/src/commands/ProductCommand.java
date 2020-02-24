package com.nortoh.src.commands;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.math.Product;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ProductCommand extends Command {

	public ProductCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
		this.setUsage("product expression[i] lowerLimit upperLimit - Gives the product of the sequence from [a,b]");
		
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		String expressionStr = (String) params.get(0).value();
		int lowerLimit = Integer.parseInt((String) params.get(1).value());
		int upperLimit = Integer.parseInt((String) params.get(2).value());

		Expression expression = new ExpressionBuilder(expressionStr).variables("x").build();

		try {
			Product series = new Product(expression).setLowerLimit(lowerLimit).setUpperLimit(upperLimit).setVariable('x');
			printConsole(series.sum());
		} catch(Exception e) {
			printError(e.getMessage());
		}

	}
}
