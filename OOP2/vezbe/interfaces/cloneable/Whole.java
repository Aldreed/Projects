package interfaces.cloneable;

/*
 * Ova klasa predstavlja container klasu koja sadrzi objekat tipa Part (CoolPart ili NotCoolPart)
 */

public class Whole implements Cloneable {

	private Part p;
	private String name;
	
	public Whole(Part p, String name) {
		this.p = p;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Whole " + name + " : " + p;
	}
	
	public String getName() {
		return name;
	}
	
	public Part getPart() {
		return p;
	}
	
	@Override
	public Whole clone() {
		Whole copy = null;
		try {
			copy = (Whole) super.clone();
			copy.p = p.clone();
		} catch (CloneNotSupportedException e) {
			//nece se desiti
		}
		return copy;
	}
}
