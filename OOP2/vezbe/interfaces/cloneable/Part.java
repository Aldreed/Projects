package interfaces.cloneable;

/*
 * Cloneable je markirajuci interfejs, odnosno nema deklarisane nikakve metode ili konstante.
 * Implementirajuci Cloneable interfejs klasa podrzava kopiranje koristeci metod clone.
 * Metod clone je zasticeni metod iz klase Object, koji u throws klauzuli navodi 
 * CloneNotSupportedException kao tip izuzetka koji moze da prijavi.
 * Takav izuzetak se prijavljuje ukoliko se pokusa kloniranje objekta cija klasa ne implementira
 * interfejs Cloneable.
 */

public abstract class Part implements Cloneable {

	private static int sid;
	private int id = ++sid;
	
	@Override
	public String toString() {
		return "P" + id;
	}
	
	//clone metod ne baca izuzetke, te klasa Part potpuno podrzava kloniranje
	@Override
	public Part clone() {
		Part p = null;
		try {
			/*super.clone() zove clone metod klase Object i vraca kopiju tekuceg objekta.
			 * U pitanju je plitka kopija (byte po byte) i takva kopija obicno ne odgovara: 
			 * - za polja koja su reference, osim nepromenljivih (Immutable objekata) tipa String npr.
			 * - za polja cija je promena neophodna 
			 * Object.clone() vraca referencu tipa Object na kopirani objekat pa je cast neophodan!
			 * Pozivom ovog metoda clone se izbegava poziv konstruktora klase Part. 
			 */
			p = (Part) super.clone();
			p.id = ++sid;
			/*
			 * Razmotriti situaciju u kojoj je polje 'id' finalno!
			 */
			
		} catch (CloneNotSupportedException e) {
			/*nece se nikad desiti, ali je clone metod iz bazne klase Object deklarisan tako 
			 * da moze da prijavi izuzetak tipa CloneNotSupportedException
			 */
		}
		return p;
	}
}
