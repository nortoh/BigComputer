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
public class LogicalConjunction extends LogicalOperator {

    public LogicalConjunction() {
        super('&');
    }

    @Override
    public boolean performOperation(boolean a, boolean b) {
        return a & b;
    }
}
