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
public class LogicalProposition extends LogicalEntity {
     
    private final char symbol;
    private boolean value;
    
    public LogicalProposition(char symbol) {
        this.symbol = symbol;
    }
    
    public char getSymbol() {
        return this.symbol;
    }
    
    public boolean getTruthValue() {
        return this.value;
    }
    
    public void setTruthValue(boolean value) {
        this.value = value;
    }
}
