package com.nortoh.src.input;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nortoh.src.BigComputer;
import com.nortoh.src.commands.Command;
import com.nortoh.src.math.Constants;

/**
 * TODO:
 *
 * Crate a parameter class that stores the value and the input type. i.e.
 * Binary, Hexadecimal, Decimal
 *
 * @author christian
 *
 */
public class InputHandler {

    private BigComputer computer;
    private List<Command> history;

    public InputHandler(BigComputer computer) {
        this.computer = computer;
        history = new ArrayList<Command>();

    }

    /**
     * Checks and executes commands
     *
     * @param input
     */
    public void handleInput(String input) {
        String command = input.split(" ")[0];
        int foundCommand = getCommandIndex(command);
        int foundFunction = getFunctionIndex(command);

        if (foundCommand != -1) {
            Command currCommand = computer.getCommands().get(foundCommand);
            try {
                try {
                    ArrayList<Parameter> params = createParams(input);
                    currCommand.execCommand(params);
                    history.add(currCommand);
                } catch (NumberFormatException e) {
                    System.out.println("Error: " + e.getLocalizedMessage());
                }
            } catch (IndexOutOfBoundsException e) {
                currCommand.printConsole(currCommand.getUsage());
            }
        } else if (foundFunction != -1) {
            Command currCommand = computer.getCommands().get(foundFunction);
            try {
                ArrayList<Parameter> params = createFunctionParams(input);
                System.out.println("Found function");
                currCommand.execCommand(params);

            } catch (IndexOutOfBoundsException e) {
                currCommand.printConsole(currCommand.getUsage());
            }
        } else if (hasConstant(command)) {
            double constantValue = Constants.constants.get(command);
            System.out.println("[" + command + "]: " + constantValue);
        }

    }

    /**
     * Returns the index of the selected command
     *
     * @param command
     * @return
     */
    public int getCommandIndex(String command) {
        Iterator<Command> iterator = computer.getCommands().iterator();
        int resultIndex = -1;
        int currIndex = 0;

        while (iterator.hasNext()) {
            Command currCommand = iterator.next();
            if (command.equalsIgnoreCase(currCommand.getCommandName())) {
                resultIndex = currIndex;
            }
            currIndex++;
        }

        return resultIndex;
    }

    /**
     * Returns the index of the selected function
     *
     * @param function
     * @return
     */
    public int getFunctionIndex(String function) {
        //		function = function.substring(0, function.indexOf("("));
        Iterator<Command> iterator = computer.getCommands().iterator();
        int resultIndex = -1;
        int currIndex = 0;

        while (iterator.hasNext()) {
            Command currCommand = iterator.next();
            if (function.equalsIgnoreCase(currCommand.getCommandFunctionName())) {
                resultIndex = currIndex;
            }
            currIndex++;
        }

        return resultIndex;
    }

    /**
     * Returns the index of the selected function
     *
     * @param function
     * @return
     */
    public boolean hasConstant(String constant) {
        return Constants.constants.containsKey(constant);
    }

    public boolean commandExists(String command) {
        return (getCommandIndex(command) != -1);
    }

    public List<Command> getHistory() {
        return history;
    }

    /**
     * Creates an arraylist of parameters
     *
     * @param input
     * @return
     */
    public ArrayList<Parameter> createParams(String input) {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        String[] split = input.split(" ");

        // start at one to skip the command
        for (int i = 1; i < split.length; i++) {
            parameters.add(new Parameter(split[i], getInputType(split[i])));
        }

        return parameters;
    }

    private ArrayList<Parameter> createFunctionParams(String input) {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        return parameters;
    }

