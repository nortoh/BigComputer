package com.nortoh.src.daisy;

import com.nortoh.src.BigComputer;
import com.nortoh.src.commands.Command;
import com.nortoh.src.input.Parameter;
import java.util.ArrayList;

/**
 * 
 * exec resolution (P + Q) & (~Q) & (~P)
 * 
 * 
 * @author chris
 */

public class DaisyExecCommand extends DaisyMethod {

    private final String command;
    private final ArrayList<Parameter> params;
    private final BigComputer computer;

    public DaisyExecCommand(String command, ArrayList<Parameter> params, BigComputer computer) {
        super("exec");
        this.command = command;
        this.params = params;
        this.computer = computer;
    }

    @Override
    public void execute() {
        Command c = this.computer.getCommands().get(this.computer.getInputHandler().getCommandIndex(command));
        c.execCommand(params);
    }

}
