package com.nortoh.src.math;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class InfiniteSeries extends Series {

    private static final int MAX = 10000000;

    /**
     * Creates a new infinite series
     *
     * @param expression
     */
    public InfiniteSeries(Expression expression) {
        super(expression);
    }

    /**
     * Returns the sum of the series with specified limits
     *
     * @param lowerLimit
     * @param upperLimit
     * @return
     */
    @Override
    public double sum() {
        Summation summation = new Summation(sequence, lowerLimit, MAX).setVariable(variable);
        return summation.calculate();
    }

    public static Series ZETA_FUNCTION;
    public static Series HORTON_FUNCTION;

    static {
        ZETA_FUNCTION = new InfiniteSeries(new ExpressionBuilder("1/x^s").variable("x").variable("s").build());
        HORTON_FUNCTION = new InfiniteSeries(new ExpressionEvaluator(new ExpressionBuilder("1/x^(hSum(s))").variable("x").variable("s"), null, null).getExpression());
    }
}
