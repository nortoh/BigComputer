/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nortoh.src.logic;

/**
 *
 * @author Christian
 */
public class LogicalPredicate extends LogicalEntity {
    
    private char symbol;
    private String arguments;
    
    public LogicalPredicate(char symbol, String arguments) {
        this.symbol = symbol;
        this.arguments = arguments;
    }
    
    public char getSymbol() {
        return this.symbol;
    }
}
