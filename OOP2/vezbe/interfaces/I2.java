package interfaces;

/*
 * Pocevsi od verzije Java-e 8 interfejsi mogu definisati i: 
 * - default (podrazumevane) metode. One su implicitno public.
 * - staticke metode. One su implicitno public.
 */

/*
 * Od verzije Java-e 9 interfejsi mogu definisati i: 
 * - privatne nestaticke metode (helper metode za nestaticke default metode)
 * - privatne staticke metode (helper metode za staticke metode)
 * Privatne su da ne bi bile deo javnog ugovora, a dostupne su i za staticki i nestaticki kontekst.
 * 
 * U nastavku su one zakomentarisane, jer velika vecina vas ima instaliranu verziju Java-e 8.
 */

public interface I2 {

	/*private void helper_def() {
		System.out.println("helper_def");
	}*/
	
	//podrazumeva se public
	default void def() {
		//helper_def();
		System.out.println("default metod");
	}
	
	/*private static void helper_stat() {
		System.out.println("helper_stat");
	}*/
	
	//podrazumeva se public
	static void stat() {
		//helper_stat();
		System.out.println("static metod");
	}
}