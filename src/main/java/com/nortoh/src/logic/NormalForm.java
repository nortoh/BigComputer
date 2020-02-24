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
public enum NormalForm {
    
    CXNF("Complex Normal Form"),
    CNF("Conjuntive Normal Form"),
    DNF("Disjunctive Normal Form"),
    PNF("Prenex Normal Form"),
    SKF("Skolem Normal Form");
    
    String name;
    
    NormalForm(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
}
