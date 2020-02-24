package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.function.Function;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.math.ComplexFunctions;
import com.nortoh.src.math.RiemannSummation;
import com.nortoh.src.math.Series;
import com.nortoh.src.math.Summation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SumCommand extends Command {

    public SumCommand(String commandName, BigComputer computer) {
        super(commandName, computer);
    }

    @Override
    public void execCommand(ArrayList<Parameter> params) {
        String expressionStr = (String) params.get(0).value();
        int lowerLimit = Integer.parseInt((String) params.get(1).value());
        int upperLimit = Integer.parseInt((String) params.get(2).value());

        Expression expression = new ExpressionBuilder(expressionStr).variables("x").build();

        try {
            Series series = new Series(expression).setLowerLimit(lowerLimit).setUpperLimit(upperLimit);
            printConsole(series.sum());
        } catch (Exception e) {
            printError(e.getMessage());
        }

    }
}
