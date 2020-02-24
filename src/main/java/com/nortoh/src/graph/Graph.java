package com.nortoh.src.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import com.nortoh.src.math.ExpressionEvaluator;

public class Graph {

	private JFrame graphFrame;
	private GraphJPanel graphPanel;
	private ExpressionEvaluator expressionEval;

	// Can we get away with this being a double? or int?
	private double[] xCoordinate;
	private double[] yCoordinate;
	
	// lets use the built-in class for now 
	private List<Point> points;
	
	private int maxX, maxY;

	public Graph(ExpressionEvaluator expression, int maxX, int maxY) {
		this.expressionEval = expression;
		this.graphPanel = new GraphJPanel(this);
		this.graphFrame	= new JFrame("Graph");
		
		this.points = new ArrayList<Point>();
		
		this.maxX = maxX;
		this.maxY = maxY;
		
		gatherPoint();
	}

	private void gatherPoint() {
		for(double x = 1; x < maxX; x+= 0.1) {
			this.expressionEval.getExpression().setVariable("x", x);
			Point point = new Point();
			point.setLocation(x, this.expressionEval.evaluate());
			points.add(point);
		}
	}

	public void displayGraph() {
		graphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		graphFrame.getContentPane().add(graphPanel);
		graphFrame.setSize(maxX,maxY);
		graphFrame.setLocation(200,200);
		graphFrame.setVisible(true);
	}
	
	public void removeGraph() {
		graphFrame.setVisible(false);
	}
	
	public List<Point> getPoints() {
		return this.points;
	}
	
	public ExpressionEvaluator getExpressionEval() {
		return expressionEval;
	}

	public int getMaxX() {
		return this.maxX;
	}
	
	public int getMaxY() {
		return this.maxY;
	}
}
