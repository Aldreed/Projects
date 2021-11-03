package interfaces;

interface A {
	int CONSTANT = 1;
	default void def() {
		System.out.println("A def");
	}
	static void moo() {
		System.out.println("A moo");
	}
}

interface B {
	int CONSTANT = 2;
	default void def() {
		System.out.println("B def");
	}
}

class C implements A, B {

	/*
	 * Ukoliko klasa nasledjuje identicne default metode iz dva ili vise interfejsa 
	 * onda mora eksplicitno da redefinise takav default metod.
	 * Ukoliko se povratni tipovi tih metoda iz oba interfejsa ne poklapaju 
	 * onda klasa ne moze implementirati oba interfejsa.
	 */
	@Override
	public void def() {
		/*
		 * Ukoliko zelimo pristup default metodu iz jednog od ova dva interfejsa (npr. A):
		 * super.def() bi oznacavao poziv metoda def iz bazne klase (u ovom slucaju je to klasa Object) 
		 */
		A.super.def();
		
		/*
		 * Ukoliko zelimo pristup konstanti identicno nazvanoj u oba interfejsa:
		 * Samo CONSTANT bi bilo viseznacno.
		 */
		System.out.println("CONSTANT = " + B.CONSTANT);
		
		/*
		 * Ukoliko zelimo pristup statickom clanu iz nekog interfejsa:
		 * Bez obzira sto je moo jednoznacno mora se navesti ime interfejsa!
		 */
		A.moo();
	}
}

public class ClassWithInterfaces2 {

	public static void main(String[] args) {
		new C().def();
	}
}
