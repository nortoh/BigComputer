package com.nortoh.src.daisy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nortoh.src.BigComputer;
import com.nortoh.src.Console;
import com.nortoh.src.exceptions.ScriptSyntaxErrorException;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.math.ExpressionEvaluator;
import com.nortoh.src.utils.Timer;

import net.objecthunter.exp4j.ExpressionBuilder;

/*
 * 
 * Number system of states per script function.
 * Ex. 	loop 	= state 3
 * 		brloop 	= state 4
 * 
 */
public class DaisyScript extends File {

    private static final long serialVersionUID = -2962879434858867216L;
    private BufferedReader bufferedReader;
    private BigComputer computer;

    private boolean scriptValidated;
    private boolean isExecuting;

    private List<Boolean> lineProcessed;
    private List<LineType> lineType;
    private List<DaisyMethod> daisyMethods;

    private Set<String> variables;
    private Map<String, Double> variableData;
    private Object[] lines;

    public DaisyScript(String pathName, BigComputer computer) {
        super(pathName);
        try {
            bufferedReader = new BufferedReader(new FileReader(this));
            lines = bufferedReader.lines().toArray();
        } catch (FileNotFoundException e) {
        }

        this.computer = computer;
        this.daisyMethods = new LinkedList<DaisyMethod>();

        this.lineProcessed = new LinkedList<Boolean>();
        this.lineType = new LinkedList<LineType>();
        this.variables = new HashSet<String>();
        this.variableData = new HashMap<String, Double>();

        loadVariables();

        this.isExecuting = false;
    }

    /*
	 * Script Processing Idea
	 * 
	 * Before a script is evaluated line by line, let's do some compile
	 * passes of the script to get a sense of where comments, functions,
	 * and variables are. We can collect all those and then in a sequential
	 * -jumping order, we can go line by line on the script.
	 * 
	 * Line Types:
	 * 1 	- expression
	 * 2 	- variable deceleration
	 * 3 	- loop
	 * 4 	- brloop
	 * 100 	- end
	 * 
	 * [0]	x:=2 			[variable deceleration,	type=2]
	 * [1]	x 				[expression, 			type=1]
	 * [2]	loop i 1 10		[loop,					type=3]
	 * [3]	x+i
	 * [4]	brloop			[brloop,				type=4]
	 * [5]	end				[end,					type=100]
	 * 
	 * Daisy Methods
	 * 
	 * Daisy Loop method
	 * - loopStart
	 * - loopEnd
	 * - loopSize
	 * - loopData
	 * - loopCounter
	 * 
     */
    private void loadVariables() {
        variables.add("max_int");
        variableData.put("max_int", (double) 10000000);
        variables.add("inf");
        variableData.put("inf", Double.POSITIVE_INFINITY);
    }

