package gui.drawing.shapes;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class CanvasFail extends Canvas {
	
	private Shape shape;
	private Color lineColor = Color.BLACK;
	private long sleepTime = 25;
	private int steps = 100;
	
	public CanvasFail() {
		shape = new Heart();
	}
	
	@Override
	public void paint(Graphics g) {
		Shape shape = this.shape.clone();
		g.setColor(lineColor);
		g.translate(this.getWidth() / 2, this.getHeight() / 2);
		double inc = 2 * Math.PI / steps;
		int x = shape.getX(0), y = shape.getY(0), endX, endY;
		
		try {
			for(double angle = 0; angle < 2 * Math.PI; angle += inc) {
				Thread.sleep(sleepTime);
				endX = shape.getX(angle);
				endY = shape.getY(angle);
				g.drawLine(x, y, endX, endY);
				x = endX;
				y = endY;
			}
		} catch (InterruptedException e) {}
	}

	public synchronized void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public synchronized void setFactor(int factor) {
		if(shape != null)
			shape.setFactor(factor);
	}
	
	public synchronized void setColor(Color color) {
		lineColor = color;
	}
	
	public synchronized void setBgColor(Color color) {
		setBackground(color);
	}
}
