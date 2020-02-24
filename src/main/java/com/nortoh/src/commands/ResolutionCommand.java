package com.nortoh.src.commands;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.Console;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.logic.LogicalFormula;
import com.nortoh.src.logic.LogicalResolution;

public class ResolutionCommand extends Command {

    public ResolutionCommand(String commandName, BigComputer computer) {
        super(commandName, computer);
        this.setUsage("resolution [expression]");
    }

    @Override
    public void execCommand(ArrayList<Parameter> params) {
        String expression = substring(0, params.size() - 1, params);

        if (expression != null) {
            LogicalFormula formula = new LogicalFormula(expression);
            new LogicalResolution(formula).execute();
        }
    }
}
