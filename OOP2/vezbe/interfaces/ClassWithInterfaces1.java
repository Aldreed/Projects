package interfaces;

interface Base_I {
	void foo();
}

//Interfejsi mogu prosirivati jedan ili vise interfejsa.
interface Derived_I extends Base_I {
	int CONSTANT = 1;
	void moo();
}

interface Standalone_I {
	void moo();
}

/*
 * U ovom primeru klasa ne moze da implementira i interfejs Standalone_I i interfejs Other_I, 
 * jer se potpisi njihovih metoda "moo()" razlikuju u povratnom tipu. Stoga je moguce implementirati 
 * samo jedan od ova dva interfejsa.
 */
interface Other_I {
	int moo();
}

/*
 * Klasa moze prosirivati jednu klasu i u isto vreme implementirati jedan ili vise interfejsa.
 * Ukoliko klasa ne definise sve apstraktne metode interfejsa koje implementira, ona mora biti
 * proglasena apstraktnom.
 */
class Sound_class implements Derived_I, Standalone_I {

	//Metod foo nasledjen iz interfejsa Base_I preko interfejsa Derived_I
	@Override
	public void foo() {
		System.out.println("foo");
	}

	//Metod moo iz interfejsa Derived_I, Standalone_I
	@Override
	public void moo() {
		System.out.println("moo");
	}
}

public class ClassWithInterfaces1 {

	public static void main(String[] args) {
		Base_I bi = new Sound_class();
		bi.foo();
		Derived_I di = (Derived_I) bi;
		di.moo();
	}
}
