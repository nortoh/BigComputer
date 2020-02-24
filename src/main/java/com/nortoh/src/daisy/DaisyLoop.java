package com.nortoh.src.daisy;

import java.util.LinkedList;
import java.util.List;

import com.nortoh.src.math.ExpressionEvaluator;

public class DaisyLoop extends DaisyMethod {

    public List<ExpressionEvaluator> loopExpressions;
    private int loopBegin;
    private int loopEnd;
    private String loopVar;

    public DaisyLoop(String loopVar, int loopBegin, int loopEnd) {
        super("loop");
        loopExpressions = new LinkedList<ExpressionEvaluator>();
        this.loopVar = loopVar;
        this.loopBegin = loopBegin;
        this.loopEnd = loopEnd;
    }

    public void addExpression(ExpressionEvaluator expression) {
        this.loopExpressions.add(expression);
    }

    public void execute() {
        int loopSize = loopEnd - loopBegin;
        int counter = loopBegin;

        //		loopVar = isVariable(loopVar);
        for (int index = 0; index <= loopSize; index++) {
            for (int expressionIndex = 0; expressionIndex < loopExpressions.size(); expressionIndex++) {
                double result = loopExpressions.get(expressionIndex).evaluate();
                printConsole("[" + counter + "] result: " + result);
                counter++;
                loopExpressions.get(expressionIndex).getExpression().setVariable(loopVar, counter);
            }
        }

        clearVariable(loopVar);

    }

    private void clearVariable(String loopVar) {
        for (int expressionIndex = 0; expressionIndex < loopExpressions.size(); expressionIndex++) {
            double result = loopExpressions.get(expressionIndex).evaluate();
            loopExpressions.get(expressionIndex).getExpression().removeVariable(loopVar);
        }
    }
}
