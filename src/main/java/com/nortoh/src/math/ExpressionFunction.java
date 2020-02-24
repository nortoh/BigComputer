package com.nortoh.src.math;

import net.objecthunter.exp4j.function.Function;

/* 
 * I want to be able to have custom eval functions that allow a passable expression.
 * 
 * Maybe we can try to extend the expression class by creating this sub-class.
 * We can then create a new apply method that allows for Strings as possible input.

 */

public abstract class ExpressionFunction extends Function {

	public ExpressionFunction(String name, int numArguments) {
		super(name, numArguments);
	}

	@Override
	public double apply(double... arg0) {
		return 0;
	}
	
	public abstract double apply(String... args);

}
