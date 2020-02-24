package com.nortoh.src.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;

import javax.swing.JPanel;

public class GraphJPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Graph graph;

	public GraphJPanel(Graph graph) {
		this.graph = graph;
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		
		Iterator<Point> pointIter = graph.getPoints().iterator();
		
		while(pointIter.hasNext()) {
			Point point = pointIter.next();
			g.drawOval(point.x, point.y, 1, 1);
		}
		
		
	}


}
