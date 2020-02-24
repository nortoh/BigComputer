/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nortoh.src.logic;

import com.nortoh.src.Console;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Move from lists to sets. It will be way better
 *
 * 
 * To check if there is no new resolvants, at the end of every run, check to see if theres at least one match
 * if no new matches are found, no new resolvants can be made
 * 
 * @author Christian
 */
public class LogicalResolution {

    private final LogicalFormula formula;
    private final List<List> resolvants;

    private boolean foundEmptyClause;
    private boolean noNewResolvants;

    public LogicalResolution(LogicalFormula formula) {
        this.formula = formula;
        this.resolvants = new ArrayList<>();

        this.foundEmptyClause = false;
        this.noNewResolvants = false;
    }

    public void execute() {
        /* Create resolvants */
        for (int i = 0; i < formula.getLogicalStack().size(); i++) {
            LogicalExpression expression = formula.getLogicalStack().get(i);
            List resolvant = stripPropositions(expression);
            resolvants.add(resolvant);
        }
        rearrange(resolvants);
        printResolvants();
        
        int run = 0;

        /* Resolution with unit resolution */
        while (!this.foundEmptyClause && !this.noNewResolvants) {
            rearrange(resolvants);
            for (int i = 0; i < resolvants.size(); i++) {
                List key = resolvants.get(i);

                for (int j = 0; j < key.size(); j++) {
                    try {
                        String symbol = (String) key.get(j);
                        List match = findMatch(symbol);

                        if (match != null && !(this.foundEmptyClause || this.noNewResolvants)) {
                            List newResolvant = buildNewResolvant(symbol, match, key);
                            resolvants.remove(match);
                            resolvants.remove(key);
                            resolvants.add(newResolvant);
                            System.out.printf("[%d] Replacing %s and %s with %s %n", run, printResolvant(key), printResolvant(match), printResolvant(newResolvant));
                            printResolvants();
                            break;
                        } 
                    } catch (NullPointerException e) {
                    }
                }
                
            }
            checkIfCapable();

            run++;
        }

        if (this.noNewResolvants) {
            Console.println("No empty clause found!");
            printResolvants();
        } else if (this.foundEmptyClause) {
            Console.println("Empty clause found!");
        }
    }

    /**
     * Takes care of unification too!
     *
     * @param expression
     * @return
     */
    public List stripPropositions(LogicalExpression expression) {
        List resolvant = new ArrayList();

        for (int i = 0; i < expression.getLogicalEntities().size(); i++) {
            LogicalEntity key = expression.getLogicalEntities().get(i);

            if (key instanceof LogicalNegation) {
                LogicalEntity negationKey = expression.getLogicalEntities().get(i + 1);

                if (negationKey instanceof LogicalProposition) {
                    char symbol = ((LogicalProposition) negationKey).getSymbol();

                    if (LogicalUtils.isValidChar(symbol) && !resolvant.contains("~" + symbol)) {
                        resolvant.add((String) "~" + symbol);
                    }

                    i++;
                }
            } else if (key instanceof LogicalProposition) {
                char symbol = ((LogicalProposition) key).getSymbol();

                if (LogicalUtils.isValidChar(symbol) && !resolvant.contains(symbol)) {
                    resolvant.add(Character.toString(symbol));
                }
            }
        }

        return resolvant;
    }

    /**
     *
     * {A, ~B}, {B}, {~A}
     *
     * Symbol A to = {A, ~B} match = {~A}
     *
     *
     * @param symbol
     * @param match
     * @param to
     * @return
     */
    public List buildNewResolvant(String symbol, List match, List to) {
        List newResolvant = new ArrayList();

        for (int i = 0; i < match.size(); i++) {
            String key = (String) match.get(i);

            if (!key.equals(negate(symbol))) {
                newResolvant.add(key);
            }
        }

        for (int i = 0; i < to.size(); i++) {
            String key = (String) to.get(i);

            if (!key.equals(symbol)) {
                newResolvant.add(key);
            }
        }

        if (newResolvant.isEmpty()) {
            this.foundEmptyClause = true;
        }

        return newResolvant;
    }

    /**
     * TODO: This is doing linear search, maying do a better search
     * 
     * 
     * @param matchSymbol
     * @return 
     */
    public List findMatch(String matchSymbol) {
        String symbol = negate(matchSymbol);
        List unaryResolvants = getUnaryResolvants();

        for (int i = 0; i < resolvants.size(); i++) {
            if (!unaryResolvants.isEmpty()) {
                for (int j = 0; j < unaryResolvants.size(); j++) {
                    List key = resolvants.get((int) unaryResolvants.get(j));

                    if (key.contains(symbol)) {
                        return key;
                    } 
                }
            } else {
                List key = resolvants.get(i);

                if (key.contains(symbol)) {
                    return key;
                }
            }          
        }

        return null;
    }

    private List getUnaryResolvants() {
        List unaryResolvants = new ArrayList();

        for (int i = 0; i < this.resolvants.size(); i++) {
            List element = this.resolvants.get(i);

            if (element.size() == 1) {
                unaryResolvants.add(i);
            }
        }

        return unaryResolvants;
    }

    /**
     * Negate the symbol
     *
     * ~A = A 
     * A = ~A
     *
     * @param symbol
     * @return
     */
    public String negate(String symbol) {
        if (symbol.startsWith("~")) {
            return symbol.substring(1);
        } else {
            return "~" + symbol;
        }
    }

    /**
     * Rearrange the resolvants with size 1 to the front
     * @param resolvants
     * @return 
     */
    public List rearrange(List resolvants) {
        for (int i = 0; i < resolvants.size(); i++) {
            List resolvant = ((List) resolvants.get(i));
            int j = i - 1;

            while (j >= 0 && resolvant.size() == 1) {
                resolvants.set(j + 1, (List) resolvants.get(j));
                j--;
            }
            resolvants.set(j + 1, resolvant);
        }
        return resolvants;
    }
    
    
    private void checkIfCapable() {
        for(int i = 0; i < this.resolvants.size(); i++) {
            List resolvant = ((List) this.resolvants.get(i));
            
            for(int j = 0; j < resolvant.size(); j++) {
                
            }
        }
    }
    
    
    public String printResolvant(List resolvant) {
        String output = "{";

        for (int i = 0; i < resolvant.size(); i++) {
            String symbol = (String) resolvant.get(i);
            if (i < resolvant.size() - 1) {
                output += symbol + ", ";
            } else {
                output += symbol;
            }
        }
        output += "}";

        if(resolvant.isEmpty()) output = "□";
        
        return output;
    }

    public void printResolvants() {
        String output = "{";

        for (int i = 0; i < this.resolvants.size(); i++) {
            List resolvant = this.resolvants.get(i);

            if (i < this.resolvants.size() - 1) {
                output += printResolvant(resolvant) + ", ";
            } else {
                output += printResolvant(resolvant);
            }
        }

        output += "}";
        Console.println(output);
    }

}