    public void evaluateScript_old() {

        long time = System.currentTimeMillis();

        try {
            boolean scriptProcessed = processScript();
        } catch (IOException | ScriptSyntaxErrorException e) {
            System.out.println("[Daisy Error] " + e.getMessage());
        }

        try {
            String expressionLine = bufferedReader.readLine();
            while (expressionLine != null) {
                if (expressionLine.isEmpty()) {
                    return; // Skip blank lines?
                }
                /* Commenting */
                if (expressionLine.startsWith("#")) { // Beginning of line commenting
                    expressionLine = bufferedReader.readLine();
                    return;
                } else {
                    if (expressionLine.contains("#")) { // End of line commenting
                        int commentIndex = expressionLine.indexOf("#");
                        // Grab only the beginning up to the first comment
                        expressionLine = expressionLine.substring(0, commentIndex - 1).trim();
                    }
                }

                /* set variable */
                if (expressionLine.contains(":=")) {
                    String[] expressionVars = expressionLine.split(":=");
                    if (expressionVars[1].matches("^[0-9]*$")) {
                        variables.add(expressionVars[0]);
                        variableData.put(expressionVars[0], Double.parseDouble(expressionVars[1]));
                    } else {
                        variables.add(expressionVars[0]);
                        ExpressionEvaluator expressionEval = new ExpressionEvaluator(new ExpressionBuilder(expressionVars[1]), variables, variableData);
                        variableData.put(expressionVars[0], expressionEval.evaluate());
                    }
                } else {
                    long passedTime = (long) ((System.currentTimeMillis() - time) / 1000);
                    ExpressionEvaluator expressionEval = new ExpressionEvaluator(new ExpressionBuilder(expressionLine), variables, variableData);
                    System.out.println("expression: " + expressionLine + " result: " + expressionEval.evaluate() + " time: " + passedTime);
                }

                expressionLine = bufferedReader.readLine();
            }

            System.out.println("Total time: " + (long) ((System.currentTimeMillis() - time) / 1000));

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void evaluateScript() {
        Iterator<DaisyMethod> daisyMethodIterator = daisyMethods.iterator();

        isExecuting = true;

        Timer scriptTimer = new Timer();

        scriptTimer.start();
        while (daisyMethodIterator.hasNext() && isExecuting) {
            daisyMethodIterator.next().execute();
        }
        scriptTimer.end();

        System.out.println("Total time: " + scriptTimer.seconds() + " seconds");
    }

    /*
	 * 
	 * COMPLETE REWRITE OF PROCESS SCRIPT
	 * 
	 * Intentions:
	 * - Add better use of methods with parameters Ex. binary(10), get
	 * 
     */
    public boolean processScript() throws IOException, ScriptSyntaxErrorException {
        boolean scriptProcessed = false;

        boolean isLooping = false;
        int loopBegin = 0;		// loop begin
        int loopEnd = 0;		// loop end
        int loopLineBegin = 0;	// begin loop line
        int loopLineEnd = 0;	// end loop line
        String loopVar = "i";	// default variable

        for (int lineNum = 0; lineNum < lines.length; lineNum++) {
            String lineString = (String) lines[lineNum];
            String[] lineParts = new String(lineString).split(" ");
            String scriptMethod = lineParts[0]; // first name of the method
            LineType type = getLineType(lineString);

            if (lineParts[0].startsWith("[")) {
                int end = lineParts[0].indexOf("]");
                System.out.println("Beginning option");
            }

//			if(scriptMethod.contains("(")) {
//				int pbIndex = scriptMethod.indexOf("(");
//				int pfIndex = scriptMethod.indexOf(")");
//				if(pfIndex == -1) {
//					System.out.println("incomplete function");
//				}
//				System.out.println(scriptMethod);
//			}
            switch (scriptMethod) {
                /*
			 * Start loop function 
                 */
                case "loop":
                    if (lineParts.length < 4) {
                        throw new ScriptSyntaxErrorException(lineNum, "loop requires 3 parameters.");
                    }
                    isLooping = true;
                    loopLineBegin = lineNum;

                    loopVar = lineParts[1];
                    loopBegin = Integer.parseInt(lineParts[2]);
                    loopEnd = Integer.parseInt(lineParts[3]);

                    // Store the loop var into the variables to be used. Scope will not exist yet.
                    if (variables.contains(loopVar)) {
                        throw new ScriptSyntaxErrorException(lineNum, "variable " + loopVar + " is already in use.");
                    }
                    variables.add(loopVar);
                    variableData.put(loopVar, (double) loopBegin);
                    break;
                /*
                    * Break loop function
                 */
                case "brloop":
                    if (!isLooping) {
                        throw new ScriptSyntaxErrorException(lineNum, "brloop found with no loop.");
                    }
                    loopLineEnd = lineNum;

                    DaisyLoop daisyLoop = new DaisyLoop(loopVar, loopBegin, loopEnd);
                    for (int loopIndex = loopLineBegin + 1; loopIndex <= loopLineEnd - 1; loopIndex++) {
                        String expressionLoopString = (String) lines[loopIndex];
                        ExpressionEvaluator expressionEval = new ExpressionEvaluator(new ExpressionBuilder(expressionLoopString), variables, variableData);
                        daisyLoop.addExpression(expressionEval);
                    }

                    daisyMethods.add(daisyLoop);
                    isLooping = false;
                    break;
                /*
                     * Similar to a halt instruction, ends the script
                 */
                case "end":
                    daisyMethods.add(new DaisyEnd(this));
                    break;

                case "exec":
                    if (lineParts.length < 1) {
                        throw new ScriptSyntaxErrorException(lineNum, "exec requires 1 parameter");
                    }
                    String command = lineParts[1];
                    String params = substring(2, lineParts.length, lineParts);
//                    daisyMethods.add(new DaisyExecCommand(command, commandParams, this.computer));
                    System.out.println("Command");
                    
                    break;
                /*
                     * Set Machine Options within the script
                     * 
                 */

                case "set_o":
                    if (lineParts.length < 2) {
                        throw new ScriptSyntaxErrorException(lineNum, "set_o requires 2 parameters");
                    }
                    String optionName = lineParts[1];
                    boolean optionVal = Boolean.parseBoolean(lineParts[2]);

                    if (BigComputer.options.containsKey(optionName)) {
                        daisyMethods.add(new DaisySetOption(optionName, optionVal));
                    } else {
                        throw new ScriptSyntaxErrorException(lineNum, "set_o " + optionName + " does not exist");
                    }
                    break;
                case "rintegrate":
                    if (lineParts.length < 4) {
                        throw new ScriptSyntaxErrorException(lineNum, "rintegrate requires 3 parameters.");
                    }

                    String function = lineParts[1];
                    String lowerBound = lineParts[2];
                    String upperBound = lineParts[3];

                    double lowerLimit = ExpressionEvaluator.getResult(lowerBound, variables, variableData);
                    double upperLimit = ExpressionEvaluator.getResult(upperBound, variables, variableData);

                    variables.add("x");
                    variableData.put("x", Double.valueOf(lowerBound));
                    System.out.println(function + " " + lowerBound + " " + upperBound);
                    ExpressionEvaluator integrateExpression = new ExpressionEvaluator(new ExpressionBuilder(function), variables, variableData);
                    daisyMethods.add(new DaisyRIntegrate(integrateExpression, lowerLimit, upperLimit, variables, variableData));
                    break;
                case "sum":
                    if (lineParts.length < 4) {
                        throw new ScriptSyntaxErrorException(lineNum, "sum requires 3 parameters.");
                    }

                    String sumFunction = lineParts[1];
                    String sumLowerBound = lineParts[2];
                    String sumUpperBound = lineParts[3];

                    double sumLowerLimit = ExpressionEvaluator.getResult(sumLowerBound, variables, variableData);
                    double sumUpperLimit = ExpressionEvaluator.getResult(sumUpperBound, variables, variableData);

                    variables.add("x");
                    variableData.put("x", Double.valueOf(sumLowerLimit));
                    ExpressionEvaluator sumExpression = new ExpressionEvaluator(new ExpressionBuilder(sumFunction), variables, variableData);
                    daisyMethods.add(new DaisySum(sumExpression, sumLowerLimit, sumUpperLimit, variables, variableData));
                    break;
                default:
                    /* 
				 * Variable Deceleration  
				 * 
				 * TODO: Allow variable deceleration inside loops 
				 * 
                     */
                    if (lineString.contains(":=") && !isLooping) {
                        String[] expressionVars = lineString.split(":=");
                        // This regex needs to be better
                        String regex = "^[0-9]*$";

                        if (expressionVars[1].matches(regex)) {
                            variables.add(expressionVars[0]);
                            variableData.put(expressionVars[0], Double.parseDouble(expressionVars[1]));
                        } else {
                            variables.add(expressionVars[0]);
                            ExpressionEvaluator expressionEval = new ExpressionEvaluator(new ExpressionBuilder(expressionVars[1]), variables, variableData);
                            variableData.put(expressionVars[0], expressionEval.evaluate());
                        }
                        /*
					 * Comments
                         */
                    } else if (lineString.contains("#")) {

                        //					int commentIndex = lineString.indexOf("#");
                        //					
                        //					if(commentIndex != 0) {
                        //						throw new ScriptSyntaxErrorException(line, "improper comment");
                        //					}
                        daisyMethods.add(new DaisyComment());
                        /*
					 * General expressions
                         */
                    } else if (!isLooping) {
                        String lineExpression = (String) lines[lineNum];
                        if (!lineExpression.isEmpty()) {
                            ExpressionEvaluator expressionEval = new ExpressionEvaluator(new ExpressionBuilder(lineExpression), variables, variableData);
                            daisyMethods.add(new DaisyExpression(expressionEval));
                        }
                    }
                    break;
            }

            // add the line as processed one the checks have been completed.
            lineProcessed.add(lineNum, true);
        }
        scriptProcessed = true;

        return scriptProcessed;
    }

    public void setExecuting(boolean val) {
        this.isExecuting = val;
    }

    public boolean isValidated() {
        return scriptValidated;
    }

    private String showVarData() {
        return this.variableData.toString();
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public Object[] getLines() {
        return lines;
    }

    public LineType getLineType(String line) {
        String[] lineParts = line.split(" ");
        String scriptMethod = lineParts[0];

        /*
		 * Handle script syntax
         */
        switch (scriptMethod) {
            case "loop":
                return LineType.LOOP;
            case "brloop":
                return LineType.BRLOOP;
            case "end":
                return LineType.END;
        }

        if (line.contains(":=")) {
            return LineType.VARIABLE_DECLERATION;
        }

        return LineType.UNKNOWN;
    }

    public String substring(int start, int end, String[] params) {
        if (end > params.length) {
            return "FAILED CONCAT";
        }
        String result = "";

        for (int i = start; i <= end; i++) {
            result += params[i];
        }

        return result;
    }
}
