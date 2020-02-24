/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nortoh.src.logic;

import com.nortoh.src.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Christian
 */
public class LogicalFormula extends PropositionalLogic {

    private final String expressionStr;
    private final String stripedExpression;
    
    private final char[] expressionChar;

    private boolean isValidFormula;
    private boolean truthValue;
    
    private final List<LogicalExpression> logicalStack;
    private Map<Character, Boolean> booleanMap;
    private int amountOfPropositions;
    
    private NormalForm form;

    public LogicalFormula(String expression) {
        this.expressionStr = expression;
        this.stripedExpression = stripWhitespaces(expression);
        
        this.expressionChar = this.stripedExpression.toCharArray();
        
        this.logicalStack = new ArrayList<>();
        this.amountOfPropositions = countPropositions(expressionChar);
        
        this.form = determineNormalForm();
        
        if (isValidFormula()) {
            // this was 1 before, idk if something breaks changing to zero
            breakdownFormula(expressionChar, 1, expressionChar.length - 1);
        } else {
            System.out.println("Not a valid formula!");
        }
    }
    
    public void calculate(Map<Character, Boolean> booleanMap) {
        this.booleanMap = booleanMap;
        evaluateFormula();
    }

    /**
     *
     * FYI, I have rewrote this for-loop twice
     *
     * @param data
     * @param low
     * @param high
     */
    private void breakdownFormula(char[] data, int low, int high) {
        if (low < high) {
            int pi = partition(data, low, high);
            if (pi == -1) return;

            LogicalExpression formula = new LogicalExpression();

            for (int i = low; i < pi; i++) {
                char key = data[i];

                if (LogicalUtils.isValidOper(key)) {
                    formula.addEntity(LogicalOperator.getType(data[i]));
                } else if (LogicalUtils.isValidChar(key)) {
                    formula.addEntity(new LogicalProposition(key));
                }
//                System.out.printf("%s %n", key);
            }

            if ((pi + 1) < high) {
                formula.addEntity(LogicalOperator.getType(data[pi + 1]));
            }

            logicalStack.add(formula);
            breakdownFormula(data, pi + 3, high);
        }
    }

    /**
     * 
     */
    private void evaluateFormula() {
        LogicalProposition proposition;
        LogicalOperator operator;

        boolean tempResult;
        boolean result = true;
        boolean deMorgan = false;
        char action = '?';

        for (int i = 0; i < logicalStack.size(); i++) {
            LogicalExpression expression = logicalStack.get(i);
            int expressionSize = expression.getLogicalEntities().size();

            if(i == 0 && (expression.getLogicalEntities().get(0) instanceof LogicalNegation) && (expressionSize != 1)) {
                System.out.println("WE HAVE A DE MORGANS!!!1");
                deMorgan = true;
            }
            
            // If the last proposition is single
            if (expressionSize <= 1) {
                proposition = (LogicalProposition) expression.getLogicalEntities().get(0);
                char symbol = proposition.getSymbol();
                
                if (!this.booleanMap.containsKey(symbol) && (LogicalUtils.isValidOper(symbol) || LogicalUtils.isValidChar(symbol))) {
                    Console.println(symbol + " is not defined.");
                    break;
                }
                
                tempResult = this.booleanMap.get(symbol);
                proposition.setTruthValue(tempResult);
                result = LogicalOperator.getType(action).performOperation(result, tempResult);               
            } else {
                for (int j = 0; j < expressionSize; j++) {
                    LogicalEntity entity = expression.getLogicalEntities().get(j);

                    if (entity instanceof LogicalProposition) {
                        proposition = (LogicalProposition) entity;
                        char symbol = proposition.getSymbol();

                        if (!this.booleanMap.containsKey(symbol) && (LogicalUtils.isValidOper(symbol) || LogicalUtils.isValidChar(symbol))) {
                            Console.println(symbol + " is not defined.");
                            break;
                        }

                        if (action != '?') {
                            tempResult = this.booleanMap.get(symbol);
                            proposition.setTruthValue(tempResult);
                            result = LogicalOperator.getType(action).performOperation(result, tempResult);
                        } else {
                            result = this.booleanMap.get(symbol);
                            proposition.setTruthValue(result);
                        }
                    } else if (entity instanceof LogicalOperator) {
                        operator = (LogicalOperator) entity;
                        action = operator.getSymbol();
                    }
                }
                
                LogicalEntity appendingEntity = expression.getLogicalEntities().get(expressionSize - 1);
                if (appendingEntity instanceof LogicalOperator) {
                    LogicalOperator appendingOperator = (LogicalOperator) appendingEntity;
                    action = appendingOperator.getSymbol();
                }
            }
            Console.println();
        }
        
        this.truthValue = result;
    }

    /**
     * Checks to see if the formula provided is valid
     *
     * @return
     */
    private boolean isValidFormula() {
        char[] characters = stripedExpression.toCharArray();
  
        for (int i = 0; i < characters.length; i++) {
            if (!LogicalUtils.isValidChar(characters[i]) && !LogicalUtils.isValidOper(characters[i])) {
                if(LogicalUtils.isValidChar(characters[i])) this.amountOfPropositions++;
                this.isValidFormula = false;
                break;
            }       
            this.isValidFormula = true;
        }
        return this.isValidFormula;
    }
    
    public boolean isOfForm(NormalForm form) {
        if(form.equals(NormalForm.CNF)) {
            for (int i = 0; i < this.logicalStack.size(); i++) {
                LogicalExpression expression = this.logicalStack.get(i);
                
                for(int j = 0; j < expression.getLogicalEntities().size() - 1; j++) {
                    LogicalEntity entity = expression.getLogicalEntities().get(j);
                    if(!(entity instanceof LogicalDisjunction)) {
                        
                    }
                }
                
            }
        }
        
        return false;
    }

    /**
     * Removes any white-spaces on a given formula
     *
     * @param expression
     * @return
     */
    private String stripWhitespaces(String expression) {
        char[] characters = expression.toCharArray();
        String result = "";

        for (int i = 0; i < characters.length; i++) {
            if (characters[i] != ' ') {
                result += characters[i];
            }
        }

        return result;
    }

    private int partition(char[] data, int low, int high) {
        if(locateElement(data, low, high, ')') != -1) {
            return locateElement(data, low, high, ')');
        }       
        return -1;
    }
    
    private int locateElement(char[] data, int low, int high, char key) {
        for(int i = low; i <= high; i++) {
            if(data[i] == key) return i;
        }
        return -1;
    }
    
    private boolean contains(char[] data, char key) {
        for(int i = 0; i < data.length; i++) {
            
        }
        return false;
    }
    
    private int countPropositions(char[] data) {
        int counter = 0;
        for(int i = 0; i < data.length; i++) {
            if(LogicalUtils.isValidChar(data[i]) && data[i] != '(' && data[i] != ')') counter++;
        }
        return counter;
    }
    
    public NormalForm determineNormalForm() {
        NormalForm form = null;
        
        for(int i = 0; i < this.logicalStack.size(); i++) {
            
        }
        
        return form;
    }
    
    public List<LogicalExpression> getLogicalStack() {
        return this.logicalStack;
    }
    
    public int amountOfPropositions() {
        return this.amountOfPropositions;
    }
       
    public String getExpression() {
        return this.expressionStr;
    }

    public String getStripedExpression() {
        return this.stripedExpression;
    }

    public boolean getTruthValue() {
        return this.truthValue;
    }
}
