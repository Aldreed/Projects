package threads;

class BankAccount {
	/*
	 * volatile - mora biti oznaceno stanje kojem pristupa (cita ili pise) vise niti.
	 * To znaci da nema kesiranja ovakvog stanja, 
	 * vec se svako citanje/upis vrsi direktno iz memorije, a ne iz kesa.
	 */
	private volatile int balance;
	
	public /*synchronized*/ void setBalance(int balance) {
		this.balance += balance;
	}
	
	public int getBalance() {
		return balance;
	}
}

class Client extends Thread {
	
	private volatile int totalDeposit;
	private BankAccount account;
	
	public Client(BankAccount account) {
		this.account = account;
	}
	
	public int getTotalDeposit() {
		return totalDeposit;
	}
	
	@Override
	public void run() {
		
		while(!isInterrupted()) {
			try {
				int deposit = (int) (Math.random() * 100);
				totalDeposit += deposit;
				account.setBalance(deposit);
				sleep((long) Math.random() * 250 + 250);
			} catch (InterruptedException e) {
				/*
				 * Ukoliko je tekuca nit bila prekinuta od strane druge niti
				 * dok je bila samosuspendovana (sleep) 
				 * onda je interrupt flag obrisan.
				 * Zato se mora pozivom ove metode izvrsiti samoprekidanje (postaviti interrupt flag)
				 * da bi mogla da se zavrsi run metoda.
				 */
				interrupt();
			}
		}
	}
}

public class Synchronization {

	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * Inicijalni balans je 0.
		 * Napravice se niz aktivnih klijenata koji ce vrsiti uplate na racun banke u opsegu [0, 100).
		 */
		BankAccount account = new BankAccount();
		Client clients[] = new Client[10];
		for(int i = 0; i < clients.length; i++)
			clients[i] = new Client(account);
		
		/*
		 * main nit pokrece klijente.
		 */
		for(int i = 0; i < clients.length; i++)
			clients[i].start();
		
		/*
		 * Samosuspendovanje main niti.
		 */
		Thread.sleep(2000);
		
		/*
		 * Prekidanje rada svih klijenata.
		 */
		for(int i = 0; i < clients.length; i++)
			clients[i].interrupt();
		
		/*
		 * main nit ceka da svi klijenti zavrse.
		 */
		for(int i = 0; i < clients.length; i++)
			clients[i].join();
		
		int totalDeposit = 0;
		for(int i = 0; i < clients.length; i++)
			totalDeposit += clients[i].getTotalDeposit();
		System.out.println("Clients deposited total: " + totalDeposit);
		
		System.out.println("Bank account balance: " + account.getBalance());
		
		/*
		 * Zasto suma svih uplata klijenata nije jednaka balansu racuna?
		 * Zbog jednog problema koji se manifestuje u visenitnim programima, 
		 * a koji se zove Utrkivanje niti (Race hazard):
		 * 
		 * public void setBalance(int balance) {
				this.balance += balance;
			}
			
			Operacija += nije atomicna, vec se sastoji od barem:
			- citanja starog balansa (get)
			- izracunavanja novog balansa (modify)
			- upisa novog balansa (set)
			
			Primer utrkivanja: balans je 1000, Client1 uplacuje 20, Client2 uplacuje 50
			Client 1:					Client2:
			getBalance 1000
			modifiedBalance 1000+20
			***context switch***
										getBalance 1000
										***context switch***
			writeNewBalance 1020
			***context switch***
										modifiedBalance 1000+50
										writeNewBalance 1050
										
			Sto znaci da je upis Client1 prakticno izgubljen.
			
			Resenje ovog problema je sinhronizacija (medjusobno iskljucivanje nad objektom), 
			odnosno omoguciti samo jednoj niti da ekskluzivno pristupa objektu.
			Za nit koja ekskluzivno pristupa objektu kaze se da poseduje bravu (monitor) objekta.
			Sinhronizacija se postize koriscenjem synchronized naredbe 
			ili koristeci modifikator metoda synchronized.
		 */
		
		/*
		 * Otkomentarisati synchronized modifikator metode setBalance klase BankAccount 
		 * i posmatrati kakav je efekat.
		 */
	}
}
