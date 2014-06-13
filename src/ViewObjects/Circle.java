package ViewObjects;

import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	private int diameter, x, y;
	private Color color;
	
	public Circle(int size, int x, int y) {
		diameter = size;
		this.color = Color.blue;
		this.x = x;
		this.y = y;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public void draw(Graphics page) {
		page.setColor(color);
		page.fillOval(x, y, diameter, diameter);
	}
}
