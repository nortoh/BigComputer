package com.nortoh.src.math;

import java.util.Set;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/*
 * 
 * [X][X^X]
 * [T][]
 * [T][]
 * [F][]
 * [F][]
 * 
 */

public class TruthTable {

	private Set<String> variables;
	private Expression expression;
	private int[][] data;
	
	public TruthTable(Set<String> variables, String expression) {
		this.variables = variables;
		this.expression = new ExpressionEvaluator(new ExpressionBuilder(expression), variables, null).getExpression();
		this.data = new int[variables.size() + 1][(int) Math.pow(2, variables.size())];
	}
	
	/*
	 * Wtf does this even do? I forget
	 */
	public void evaluateTable() {
		int c = (int) Math.pow(2, variables.size());
		// populate
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data.length-1; j++) {
				int p = i % c;
				if(p < (c - 1) && p >= 0) data[i][j] = 1;
				if(p <= (c-1) && p > c) data[i][j] = 0;
				System.out.println("[" + i + "][" + j + "] = " + data[i][j]);
			}
		}
		
	}
	
	public Expression getExpression() {
		return expression;
	}
	
	public Set<String> getVariables() {
		return this.variables;
	}
}
