package interfaces;

/*
 * Interfejsi su u Java-i inicijalno zamisljeni kao stvar cistog ugovora, odnosno treba samo da 
 * naznace koje su funkcionalnosti odredjenog tipa, koji bude implemetirao ovakav interfejs.
 * Neki mogu da budu samo markirajuci (npr. Cloneable, Serializable, itd.) i oni su potpuno prazni, 
 * odnosno ne postoje nikakve deklaracije funkcija/konstanti u definiciji samog interfejsa.
 * 
 * Zakljucno sa verzijom Java-e 7 oni i jesu samo ugovor: 
 * - mogu da sadrze samo deklaracije apstraktnih metoda
 * - mogu da sadrze i definicije konstanti
 * 
 * Ne postoji koreni interfejs u hijerarhiji interfejsa (kao npr. klasa Object koja je u korenu 
 * hijerarije klasa).
 */

public interface I1 {

	/*
	 * Konstante su oznacene modifikatorima public, static i final. 
	 * Sva 3 modifikatora se podrazumevaju.
	 * Konstante se moraju inicijalizovati na mestu definicije, 
	 * jer interfejsi ne mogu da definisu konstruktore
	 * niti (ne)staticke inicijalizacione blokove.
	 */
	int C = 1;
	
	/*
	 * Podrazumeva se da je foo i public i abstract metoda. Ekvivalentne deklaracije su i:
	 * - public void foo();
	 * - abstract void foo();
	 * - public abstract void foo();
	 */
	void foo();
}