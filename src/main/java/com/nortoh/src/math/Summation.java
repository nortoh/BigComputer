package com.nortoh.src.math;

import java.util.Map;
import java.util.Set;

import net.objecthunter.exp4j.Expression;

public class Summation {

    private Expression expression;
    private double lowerLimit;
    private double upperLimit;
    private char variable;
    private Set<String> variables;
    private Map<String, Double> variableData;

    /**
     * Create a new summation using an exp4j expression
     *
     * @param expression
     * @param lowerLimit
     * @param upperLimit
     * @param variable
     */
    public Summation(Expression expression, double lowerLimit, double upperLimit) {
        this.expression = expression;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.variable = 'x';
    }

    public Summation(Expression expression, double lowerLimit, double upperLimit, Set<String> variables, Map<String, Double> variableData) {
        this.expression = expression;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.variables = variables;
        this.variableData = variableData;
        this.variable = 'x';
    }

    /**
     * Calculates the sum of an expression
     *
     * @return
     */
    public double calculate() {
        double result = 0.0; // O(1)

        if (expression != null) {
            for (double index = lowerLimit; index <= upperLimit; index++) { // O(lowerLimit)
                expression.setVariable(Character.toString(variable), index);
                double y = expression.evaluate();
                result += y;
            }
        }

        return result;
    }

    public Summation setVariable(char variable) {
        this.variable = variable;
        return this;
    }

    /**
     * Returns the upper limit
     *
     * @return
     */
    public double getUpperLimit() {
        return upperLimit;
    }

    /**
     * Returns the lower limit
     *
     * @return
     */
    public double getLowerLimit() {
        return lowerLimit;
    }
}
