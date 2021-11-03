package baloni;

public class Vektor implements Cloneable {
	private double x;
	private double y;
	
	public Vektor(double x1,double y1) {
		x=x1;
		y=y1;
	}
	
	public static Vektor saberi(Vektor v1,Vektor v2) {
		return new Vektor(v1.getX()+v2.getX(), v1.getY()+v2.getY());
	}
	
	public static Vektor pomnozi(Vektor v,double m) {
		return new Vektor(v.getX()*m, v.getY()*m);
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public Vektor clone()  {
		try {
			return (Vektor) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}
	

}
