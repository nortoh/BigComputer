package com.nortoh.src.math;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ComplexFunctions {

    public static double gammaFunction(double s) {
        Expression expression = new ExpressionBuilder("(x^s)*(e^(-x))")
                .variables("x")
                .variables("s")
                .build()
                .setVariable("s", s - 1);
        System.out.println("Gamma of " + (s - 1));
        RiemannSummation gammaSummation = new RiemannSummation(expression, 1, 1000000000);

        return gammaSummation.calculate();
    }

}
