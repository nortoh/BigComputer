package com.nortoh.src.logic;

/**
 *
 * @author Christian
 */
public class LogicalExclusiveOr extends LogicalOperator {
    
    public LogicalExclusiveOr() {
        super('#');
    }

    @Override
    public boolean performOperation(boolean a, boolean b) {
        return (a | b) & (!a | !b);
    }   
}
