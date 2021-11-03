package gui.drawing.shapes;

public class Heart extends Shape {

	@Override
	public int getX(double angle) {
		return (int)(factor * 16 * Math.pow(Math.sin(angle), 3));
	}

	@Override
	public int getY(double angle) {
		return -(int)(factor * (13 * Math.cos(angle) - 5 * Math.cos(2 * angle) 
				- 2 * Math.cos(3 * angle) - Math.cos(4 * angle)));
	}
}
