package generics;

/*
 * Moguce je postaviti gornju granicu tipa parametra prilikom definisanja generickog tipa, 
 * odnosno T moze samo da bude zamenjeno stvarnim tipom, koji je izveden iz klase Number, 
 * kao sto su Integer, Double.
 */
class Gen_Bounded<T extends Number> {
	
	private T []ref;
	
	public Gen_Bounded(T []ref) {
		this.ref = ref;
	}
	
	public double average() {
		
		double d = 0;
		for(T t : ref)
			d += t.doubleValue();	//doubleValue definisan za tip Number
		return d == 0 ? 0 : d/ref.length;
	}
	
	public boolean sameAverage(Gen_Bounded<? extends Number> other) {
		return this.average() == other.average();
	}
	
	public T get(int ind) throws Exception {
		if(ind < 0 || ind >= ref.length)
			throw new Exception("Out of bounds");
		return ref[ind];
	}
	
	public void set(int ind, T val) throws Exception {
		if(ind < 0 || ind >= ref.length)
			throw new Exception("Out of bounds");
		ref[ind] = val;
	}
}

public class GenDemo2 {
	
	//I negenericke klase mogu definisati genericke funkcije
	private static <T extends Comparable<T>> void sortAsc(T []arr) {
		for(int i = 0; i < arr.length - 1; i++)
			for(int j = i + 1; j < arr.length; j++)
				if(arr[i].compareTo(arr[j]) > 0) {
					T temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
	}
	
	private static <T> void printArr(T []arr) {
		for(T val : arr)
			System.out.print(val + " ");
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		Integer []arrInt = new Integer [] { 3, 2, 5, 4, 1 };
		Double []arrDou = { 3., 1., 2. };
		Gen_Bounded<Integer> gInt = new Gen_Bounded<Integer>(arrInt);
		/*
		 * Ne mora se specificirati stvarni argument-tip u izrazu, 
		 * ukoliko se on moze zakljuciti na osnovu stvarnog argumenta generika.
		 * Gen_Bounded<Double> gDou = new Gen_Bounded<>(b);
		 * 
		 * je isto sto i
		 * 
		 * Gen_Bounded<Double> gDou = new Gen_Bounded<Double>(b);
		 */
		Gen_Bounded<Double> gDou = new Gen_Bounded<>(arrDou);
		System.out.println(gInt.sameAverage(gDou));
		
		/*
		 * Iako je tip Integer podtip tipa Number
		 * Gen_Bounded<Integer> nije podtip tipa Gen_Bounded<Number>
		 * pa sledeca linija dovodi do greske prilikom prevodjenja programa
		 */
		//Gen_Bounded<Number> gb = gInt;
		
		GenDemo2.<Integer>sortAsc(arrInt);
		printArr(arrInt);
		sortAsc(arrDou);
		printArr(arrDou);
	}
}