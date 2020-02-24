/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nortoh.src;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Christian
 */
public class Console {

    private static final Logger logger;
    private Scanner scanner;
    private BigComputer computer;
    
    public Console(BigComputer computer) {
        this.scanner = new Scanner(System.in);
        this.computer = computer;
    }
    
    public void handleConsole() {

    }
    
    public String getLine() {
        return this.scanner.next();
    }
    
    public int getInt() {
        return this.scanner.nextInt();
    }
    
    public static Logger getLogger() {
        return logger;
    }

    public static void print(Object object) {
        System.out.print(object);
    }

    public static void println(Object object) {
        System.out.println(object);
    }
    
    public static void println() {}
    

    static {
        logger = Logger.getLogger("Computer");
    }
}
