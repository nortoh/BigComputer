package com.nortoh.src.daisy;

import java.util.Map;
import java.util.Set;

import com.nortoh.src.math.ExpressionEvaluator;
import com.nortoh.src.math.Summation;

public class DaisySum extends DaisyMethod {

	private ExpressionEvaluator expressionEval;
	private double lowerBound;
	private double upperBound;
	private String loopVar;
	private Set<String> variables;
	private Map<String, Double> variableData;

	public DaisySum(ExpressionEvaluator expressionEval, double lowerBound, double upperBound, Set<String> variables, Map<String, Double> variableData) {
		super("sum");
		this.loopVar = "x";
		this.expressionEval = expressionEval;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.variables = variables;
		this.variableData = variableData;
	}


	public void execute() {
		Summation summation = new Summation(expressionEval.getExpression(), lowerBound, upperBound, variables, variableData);
		printConsole(summation.calculate());
	}
}