    // 2, 2^10
    /*
	 * Replaced with exp4j
	 * 
	private String[] processNotations(String[] split) {
		String[] newSplit = split;
		int result = 0;

		for(int index = 0; index < split.length; index++) {
			if(split[index].contains("^")) {
				String tempStr = split[index]; // store temp "2^10" text
				int base = Integer.parseInt(tempStr.substring(0, 1));
				int power = Integer.parseInt(tempStr.substring(2, tempStr.length()));
				result = (int) Math.pow(base, power);
				split[index]  = Integer.toString(result);
			} else if(split[index].contains("!")) {
				String tempStr = split[index]; // store temp "10!" text
				int base = Integer.parseInt(tempStr.substring(0, tempStr.length() - 1));
				result = 1;
				for(int kTimes = base; kTimes > 0; kTimes--) {
					result *= kTimes;
				}
				split[index] = Integer.toString(result);
			} else if(split[index].contains("*")) {
				String tempStr = split[index];
				int multi1 = Integer.parseInt(tempStr.substring(0, 1));
				int multi2 = Integer.parseInt(tempStr.substring(2, tempStr.length()));
				result = multi1 * multi2; 

				split[index] = Integer.toString(result);
			} else if(split[index].contains("/")) {
				int divisorIndex = split[index].indexOf("/");
				String tempStr = split[index];
				int a = Integer.parseInt(tempStr.substring(0, divisorIndex));
				int b = Integer.parseInt(tempStr.substring(divisorIndex + 1, tempStr.length()));
				result = a / b;
				split[index] = Integer.toString(result);
			}
		}

		return newSplit;
	}
     */
    /**
     * Handles input for decimal values
     *
     * @param input
     * @param operator TODO: Add check for what type of base a number is before
     * doing operations Add support for negative values Maybe strip white
     * spaces? Make operations have no space delimiter - 03/24/19
     *
     *
     */
    /*
	public void handleOperator(String input, String operator) {
		int operatorIndex = input.indexOf(operator); // get the index of the operator in the string

		if(operatorIndex == -1) return; // operator not located will return

		int firstValue = Integer.parseInt(input.substring(0, operatorIndex));
		int lastValue = Integer.parseInt(input.substring(operatorIndex + 1, input.length()));

		if(operator.equals("+")) {

			// Test arithmetic mode

			// The goal is to see if we can toggle specific states between commands and arithmetic,
			// this can be used to prevent some warning and error texts along with keeping a State constant
			// that runs parallel to the applications being used.... and so far it works.


			computer.getState().setType(State.Type.ARITHMETIC);
			int result = firstValue + lastValue;
			System.out.println(firstValue + "+" + lastValue + "=" + result);
		} else if(operator.equals("-")) {
			computer.getState().setType(State.Type.ARITHMETIC);
			int result = firstValue - lastValue;
			System.out.println(firstValue + "-" + lastValue + "=" + result);
		} else if(operator.equals("#")) {
			if(lastValue != 2 || lastValue != 16) {
				System.out.println("Only base 2 and base 16 are programmed.");
			}
		}
	}
     */
    /**
     * Returns the input type of the input
     *
     * TODO: Add check to make sure Binary and Hexadecimal are in their correct
     * forms.
     *
     * i.e. Binary: b01110011 i.e. Hexadecimal: 0xA9C1 i.e. Web site:
     * http://christianhorton.me i.e. Decimal: 3.141592
     *
     * @param input
     * @return
     */
    public InputType getInputType(String input) {
        InputType type = null;

        if (input.startsWith("b")) { 				// Binary
            type = InputType.BINARY;
        } else if (input.startsWith("0x")) {			// Hexadecimal
            type = InputType.HEXADECIMAL;
        } else if (input.startsWith("LOG_EXP")) { 	// Logic Expressions
            //TODO: Begin logic expressions
            type = InputType.LOGIC_EXPRESSION;
        } else if (input.startsWith("www")
                || input.startsWith("http")
                || input.startsWith("https")) {
            type = InputType.WEBSITE;
        } else {									// Decimal
            type = InputType.DECIMAL;
        }

        return type;
    }

    public enum InputType {
        BINARY("Binary"),
        DECIMAL("Decimal"),
        HEXADECIMAL("Hexadecimal"),
        EXPRESSION("Expression"),
        STRING("String"),
        LOGIC_EXPRESSION("Logic Expression"),
        ALPHANUMERIC("Alphanumeric"),
        WEBSITE("Website");

        String name;

        InputType(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }
}
