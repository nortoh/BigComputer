/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nortoh.src.logic;

import com.nortoh.src.Console;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Christian
 */
public class PropositionalLogic {

    public PropositionalLogic() {

    }

    public void calculateTruthTable(LogicalFormula formula) {
        int n = formula.amountOfPropositions();
        int size = (int) Math.pow((long) n, 2);
        Map<Character, Boolean> booleanMap = new HashMap<Character, Boolean>();

        for (int i = 0; i < size; i++) {
            int l = size / (i + 1);
            l = l / 2;
            Console.println(l);
        }
    }
}
