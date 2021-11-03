package gui.drawing.shapes;

public abstract class Shape implements Cloneable {

	protected int factor = 3;
	
	public void setFactor(int factor) {
		this.factor = factor;
	}
	
	public abstract int getX(double angle);
	public abstract int getY(double angle);
	
	@Override
	public Shape clone() {
		try {
			return (Shape) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
