package com.nortoh.src.commands;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.math.ComplexFunctions;
import com.nortoh.src.math.ExpressionEvaluator;
import com.nortoh.src.math.Fibonacci;
import com.nortoh.src.math.InfiniteSeries;
import com.nortoh.src.math.Series;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;

public class EvalCommand extends Command {

	public EvalCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		String input = (String) params.get(0).value();
		
		if(input.equalsIgnoreCase("functions")) {
			printConsole(" ~=~=~= Standard Functions =~=~=~");
			printConsole("abs: absolute value");
			printConsole("acos: arc cosine");
			printConsole("asin: arc sine");
			printConsole("atan: arc tangent");
			printConsole("cbrt: cubic root");
			printConsole("ceil: nearest upper integer");
			printConsole("cos: cosine");
			printConsole("cosh: hyperbolic cosine");
			printConsole("exp: euler's number raised to the power (e^x)");
			printConsole("floor: nearest lower integer");
			printConsole("log: logarithmus naturalis (base e)");
			printConsole("log2: logarithm to base 2");
			printConsole("log10: logarithm to base 10");
			printConsole("sin: sine");
			printConsole("sinh: hyperbolic sine");
			printConsole("sqrt: square root");
			printConsole("tan: tangent");
			printConsole("tanh: hyperbolic tangent");
			printConsole("signum: signum of a value");
			printConsole(" ~=~=~= Additional Functions =~=~=~");	
			printConsole("hSum: hermonic series at a given (s)");
			printConsole("rZeta: riemann zeta function at a given (s)");
			printConsole("fib: fibonacci number at a given (s)");
			return;
		}
		
		if(input.equalsIgnoreCase("help")) {
			printConsole("help: Displays this message");
			printConsole("functions: List available functions");
			return;
		}

		try {
			ExpressionEvaluator expressionEval = new ExpressionEvaluator(new ExpressionBuilder(input), null, null);
			printConsole(expressionEval.evaluate());
		} catch(IllegalArgumentException | NullPointerException e) {
			printError(e.getMessage());
		}
	}
	
}
