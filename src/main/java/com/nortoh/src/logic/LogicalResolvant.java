/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nortoh.src.logic;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Christian
 */
public class LogicalResolvant {
    
    
    private Set<String> propositions;
    
    public LogicalResolvant() {
        this.propositions = new HashSet();
    }
    
    public void addProposition(String proposition) {
        propositions.add(proposition);
    }
}
