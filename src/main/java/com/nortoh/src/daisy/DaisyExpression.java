package com.nortoh.src.daisy;

import com.nortoh.src.math.ExpressionEvaluator;

/**
 * 
 * Rewrite expressions as a functioin
 * 
 * function({"func":"1/x", "vars", "x"});
 * 
 * @author christian
 *
 */
public class DaisyExpression extends DaisyMethod {

	private ExpressionEvaluator expressionEvaluator;
	
	public DaisyExpression(ExpressionEvaluator expressionEvaluator) {
		super("expression");
		this.expressionEvaluator = expressionEvaluator;
	}

	@Override
	public void execute() {
		printConsole("result: " + expressionEvaluator.evaluate());
	}

}
