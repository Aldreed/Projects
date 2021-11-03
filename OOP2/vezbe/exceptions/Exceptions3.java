package exceptions;

@SuppressWarnings("serial")
class Base_Ex extends Exception {}

@SuppressWarnings("serial")
class Derived_Ex extends Base_Ex {}
@SuppressWarnings("serial")
class Another_Derived_Ex extends Base_Ex {}

class Base {
	public void foo() throws Base_Ex {
		//bezuslovno bacanje izuzetka
		throw new Base_Ex();
	}
}

class Derived extends Base {
	@Override
	public void foo() throws Derived_Ex, Another_Derived_Ex {
		/* throws klauzula nadjacane metode bazne klase u izvedenoj klasi mora da bude kompatibilna
		 * sa throws klauzulom odgovarajuce metode iz bazne klase.
		 * Ne sme da je prosiruje novim tipovima izuzetaka koji nisu ujedno izvedeni 
		 * iz tipova izuzetaka u throws klauzuli metode iz bazne klase.
		 * Moze da suzi throws klauzulu metode iz bazne klase.
		 */
		try {
			super.foo();
		} catch (Base_Ex b) {
			System.out.println("Uhvacen od strane Base_Ex rukovaoca; Derived.foo");
			throw new Derived_Ex();
		} finally {
			System.out.println("Finally - metoda foo klase Derived");
		}
	}
}

public class Exceptions3 {

	public static void main(String[] args) {
		
		Derived d = new Derived();
		try {
			d.foo();
		} catch (Derived_Ex | Another_Derived_Ex e) {
			System.out.println("Uhvacen od strane multi-catch rukovaoca");
			/* zavisi od implementacije, tipicno tekstualno prezentuje sam izuzetak,
			 * a zatim i ceo call stack na kome se nalaze okviri redom pozivanih funkcija
			 * od funkcije u kojoj je izuzetak prijavljen 
			 * pa do funkcije koja sadrzi rukovalac koji taj izuzetak obradjuje.
			 */
			e.printStackTrace();
			/* U multi-catch rukovaocu je parametar rukovaoca finalan
			 * sto znaci da ne bi bilo moguce napisati
			 * e = new Derived_Ex();
			 * ali je moguce koristeci referencu e promeniti referisani objekat
			 */
		} finally {
			System.out.println("Finally - metoda main klase Exceptions_3");
		}
		System.out.println("Ispis van try naredbe");
		
		//===========================
		//Alternativa za multi-catch
		//===========================
		d = new Derived();
		try {
			d.foo();
		} catch (Derived_Ex e) {
			System.out.println(e);
			//e nije final
			e = new Derived_Ex();
		} catch (Another_Derived_Ex e) {
			System.out.println(e);
		}
	}
}
