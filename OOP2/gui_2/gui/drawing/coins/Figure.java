package gui.drawing.coins;

import java.awt.Graphics;

public abstract class Figure {

	protected int x, y, width;
	
	protected Figure(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Figure))
			return false;
		Figure that = (Figure)obj;
		return x == that.x && y == that.y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public abstract void paint(Graphics g);
}
