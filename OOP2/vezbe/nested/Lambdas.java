package nested;

/*
 * FunctionalInterface je anotacija kojom se tip interfejsa oznacava kao funkcionalni interfejs.
 * Takvi interfejsi se mogu koristiti prilikom instanciranja objekata koji predstavljaju lambde.
 * Funkcionalni interfejs moze imati najvise JEDAN apstraktan metod.
 */
@FunctionalInterface
interface CheckVal {
	boolean test(int val);
	default void foo() {}
	static void s_foo() {}
}

/*
 * Lambda izrazi su korisni kada su nam neophodne klase koje imaju tacno jedan metod.
 * Za razliku od rogobatnih izraza, koji su nepohodni kada se instancira objekat anonimne klase, 
 * lambda izrazi su elegantni, najcesce jednolinijski izrazi.
 * Lambda izraz ne predstavlja nista drugo do neimenovani objekat bezimene klase, 
 * koji predstavlja funkcionalnost/kod koji je neophodno proslediti kao argument neke druge funkcije.
 */
public class Lambdas {
	
	/*
	 * Privatna staticka funkcija klase Lambdas koja uslovno ispisuje vrednosti niza arr.
	 * Ispunjenost uslova odredjuje se pozivom metode test (iz interfejsa CheckVal), 
	 * kojoj se prosledi argument - u svakoj iteraciji po jedan element niza.
	 */
	static void print(int []arr, CheckVal checker) {
		for(int val : arr)
			if(checker.test(val))
				System.out.print(val + " ");
		System.out.println();
	}
	
	int fld = 3;
	
	public static void main(String[] args) {

		int []arr = new int [10];
		for(int i = 0; i < 10; i++)
			arr[i] = i;
		
		/*
		 * 1. Lokalna klasa EvenTest - njen metod test vraca vrednost true samo ako je val paran.
		 * Lokalne klase se definisu u okviru tela funkcije.
		 * Predstavljaju vrstu unutrasnje klase. 
		 * Cesto se koristi kada je u odredjenom scope-u (dosegu) neophodno vise objekata takve klase.
		 */
		class EvenTest implements CheckVal {
			@Override
			public boolean test(int val) {
				return val % 2 == 0;
			}
		}
		//ispis parnih vrednosti
		print(arr, new EvenTest());
		
		/*
		 * 2. Objekat anonimne klase se prosledjuje kao argument metode print.
		 * U ovom slucaju se ispisuju samo neparni brojevi.
		 * 
		 * Anonimne klase su obicno korisne kada postoji vise apstraktnih metoda. 
		 * Mana im je sto prave rogobatni, previse opisni kod (too verbose code).
		 * Cesto se koristi kada je neophodan najvise jedan objekat takve klase.
		 */
		CheckVal anonymus = new CheckVal() {
			
			@Override
			public boolean test(int val) {
				return val % 2 == 1;
			}
		}; 
		print(arr, anonymus); 
		
		/*
		 * 3. Lambda izraz - ispis brojeva sa vrednoscu manjom od x
		 * Osnovni oblik lambda izraza je:
		 * (tip_parametra ime_parametra) -> { telo_lambda_funkcije }
		 * Ovaj izraz se predstavlja vid definicije metoda test interfejsa CheckVal, 
		 * a u stvari je to objekat anonimne klase.
		 * 
		 * telo_lambda_funkcije predstavlja implementaciju metoda test
		 */
		int x = 5;
		print(arr, (int par) -> { return par < x; });
		
		/*
		 * 4. Lambda izraz - ispis brojeva sa vrednoscu vecom od x
		 * Nije neophodno pisati zagrade u kojima ce se navesti parametar lambda izraza 
		 * ukoliko je to jedini parametar apstraktne metode funkcijskog interfejsa.
		 * Takodje, nije neophodno napisati ni tip parametra - on se moze zakljuciti 
		 * iz potpisa metode interfejsa CheckVal.
		 */
		print(arr, par -> { return par > x; });
		
		/*
		 * Lambda izrazi, kao i anonimne i lokalne klase mogu pristupati:
		 * - lokalnim promenljivama funkcije u kojoj su definisani (ali ih ne mogu menjati!)
		 * - parametrima funkcije u kojoj su definisani (ali ih ne mogu menjati!)
		 * - nestatickim clanovima klase u cijoj metodi su definisani
		 * - statickim clanovima klase
		 */
		
		int localVar = 3;
		print(arr, par -> {
			/* ne moze direktno pristupiti nestatickom polju fld klase Lambdas, 
			 * jer se trenutno nalazimo u statickom kontekstu (main metod klase Lambdas)
			 */
			Lambdas l = new Lambdas();
			l.fld = 5;
			//nije dozvoljeno
			//localVar = 4;
			return localVar > x;
		});
	}
}
