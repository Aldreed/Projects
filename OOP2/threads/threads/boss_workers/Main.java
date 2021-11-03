package threads.boss_workers;

/*
 * U ovom paketu je primer koda gde se komunikacija izmedju aktivnih objekata vrsi na sledeci nacin:
 * - Postoji samo jedan sef. Dok on radi nijedan od radnika ne sme da radi. 
 * Sef obavestava sve radnike da krenu sa poslom i ceka dok svi radnici ne zavrse.
 * Tek kad svi radnici zavrse sa poslom, onda sef pocinje svoj posao.
 * - Postoji vise radnika kojima je sef nadredjen. 
 * Radnici cekaju da im sef kaze kada smeju da zapocnu sa poslom.
 * Kada zavrse posao oni obavestavaju sefa da je njih posao zavrsen.
 * Kada i poslednji radnik, kome je sef nadredjen, zavrsi posao, tek tada sef zapocinje posao.
 */

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Worker workers[] = new Worker[5];
		Boss boss = new Boss(workers);
		
		for(int i = 0; i < workers.length; i++)
			workers[i] = new Worker();
		
		for(int i = 0; i < workers.length; i++)
			workers[i].start();
		boss.start();
		
		Thread.sleep(2000);
		/*
		 * Dosta sa poslom.
		 */
		for(int i = 0; i < workers.length; i++) {
			workers[i].interrupt();
			workers[i].join();
		}
		
		boss.interrupt();
		boss.join();
		
		System.out.println("Bye from " + Thread.currentThread().getName());
	}
}
