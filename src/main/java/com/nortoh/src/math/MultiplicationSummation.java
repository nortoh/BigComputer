package com.nortoh.src.math;

import net.objecthunter.exp4j.Expression;

public class MultiplicationSummation {

    private Expression exp4jexpression;
    private int lowerLimit;
    private int upperLimit;
    private char variable;

    /**
     * Create a new multiplication summation using an exp4j expression
     *
     * @param exp4jexpression
     * @param lowerLimit
     * @param upperLimit
     */
    public MultiplicationSummation(Expression exp4jexpression, int lowerLimit, int upperLimit, char variable) {
        this.exp4jexpression = exp4jexpression;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.variable = variable;
    }

    /**
     * Calculates the sum of an expression
     *
     * @return
     */
    public double calculate() {
        double result = 1.0;

        if (exp4jexpression != null) {
            for (int index = lowerLimit; index <= upperLimit; index++) {
                exp4jexpression.setVariable(Character.toString(variable), index);
                double y = exp4jexpression.evaluate();
                result *= y;
            }
        }
        return result;
    }

    /**
     * Returns the upper limit
     *
     * @return
     */
    public int getUpperLimit() {
        return upperLimit;
    }

    /**
     * Returns the lower limit
     *
     * @return
     */
    public int getLowerLimit() {
        return lowerLimit;
    }
}
