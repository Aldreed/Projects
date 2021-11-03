package gui.drawing.shapes;

public class Circle extends Shape {

	private double r;
	
	public Circle(double r) {
		this.r = r;
	}
	
	@Override
	public int getX(double angle) {
		return (int)(factor * r * Math.sin(angle));
	}

	@Override
	public int getY(double angle) {
		return (int)(factor * r * Math.cos(angle));
	}

}
