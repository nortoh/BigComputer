package com.nortoh.src.math;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Series {
    
    private static final int MAX = 100000;

    protected Expression sequence;
    protected int lowerLimit;
    protected int upperLimit;
    protected char variable;

    /**
     * Creates a new series with an upper and lower limit of 1
     *
     * @param expression
     */
    public Series(Expression sequence) {
        this.sequence = sequence;
        this.lowerLimit = 1;
        this.upperLimit = 1;
        this.variable = 'x';
    }

    /**
     * Returns the sum of the series with specified limits
     *
     * @return
     */
    public double sum() {
        Summation summation = new Summation(sequence, lowerLimit, upperLimit).setVariable(variable);
        return summation.calculate();
    }

    /**
     * Set the upper limit of the series
     *
     * @param upperLimit
     * @return
     */
    public Series setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
        return this;
    }

    /**
     * Set the lower limit of the series
     *
     * @param lowerLimit
     * @return
     */
    public Series setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
        return this;
    }

    /**
     * Set the dependent variable for the series
     *
     * @param variable
     * @return
     */
    public Series setVariable(char variable) {
        this.variable = variable;
        return this;
    }

    /**
     * Return the sequence expression
     *
     * @return
     */
    public Expression getSequence() {
        return sequence;
    }

    public boolean nthTermTest() {
        boolean converge = false;

        for (int n = 0; n < 1000; n++) {
            sequence.setVariable("x", n);
            double value = sequence.evaluate();
            System.out.println(((int) value % 1));
        }

        return converge;
    }

    public static Series HERMONIC_SERIES;

    static {
        HERMONIC_SERIES = new Series(new ExpressionBuilder("1/x").variable("x").build());
    }
}
