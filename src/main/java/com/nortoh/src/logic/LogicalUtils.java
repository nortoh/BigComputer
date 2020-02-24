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
public class LogicalUtils {

    private static Set ALLOWED_PROPOSITIONAL_CHARS = new HashSet();
    private static Set ALLOWED_PROPOSITIONAL_OPERS = new HashSet();

    public static boolean isValidChar(char key) {
        return ALLOWED_PROPOSITIONAL_CHARS.contains(key);
    }

    public static boolean isValidOper(char key) {
        return ALLOWED_PROPOSITIONAL_OPERS.contains(key);
    }

    static {
        /* Operator Chars*/
        ALLOWED_PROPOSITIONAL_OPERS.add('~');
        ALLOWED_PROPOSITIONAL_OPERS.add('&');
        ALLOWED_PROPOSITIONAL_OPERS.add('+');
        ALLOWED_PROPOSITIONAL_OPERS.add('>');
        ALLOWED_PROPOSITIONAL_OPERS.add('=');
        
        /* Propositional Chars */
        ALLOWED_PROPOSITIONAL_CHARS.add('A');
        ALLOWED_PROPOSITIONAL_CHARS.add('B');
        ALLOWED_PROPOSITIONAL_CHARS.add('C');
        ALLOWED_PROPOSITIONAL_CHARS.add('D');
        ALLOWED_PROPOSITIONAL_CHARS.add('E');
        // F is reserved for truth value
        ALLOWED_PROPOSITIONAL_CHARS.add('G');
        ALLOWED_PROPOSITIONAL_CHARS.add('H');
        ALLOWED_PROPOSITIONAL_CHARS.add('I');
        ALLOWED_PROPOSITIONAL_CHARS.add('J');
        ALLOWED_PROPOSITIONAL_CHARS.add('K');
        ALLOWED_PROPOSITIONAL_CHARS.add('L');
        ALLOWED_PROPOSITIONAL_CHARS.add('M');
        ALLOWED_PROPOSITIONAL_CHARS.add('N');
        ALLOWED_PROPOSITIONAL_CHARS.add('O');
        ALLOWED_PROPOSITIONAL_CHARS.add('P');
        ALLOWED_PROPOSITIONAL_CHARS.add('Q');
        ALLOWED_PROPOSITIONAL_CHARS.add('R');
        ALLOWED_PROPOSITIONAL_CHARS.add('S');
        // T is reserved for truth value
        ALLOWED_PROPOSITIONAL_CHARS.add('U');
        ALLOWED_PROPOSITIONAL_CHARS.add('V');
        ALLOWED_PROPOSITIONAL_CHARS.add('X');
        ALLOWED_PROPOSITIONAL_CHARS.add('Z');

        ALLOWED_PROPOSITIONAL_CHARS.add('(');
        ALLOWED_PROPOSITIONAL_CHARS.add(')');
    }

}
