package interfaces.cloneable;

/*
 * Nema potrebe da CoolPart implementira Cloneable eksplicitno, jer Part to vec radi.
 * I CoolPart jeste Cloneable.
 */
public class CoolPart extends Part {
	
	@Override
	public Part clone() {
		/*zove se clone metod iz klase Part, koji dalje zove clone metod klase Object, 
		 * koji vraca plitku kopiju.
		 */
		return super.clone();
	}

	@Override
	public String toString() {
		return "\"Cool\"" + super.toString();
	}
}
