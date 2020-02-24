/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nortoh.src.logic;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Christian
 */
public class LogicalExpression extends LogicalEntity {
    
    private boolean value;
    
    private final List<LogicalEntity> logicalEntities; 

    public LogicalExpression() {
        logicalEntities = new ArrayList<>();
    }
    
    public void addEntity(LogicalEntity e) {
        this.logicalEntities.add(e);
//        System.out.printf("[LogicalExpression.class] %s %n", e.toString());
    }
    
    public List<LogicalEntity> getLogicalEntities() {
        return this.logicalEntities;
    }
}
