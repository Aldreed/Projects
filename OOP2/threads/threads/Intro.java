package threads;

/*
 * Nit (Thread) - jedna nezavisna putanja izvrsavanja programa u Java-i.
 * Svi Java programi imaju barem jednu nit.
 * Na pocetku izvrsavanja programa je to main thread, 
 * koja poziva main metod i koju kreira Java virtuelna masina.
 * 
 * Jednonitno programiranje (single-threaded) - podrazumeva postojanje jedne niti u programu
 * Visenitno programiranje (multi-threaded) - podrazumeva postojanje vise niti u programu
 * 
 * Konkurentno izvrsavanje - podrazumeva deljenje jedne procesirajuce jedinice (konkurise se za resurs)
 * Paralelno izvrsavanje - podrazumeva postojanje vise procesirajucih jedinica (stvarni paralelizam)
 * 
 * Kreiranje niti u Java-i je moguce na dva nacina:
 * - prosirivanjem klase Thread i redefinisanjem metode run.
 * - implementiranjem interfejsa Runnable i definisanjem metode run.
 * 
 * Drugi nacin je preporucljiviji, jer se njime ne gubi mogucnost izvodjenja iz jos jedne klase.
 * 
 * Metod run treba da sadrzi programski kod koji ce novokreirana nit izvrsavati. 
 * Nit zavrsava svoje izvrsavanje kada se zavrsi njen metod run.
 */

class ActiveExt extends Thread {
	
	/*
	 * Thread.currentThread() - staticka metoda koja vraca referencu na tekuce izvrsavanu nit
	 */
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " - Hello from ActiveExt!");
	}
}

class ActiveImp implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " - Hello from ActiveImp!");
	}
}

public class Intro {

	public static void main(String[] args) {
		/*
		 * Da bi se kreirala nova nit neophodno je instancirati aktivni objekat klase Thread 
		 * ili klase izvedene iz klase Thread.
		 */
		ActiveExt ae = new ActiveExt();
		
		/*
		 * Da bi se pokrenuo novokreirani aktivni objekat neophodno je pozvati njegovu metodu start.
		 * Poziv metoda start omogucava da se nit pokrene, ali JVM je ta koja poziva njen metod run.
		 * Ilegalno je za neki aktivni objekat vise od jedanput pozvati metod start 
		 * (IllegalThreadStateException se desava u tom slucaju).
		 * 
		 * U ovom slucaju glavna (main) nit poziva metod start i odmah se vraca iz tog poziva. 
		 */
		ae.start();
		/*
		 * Pozivom metode yield tekuca nit nagovestava rasporedjivacu niti 
		 * da je voljna da prepusti procesor, 
		 * ali rasporedjivac niti moze ovaj zahtev u potpunosti da ignorise.
		 */
		Thread.yield();
		System.out.println(Thread.currentThread().getName() + " - Hello from main thread");
		
		/*
		 * Kreiranje niti koristeci objekat klase koja implementira interfejs Runnable. 
		 * Metod run klase ActiveImp ce biti pozvan od strane JVM kada se nit startuje.
		 */
		Thread ai = new Thread(new ActiveImp());
		
		/*
		 * Semanticki pogresno bi bilo direktno pozvati metod run iz glavne niti, 
		 * jer bi se onda kod iz metoda run izvrsio u glavnoj niti.
		 */
		ai.run();
		
		ai.start();
		Thread.yield();
		System.out.println(Thread.currentThread().getName() + " - Bye from main thread");
	}
}