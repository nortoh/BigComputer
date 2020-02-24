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
public abstract class LogicalOperator extends LogicalEntity {

    private final char symbol;

    public LogicalOperator(char symbol) {
        this.symbol = symbol;
    }
    
    public abstract boolean performOperation(boolean a, boolean b);

    public static LogicalOperator getType(char oper) {
        switch (oper) {
            case '~':
                return new LogicalNegation();
            case '&':
                return new LogicalConjunction();
            case '+':
                return new LogicalDisjunction();
            case '>':
                return new LogicalImplication();
            case '=':
                return new LogicalBiconditional();
            case '#':
                return new LogicalExclusiveOr();
        }
        return null;
    }

    public char getSymbol() {
        return symbol;
    }
}
