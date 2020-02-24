package com.nortoh.src.math;

import net.objecthunter.exp4j.Expression;

public class Product {

	protected Expression sequence;
	protected int lowerLimit;
	protected int upperLimit;
	private char variable;
	
	/**
	 * Creates a new product with an upper and lower limit of 1
	 * @param expression
	 */	
	public Product(Expression sequence) {
		this.sequence = sequence;
		this.lowerLimit = 1;
		this.upperLimit = 1;
		this.variable = 'x';
	}
	
	/**
	 * Returns the sum of the series with specified limits
	 * @param lowerLimit
	 * @param upperLimit
	 * @return
	 */
	public double sum() {
		MultiplicationSummation summation = new MultiplicationSummation(sequence, lowerLimit, upperLimit, variable);
		return summation.calculate();
	}
	
	/**
	 * Set the upper limit of the series
	 * @param upperLimit
	 * @return
	 */
	public Product setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
		return this;
	}
	
	/**
	 * Set the lower limit of the series
	 * @param lowerLimit
	 * @return
	 */
	public Product setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
		return this;
	}
	
	public Product setVariable(char variable) {
		this.variable = variable;
		return this;
	}
	
	/**
	 * Return the sequence expression
	 * @return
	 */
	public Expression getSequence() {
		return sequence;
	}
	
}
