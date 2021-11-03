package generics;

/*
 * Genericke klase (i funkcije) su neophodne kada je potrebno parametrizovati 
 * neke tipove (odnosno funkcije) razlicitim drugim tipovima.
 * Prednost generika je to sto je odredjenu strukturu podataka 
 * ili odredjenu funkcionalnost (algoritam)
 * dovoljno definisati samo jednom (koristeci genericke parametre), 
 * a kasnije ih parametrizovati stvarnim argumentima (tipovima) u momentu njihovog koriscenja.
 * 
 * Do Java verzije 5 generici nisu postojali i primer pregenerickog koda je dat u nastavku:
 * Klasa PreGen je primer pregenerickog koda i objekti ove klase mogu da sadrze objekte 
 * bilo koje druge klase (jer su sve izvedene iz klase Object).
 */

class PreGen {
	
	private Object ref;
	
	public PreGen(Object ref) {
		this.ref = ref;
	}
	
	public void showType() {
		System.out.println(ref.getClass().getName());
	}
	
	public Object getRef() {
		return ref;
	}
}

public class PreGenDemo {
	
	public static void main(String[] args) {
		
		Object obj = Integer.valueOf(1);
		PreGen pg = new PreGen(obj);
		
		Integer valInt = (Integer) pg.getRef();
		System.out.println("valInt: " + valInt);
		
		//Ovo ipak predstavlja problem :/
		try {
			Double valDou = (Double) pg.getRef();
			System.out.println("valDou: " + valDou);
		} catch (ClassCastException cce) {
			System.err.println("Cast failed");
		}
	}
}