package nested;

/*
 * Ugnezdeni tipovi su tipovi definisani unutar neke druge klase (cesce) ili interfejsa (redje).
 * Razlog za koriscenje ovakvih konstrukata je sledeci: 
 * - ugnezdeni tip je obicno neophodan samo tipu u kome je definisan.
 * - na taj nacin se povecava enkapsulacija
 * - kod postaje citljiviji
 * 
 * Ugnezdene klase se dele na dve vrste: 
 * - static nested classes - staticke ugnezdene klase (obicno usluzne klase)
 * - inner classes - unutrasnje klase (postoji secondary this, 
 * specijalna veza objekta unutrasnje klase sa objektom spoljasnje klase)
 * 
 * Obe vrste imaju potpuno pravo pristupa clanovima okruzujuceg tipa, 
 * a i okruzujuci tip ima potpuno pravo pristupa ugnezdenom tipu.
 */

public class Outer {
	
	private int x = 1;
	private static int sx = 0;
	
	/*
	 * Unutrasnje klase ne mogu da definisu staticke clanove.
	 * Imaju direktan pristup clanovima spoljasnjeg tipa.
	 */
	class Inner_class {
		private int x = 2;
		
		public void foo(int x) {
			System.out.println("param x = " + x);
			System.out.println("Inner_Class x = " + this.x);
			//Outer.this je secondary this - referenca na objekat obuhvatajuce klase Outer
			System.out.println("Outer x = " + Outer.this.x);
		}
	}
	
	/*
	 * Staticke ugnezdene klase nemaju direktan pristup nestatickim clanovima spoljasnje klase Outer.
	 * Nestatickim clanovima mogu pristupiti preko reference na objekat spoljasnje klase.
	 * Statickim clanovima mogu pristupiti direktno.
	 */
	protected static class Static_class {
		//mogu da definisu staticke clanove
		private static int stat_int = 1;
		
		public void foo() {
			//nije dozvoljeno
			//x = 3;
			sx = stat_int;
			Outer o = new Outer();
			o.x = sx + 1;
		}
	}
	
	public void foo() {
		Inner_class n = new Inner_class();
		//Spoljasnja klasa ima pristup do clanova unutrasnje klase.
		n.x = 1;
	}
	
	public static void main(String[] args) {
		Outer.Static_class stat_obj = new Outer.Static_class();
		stat_obj.foo();
		
		Outer list = new Outer();
		//Neophodan je objekat spoljasnje klase (list ga referise) za kreiranje objekta unutrasnje.
		Outer.Inner_class inner_obj = list.new Inner_class();
		inner_obj.foo(3);
	}
}