package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.Map;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;

public abstract class Command {

    private String commandName;
    private String commandUsage;
    private String commandFunctionName;
    private BigComputer computer;

    //updated parameters
    private ArrayList<Parameter> parameters;

    public Command(String commandName, BigComputer computer) {
        this.commandName = commandName;
        this.computer = computer;
        this.parameters = new ArrayList<Parameter>();
        this.commandUsage = "N/A";
        this.commandFunctionName = "nil";
    }

    /**
     * Called when a command is called
     *
     * @param params
     */
    public abstract void execCommand(ArrayList<Parameter> params);

    /**
     * Sets the usage description of the command
     *
     * @param message
     */
    protected void setUsage(String message) {
        this.commandUsage = message;
    }

    /**
     * Sets the function name of the command
     *
     * @param functionName
     */
    protected void setFunctionName(String functionName) {
        this.commandFunctionName = functionName;
    }

    /**
     * Returns the command usage description
     *
     * @return
     */
    public String getUsage() {
        return this.commandUsage;
    }

    /**
     * Prints a usage message to the console
     *
     * @param message
     */
    public void printError(String message) {
        System.out.println("[" + commandName + "] Error: " + message);
    }

    /**
     * Prints a message to the console
     *
     * @param message
     */
    public void printConsole(Object message) {
        System.out.println("[" + commandName + "]: " + message);
    }

    /**
     * Returns the command name
     *
     * @return
     */
    public String getCommandName() {
        return this.commandName;
    }

    /**
     * Returns the command function name
     *
     * @return
     */
    public String getCommandFunctionName() {
        return this.commandFunctionName;
    }

    /**
     * Returns the parameters of the command
     *
     * @return
     */
    public ArrayList<Parameter> getParams() {
        return this.parameters;
    }

    public BigComputer getComputer() {
        return computer;
    }

    public String substring(int start, int end, ArrayList<Parameter> params) {
        if (end > params.size()) return "FAILED CONCAT";
        String result = "";

        for (int i = start; i <= end; i++) {
            result += (String) params.get(i).value();
        }

        return result;
    }
}
