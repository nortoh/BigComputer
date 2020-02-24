package com.nortoh.src.commands;

import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.Console;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.logic.LogicalFormula;
import com.nortoh.src.logic.LogicalResolution;

public class TruthValueCommand extends Command {

    public TruthValueCommand(String commandName, BigComputer computer) {
        super(commandName, computer);
        this.setUsage("truthvalue [expression] [{symbol}:{truthvalue} ...]");
    }

    @Override
    public void execCommand(ArrayList<Parameter> params) {
//d
    }
    
    
    
    
}
