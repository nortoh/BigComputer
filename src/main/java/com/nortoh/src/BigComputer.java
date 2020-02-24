package com.nortoh.src;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;

import com.nortoh.src.commands.*;
import com.nortoh.src.daisy.DaisyScript;
import com.nortoh.src.input.InputHandler;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.logic.PropositionalLogic;
import com.nortoh.src.states.*;

/**
 * TODO + Add abstraction for binary and hexadecimal while using a decimal as
 * it's main value + Add handles for operations such as +,-,*,/ (eventually,
 * %,^,~,&) and other bit-wise defined operations (this is for expressions only)
 * + Add order of operations (PEMDAS) - done with 3rd party lib
 *
 * States (Moving states to an object class over an integer based value) 0 =
 * Normal Mode [commands, integer operations] 1 = Truth Table Mode [creation of
 * a truth table] 2 = History Mode [view the past inputs using pages] 3 = Debug
 * Mode [view debug messages]
 *
 *
 * Added capabilities to get negative Binary values - 02/11/2019 Added command
 * handler Added bases of InputHandler
 *
 *
 *
 * @author christian
 *
 */
public class BigComputer implements Runnable {

    public static final int MEMORY_SIZE = 16;
    public static final double VERSION = 1.4;

    public static boolean DEBUG = false;

    public static Map<String, Boolean> options;

    private Scanner scanner;
    private Thread computerThread;
    private InputHandler inputHandler;
    private ArrayList<Command> commands;
    private DaisyScript biosFile;
    private Timer clock;
    private State state;
    

    public BigComputer() {
        scanner = new Scanner(System.in);
        inputHandler = new InputHandler(this);
        commands = new ArrayList<Command>();
        clock = new Timer("Clock");
        state = new State(State.Type.NORMAL);
        options = new HashMap<String, Boolean>();
        computerThread = null;

        options.put("debug", false);
        options.put("converge_obvious_values", true);
    }

    public void run() {
        boot();

        while (true) {
            System.out.print(">>");
            
            
            String input = scanner.nextLine();
            inputHandler.handleInput(input);
            getState().setType(State.Type.NORMAL);
        }
    }

    /**
     * Boots the computer
     */
    private void boot() {
        getState().setType(State.Type.NORMAL);

        // Load Commands
        //Logic Commands
        commands.add(new ResolutionCommand("resolution",this));
        
        // Math Commands
        commands.add(new EvalCommand("eval", this));
        commands.add(new RiemannSumCommand("rSum", this));
        commands.add(new SumCommand("sum", this));
        commands.add(new ProductCommand("product", this));

        // Script commands
        commands.add(new LoadDSCommand("loadDs", this));
        commands.add(new LoadDSCommand("lds", this));

        // Datatype Commands
        commands.add(new GetBinCommand("getBin", this));
        commands.add(new GetHexCommand("getHex", this));
        commands.add(new AddBinCommand("addbin", this));

        // Util Commands
        commands.add(new InputTypeCommand("inputType", this));
        commands.add(new CConCommand("ccon", this));
        commands.add(new PingCommand("ping", this));
        commands.add(new ScriptCommand("script", this));

        // Command Commands
        commands.add(new UnloadCmdCommand("unloadcmd", this));

        // Misc Comands
        commands.add(new VersionCommand("version", this));
        commands.add(new HistoryCommand("history", this));
        commands.add(new ListCommand("list", this));
        commands.add(new ExitCommand("exit", this));
        commands.add(new Command("shutdown", this) {
            @Override
            public void execCommand(ArrayList<Parameter> params) {
                shutdown();
                //TODO: Maybe allow certain states for an approved shutdown
            }

        });
        
        commands.add(new Command("reset", this) {
            @Override
            public void execCommand(ArrayList<Parameter> params) {
                reboot();
            }

        });

        commands.add(new Command("rp", this) {
            @Override
            public void execCommand(ArrayList<Parameter> params) {
                int lastCommand = this.getComputer().getInputHandler().getHistory().size() - 1;
                ArrayList<Parameter> commandParams = this.getComputer().getInputHandler().getHistory().get(lastCommand).getParams();
                this.getComputer().getInputHandler().getHistory().get(lastCommand).execCommand(commandParams);
            }
        });

        System.out.println("Added " + commands.size() + " commands");
        System.out.println("Booted successfully.");

        System.out.println();
        welcomeScreen();
    }

    private void welcomeScreen() {
        PrintStream out = System.out;
        out.println("Today's Date is " + new Date().toString());

        PropositionalLogic pl = new PropositionalLogic();
        
        //= {{p, r}, {q, ¬r}, {¬q}, {¬p, t}, {¬s}, {s, ¬t}}
//        String expression = "(P + R) & (Q + ~R) & (~Q) & (~P + G) & (S + ~G) & (~S)";
//        String expression = "~(A + B)";

//        Map<Character, Boolean> truthValues = new HashMap<>();        
//        truthValues.put('A', false);
//        truthValues.put('B', false);
//        
//        LogicalFormula formula = new LogicalFormula(expression);
//        
////        formula.calculate(truthValues);
////        out.println(formula.getExpression() + " is " + formula.getTruthValue());
//        
//        new LogicalResolution(formula).execute();
    }

    /**
     * Shuts down the computer
     */
    private void shutdown() {
        System.out.println("Booting down...");
        System.gc();
        System.exit(0);
    }

    public void reboot() {
        shutdown();
        boot();
    }

    public State getState() {
        return state;
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public static void main(String[] args) {
        BigComputer computer = new BigComputer();
        computer.computerThread = new Thread(computer);
        computer.computerThread.start();
    }
}
