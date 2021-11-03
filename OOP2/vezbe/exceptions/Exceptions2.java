package exceptions;

/*
 * Korisnik svoje izuzetke treba da izvodi iz klase Exception.
 */

@SuppressWarnings("serial")
class Greska extends Exception {
	
	public Greska(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		/* poziv toString metoda natklase ispisuje sadrzaj objekta u formatu:
		 * ime_paketa.ime_klase : poruka
		 */
		return super.toString();
	}
}

public class Exceptions2 {

	public static void main(String[] args) {
		
		try {
			throw new Greska("poruka");
		} catch (Greska g) {
			System.err.println(g);
			return;
		} catch (Exception e) {
			/* 
			 * rukovalac izuzetka opstijeg tipa ne sme da se nadje ispred 
			 * rukovaoca izuzetka specificnog tipa
			 */
		} finally {
			System.out.println("Uvek se izvrsava finally grana");
		}
	}
}
