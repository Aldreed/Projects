package generics;

/*
 * U fajlu PreGenDemo.java je vidjeno zasto pregenericki kod ne odgovara.
 * Ekvivalentno resenje sa generickom varijantom:
 */

//T je tip-parametar generika
class Gen<T> {
	
	public T ref;
	
	public Gen(T ref) {
		this.ref = ref;
	}
	
	public void showType() {
		System.out.println(ref.getClass().getName());
	}
	
	public T getRef() {
		return ref;
	}
}

public class GenDemo1 {
	
	public static void main(String[] args) {
		
		Integer i = Integer.valueOf(1);
		Gen<Integer> gen = new Gen<>(i);
		
		Integer valInt = gen.getRef();
		System.out.println("valInt: " + valInt);
		
		/*
		 * Sledeca linija dovodi do greske, koja se javlja u vreme prevodjenja, 
		 * jer se ne moze implicitno konvertovati Integer u Double.
		 */
		//Double d = gen.getRef();		
	}
}