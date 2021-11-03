package interfaces.cloneable;

//podrzava kloniranje i dopusta potklasama da se kloniraju
class Type_One implements Cloneable {
	
	@Override
	public Type_One clone() {
		Type_One self = null;
		try {
			self = (Type_One) super.clone();
			//duboka kopija (ukoliko je potrebna)
		} catch (CloneNotSupportedException e) {
			//nece se desiti
		}
		return self;
	}
}

class Piece implements Cloneable {
	
	@Override
	protected Object clone() {
		return null;
	}
}

//uslovno podrzava kloniranje
class Type_Two implements Cloneable {
	
	private Piece piece;
	
	@Override
	public Type_Two clone() throws CloneNotSupportedException {
		
		Type_Two copy = (Type_Two) super.clone();
		copy.piece = (Piece) this.piece.clone();
		return copy;
	}
}

//ne podrzava kloniranje, ali dopusta potklasama da se kloniraju
class Type_Three {
	
	@Override
	protected Type_Three clone() {
		
		Type_Three copy = null;
		try {
			copy = (Type_Three) super.clone();
			//duboka kopija (ukoliko je potrebna)
		} catch (CloneNotSupportedException e) {
		}
		return copy;
	}
}

//ne podrzava kloniranje niti dopusta potklasama da se kloniraju
class Type_Four {
	
	@Override
	public final Type_Four clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}

public class CloneableAffinity {

	public static void main(String[] args) {
		
		Type_One to = new Type_One();
		Type_One clone = to.clone();
		System.out.println("Ne referise isti objekat: " + (to != clone));
	}
}
