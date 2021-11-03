package threads;

/*
 * Model komunikacije izmedju niti u jeziku Java je kooperativan model. 
 * To znaci da kood run metoda niti koje komuniciraju mora biti napisan tako 
 * da je nit u mogucnosti da salje signale drugim nitima, 
 * a u isto vreme da proverava signale dobijene od drugih niti i deluje u skladu sa njima.
 * 
 * Zastareo (zabranjen) model komunikacije ukljucuje metode resume, suspend, stop, destroy.
 */

class Active implements Runnable {

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		/*
		 * Staticki metodod interrupted proverava (i vraca boolean vrednost, status prekida) 
		 * da li je tekuca nit, koja poziva taj metod interrupted, bila prekidana.
		 * Ova metoda brise status prekida (postavlja ga na false).
		 */
		try {
			while(!Thread.interrupted()) {
			
				/*
				 * Nit koja pozove metod sleep se samosuspenduje (uspava, 
				 * privremeno prekida izvrsavanje) na odredjen broj milisekundi. 
				 * Nit ne gubi vlasnistvo nad zauzetim monitorima (bice reci kasnije).
				 */
				Thread.sleep(500);
				System.out.println(threadName + " : Doin' some task");
			}
		} catch (InterruptedException e) {
			/*
			 * Ovaj izuzetak se moze javiti ukoliko je neko pokusao da prekine tekucu nit 
			 * dok je ona bila samosuspendovana pozivom metode sleep.
			 * Zato je vazno da try blok okruzuje while ciklus, a ne obrnuto, 
			 * jer bi u suprotnom slucaju moglo da se desi da ne mozemo da prekinemo izvrsavanje niti 
			 * (to bi nam uspelo jedino ako bismo uspeli da joj posaljemo signal 
			 * u momentu kada nije samosuspendovana, tj. pozvala sleep).
			 * 
			 * Sta bi trebalo uraditi ukoliko while okruzuje try?
			 */
			System.out.println(threadName + " was interrupted while on sleep!");
		}
	}
}

public class BasicCommunication {

	public static void main(String[] args) throws InterruptedException {
		
		Thread t = new Thread(new Active());
		/*
		 * Dok se ne pokrene nit t, ona je u stanju NEW.
		 * Kada se pokrene i izvrsava u okviru JVM ona je u stanju RUNNABLE.
		 */
		System.out.println("Starting the thread from " + Thread.currentThread().getName());
		t.start();
		/*
		 * Pusticemo pokrenutu nit da malo radi..
		 * main nit prelazi u stanje TIMED-WAITING.
		 * Time sansu za izvrsavanje dobijaju druge niti.
		 */
		Thread.sleep(2100);
		/*
		 * main nit prekida (salje signal) niti t pozivom metode interrupt.
		 */
		t.interrupt();
		/*
		 * main nit ce sacekati da nit t zavrsi sa svojim izvrsavanjem pozivom metode join.
		 * Kada neka nit zavrsi sa svojim izvrsavanjem onda prelazi u stanje TERMINATED. 
		 * Kada nit t zavrsi bice TERMINATED, a za to vreme (dok ne zavrsi 
		 * i dok je main nit ceka) main nit ce biti u stanju WAITING. 
		 */
		t.join();
		System.out.println("Thread t state: " + t.getState());
		System.out.println("Bye from " + Thread.currentThread().getName());
	}
}
