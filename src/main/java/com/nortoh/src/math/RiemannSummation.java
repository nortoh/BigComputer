package com.nortoh.src.math;

import java.util.Map;
import java.util.Set;

import net.objecthunter.exp4j.Expression;

public class RiemannSummation {

    private static final double N = 10000000;	// num of rectangles

    private double lowerLimit;	// lower limit
    private double upperLimit;		// upper limit
    private Expression expression;

    private Set<String> variables;
    private Map<String, Double> variableData;

    /**
     * Create a new Riemann summation using an exp4j expression
     *
     * @param expression
     * @param lowerLimit
     * @param upperLimit
     */
    public RiemannSummation(Expression expression, double lowerLimit, double upperLimit) {
        this.expression = expression;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    /**
     * Create a new Riemann summation using an exp4j expression
     *
     * @param expression
     * @param lowerLimit
     * @param upperLimit
     */
    public RiemannSummation(Expression expression, double lowerLimit, double upperLimit, Set<String> variables, Map<String, Double> variableData) {
        this.expression = expression;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.variables = variables;
        this.variableData = variableData;
    }

    /**
     * Calculates the Right Riemann sum of an expression
     *
     * @return
     */
    public double calculate() {
        double result = 0.0;

        // As N -> inf, deltaX converges to 0.
        double deltaX = (upperLimit - lowerLimit) / N; // how many triangles we have

        if (expression != null) {
            for (double index = lowerLimit; index <= upperLimit; index += deltaX) {
                expression.setVariable("x", index);
                double value = expression.evaluate();
                result += (value * deltaX);
            }
        }
        return result;
    }

    
    public double getLowerLimit() {
        return this.lowerLimit;
    }
    
    public double getUpperLimit() {
        return this.upperLimit;
    }
}
