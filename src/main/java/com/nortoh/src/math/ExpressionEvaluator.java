package com.nortoh.src.math;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nortoh.src.BigComputer;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

public class ExpressionEvaluator {

    private DecimalFormat decimalFormat;

    private ExpressionBuilder expressionBuilder;
    private Expression expression;

    private Set<String> variables;
    private Map<String, Double> variableData;

    public ExpressionEvaluator(ExpressionBuilder expressionBuilder, Set<String> variables, Map<String, Double> variableData) {
        this.expressionBuilder = expressionBuilder;
        this.expressionBuilder.functions(getFunctions());
        this.expressionBuilder.operator(getOperators());

        // Built-in variables
        this.expressionBuilder.variables(Constants.constants.keySet());

        // Additional variables
        if (variables != null) {
            this.expressionBuilder.variables(variables);
        }

        try {
            this.expression = expressionBuilder.build();
        } catch (Exception e) {
            System.out.println("[Error] " + e.getMessage());
        }
        try {
            this.expression.setVariables(Constants.constants);
        } catch (NullPointerException e) {
            System.out.println("[Error] " + e.getMessage());
        }

        // Additional variable data
        if (variableData != null) {
            try {
                this.expression.setVariables(variableData);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        decimalFormat = new DecimalFormat("#.###########");
        decimalFormat.setRoundingMode(RoundingMode.UP);

        // Do this last because does it really matter?
        this.variables = variables;
        this.variableData = variableData;
    }

    public static double getResult(String expression, Set<String> variables, Map<String, Double> variableData) {
        return new ExpressionEvaluator(new ExpressionBuilder(expression), variables, variableData).evaluate();
    }

    /**
     * Evaluate the expression
     *
     * Value Convergence Test Formula written by Christian Horton
     *
     * @return
     */
    public double evaluate() {
        /* 
		 * Add some floor and ceiling support. 
		 * Makes number look cleaner when it's 
		 * converging to a whole number. 
		 * 
		 * Example: val = 50.00000000001
		 * 
		 * corrector: 10^(5) = 100000
		 * error: 10^(-5) 
		 * 
		 * val - floor(val) = 0.00000000001
		 * 0.00000000001 * corrector = 0.00001
		 * 
		 * 0.00001 / error <= 1 -> result is converging to the whole value
		 * 
		 * Result is 50
		 * 
		 * ------------------------------------------------------------
		 * 
		 * Example of a result failing this test
		 * 
		 * val = 3.14159265359
		 * 
		 * val - floor(val) = 0.14159265359
		 * 0.14159265359 * corrector = 14,159.265359
		 * 
		 * 14,159.265359 / error > 1 -> result is not converging to the whole value
		 * 
		 * Result is 3.14159265359
		 * 
		 * ------------------------------------------------------------
		 * 
		 * Note: This breaks after a certain threshold. With testing using the
		 * n-th term fibonacci formula, the 27th fibonacci (196418.00000000017)
		 * is no longer truncated to it's whole value
		 * 
		 * It is known that decreasing the size of the threshold, we can get better
		 * approximations that are closer to the whole value. Maybe the corrector 
		 * is unnecessary, but this will be tested later. Or, remove the corrector
		 * and rely on the error threshold but with a higher error check. 10^(-8)?
		 * 
         */

 /* Data */
        double result = Double.parseDouble(decimalFormat.format(expression.evaluate()));

        if (!BigComputer.options.get("converge_obvious_values")) {
            return result;
        }

        double floorResult = Math.floor(result);

        /* Parameters */
        double threshold = Math.pow(10, -6);
        double corrector = 100000;	// is this required?

        /* Computations */
        double resultRemainder = result - floorResult;
        double resultCorrected = resultRemainder * corrector;
        double alpha = resultCorrected / threshold;

        if (alpha <= 1) {
            result = floorResult;
        }

        if (BigComputer.options.get("debug")) {
            System.out.println("[val conv.] rRem: " + resultRemainder + " rCor: " + resultCorrected + " a: " + alpha);
        }

        return result;
    }

    /**
     * Return the expression builder
     *
     * @return
     */
    public ExpressionBuilder getExpressionBuilder() {
        return expressionBuilder;
    }

    public Expression getExpression() {
        return expression;
    }

    public Set<String> getVariables() {
        return variables;
    }

    public Map<String, Double> getVariableData() {
        return variableData;
    }

    private List<Operator> getOperators() {
        List<Operator> operators = new ArrayList<Operator>();

        /* 
		 * Factorial Operator
		 * Notation: !
		 * Example: 4! = 24 
		 * Restrictions: Integer only
         */
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        /* 
		 * Bitwise Or Operator
		 * Notation: |
		 * Example: 0|1 = 1, 0|0 = 0 
		 * Restrictions: Integer only
         */
        Operator bitwiseOr = new Operator("|", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
            @Override
            public double apply(double... values) {
                int a = (int) values[0];
                int b = (int) values[1];

                if ((double) a != values[0] || (double) b != values[1]) {
                    throw new IllegalArgumentException("Bitwise operators are for integers only");
                }
                if (!(a > -1 && a < 2) || !(b > -1 && b < 2)) {
                    throw new IllegalArgumentException("Bitwise operators are for binary values");
                }
                int c = a | b;
                return (double) c;
            }
        };

        /* 
		 * Bitwise And Operator
		 * Notation: &
		 * Example: 0&1 = 0, 1&1 = 1 
		 * Restrictions: Integer only
         */
        Operator bitwiseAnd = new Operator("&", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
            @Override
            public double apply(double... values) {
                int a = (int) values[0];
                int b = (int) values[1];

                if ((double) a != values[0] || (double) b != values[1]) {
                    throw new IllegalArgumentException("Bitwise operators are for integers only");
                }
                if (!(a > -1 && a < 2) || !(b > -1 && b < 2)) {
                    throw new IllegalArgumentException("Bitwise operators are for binary values");
                }
                int c = a & b;
                return (double) c;
            }
        };

        operators.add(factorial);
        operators.add(bitwiseOr);
        operators.add(bitwiseAnd);

        return operators;
    }

    /**
     * Load the functions into an array list
     *
     * @return
     */
    private List<Function> getFunctions() {
        List<Function> functions = new ArrayList<Function>();

        /* Hermonic Series */
        Function hermonicSum = new Function("hSum", 1) {
            @Override
            public double apply(double... doubles) {
                return Series.HERMONIC_SERIES.setUpperLimit((int) doubles[0]).sum();
            }
        };

        /* Horton Function */
        Function hortonFunction = new Function("horton", 1) {
            @Override
            public double apply(double... doubles) {
                InfiniteSeries.HORTON_FUNCTION.getSequence().setVariable("s", (int) doubles[0]);
                return InfiniteSeries.HORTON_FUNCTION.sum();
            }
        };

        /* Riemann Zeta Function */
        Function rZeta = new Function("rZeta", 1) {
            @Override
            public double apply(double... doubles) {
                InfiniteSeries.ZETA_FUNCTION.getSequence().setVariable("s", (int) doubles[0]);
                return InfiniteSeries.ZETA_FUNCTION.sum();
            }
        };

        /* Gamma Function */
        Function gamma = new Function("gamma", 1) {
            @Override
            public double apply(double... doubles) {
                return ComplexFunctions.gammaFunction(doubles[0]);
            }
        };

        /* Fibonacci Function */
        Function fibonacci = new Function("fib", 1) {
            @Override
            public double apply(double... doubles) {
                return Fibonacci.get((int) doubles[0]);
            }
        };

        /* Least Common Multiple Function */
        Function lcm = new Function("lcm", 2) {
            @Override
            public double apply(double... values) {
                int a = (int) values[0];
                int b = (int) values[1];

                if ((double) a != values[0] || (double) b != values[1]) {
                    throw new IllegalArgumentException("lcm is for integers only");
                }
                int c = 0;

                // do math
                return (double) c;

            }
        };

        functions.add(hermonicSum);
        functions.add(rZeta);
        functions.add(gamma);
        functions.add(fibonacci);
        functions.add(hortonFunction);

        return functions;

    }
}
