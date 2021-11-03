package exceptions;

/*
 * Prijavljivanjem izuzetka koristeci naredbu throw, njen operand moze biti samo nesto sto je Throwable.
 * Throwable je korena klasa svih izuzetaka i gresaka u jeziku Java i dve njene potklase su:
 * - Exception - predstavlja specificnu situaciju koju bi aplikacija mogla da razresi.
 * - Error - predstavlja ozbiljan problem koji aplikacija sama ne bi trebalo da resava.
 * 
 * Svi izuzeci izvedeni iz klase Exception (osim RuntimeException i njenih naslednika) i koji ne 
 * predstavljaju Error (ili njene naslednike) su proveravani izuzeci (proverava ih prevodilac). 
 * Proveravani izuzeci moraju biti navedeni u throws klauzulama metoda, konstruktora itd. koji ih 
 * mogu prijaviti.
 * */

public class Exceptions1 {

	public static void main(String[] args) {

		try {
			@SuppressWarnings("unused")
			int []arr = new int [Integer.MAX_VALUE];
		} catch (OutOfMemoryError e) {
			/*
			 * primer greske tipa java.lang.OutOfMemoryError
			 * (ukoliko nema dovoljno raspolozive memorije na heap-u)
			 * Ovakve greske aplikacija ne bi trebalo da obradjuje. 
			 */
		}
		
		try {
			int []arr = new int[10];
			arr[-5] = 3;
		} catch (ArrayIndexOutOfBoundsException e) {
			/*
			 * primer RunTimeException-a
			 * Ovo je primer neproveravanog izuzetka.
			 */
		}
		
		//forma try naredbe na jeziku Java:
		try {
			/*
			 * deo bloka koda na koji sumnjamo da moze proizvesti gresku
			 * treba da bude smesten u try blok
			 */
		} catch (Exception e) {
			//rukovalac izuzetka tipa Exception ili klasa izvedenih iz ovog tipa
		} finally {
			/* opciona grana finally ne mora da se navede,
			 * ali ako se navede uvek se izvrsava - desio se izuzetak ili ne 
			 */
		}
		
		try {
			//moze postojati try naredba bez catch bloka, ali mora da je sledi makar finally
		} finally {
			
		}
	}
}
