package ViewObjects;

import java.awt.Color;
import java.awt.Graphics;


public class Rectangle {
	private Color color;
	private int[] xPoints, yPoints;
	
	public Rectangle(int[] x, int[] y) {
		this.xPoints = x;
		this.yPoints = y;
		color = Color.lightGray;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillPolygon(xPoints, yPoints, xPoints.length);
	}
}
