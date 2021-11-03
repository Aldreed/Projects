package generics;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/*
 * Interfejs Comparable omogucava uredjivanje objekata klase koja implementira taj interfejs.
 * Za samo poredjenje je zaduzena metoda compareTo.
 */
class Student implements Comparable<Student> {
	int id;
	String name;
	
	public Student(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public int compareTo(Student o) {
		return this.name.compareTo(o.name);
	}
	
	@Override
	public String toString() {
		return id + " " + name;
	}
}

public class Collects {

	public static void main(String[] args) {
		
		Student s1 = new Student(1, "Pera"), 
				s2 = new Student(2, "Zika"), 
				s3 = new Student(3, "Laza");
		
		/*
		 * List - Interfejs koji predstavlja uredjenu kolekciju (listu). Dozvoljava duplikate.
		 */
		
		/*
		 * ArrayList - lista koja se dinamicki menja, realizovana kao niz.
		 * Dodavanje u listu se podrazumevano vrsi na kraj. 
		 * Moguce je dodati vrednost i na odgovarajucu poziciju, pri cemu se vrednosti desno pomeraju.
		 * Uklanjanje je moguce sa bilo koje pozicije.
		 */
		System.out.println("====ArrayList====");
		List<Student> array_list = new ArrayList<Student>();
		//dodaje studente u listu, na kraj
		array_list.add(s1);
		array_list.add(s2);
		array_list.add(s3);
		for(Student s : array_list) {
			System.out.println(s);
		}
		System.out.println("Velicina: " + array_list.size());
		//uklanja prvi element iz liste
		array_list.remove(0);
		System.out.println("Velicina: " + array_list.size());
		
		/*
		 * LinkedList - lista koja se dinamicki menja, realizovana kao ulancana lista.
		 */
		
		System.out.println("====LinkedList====");
		List<Student> linked_list = new LinkedList<Student>();
		linked_list.add(s1);
		linked_list.add(s2);
		linked_list.remove(1);
		for(Student s : linked_list) {
			System.out.println(s);
		}
		System.out.println("Sadrzi s1: " + linked_list.contains(s1));
		System.out.println("Velicina: " + linked_list.size());
		
		/*
		 * Vector - dinamicki niz objekata
		 * Stack - podtip Vector, dodaje operacije push, pop itd.
		 */
		
		System.out.println("====Stack====");
		Stack<Student> stack = new Stack<Student>();
		stack.add(s1);
		stack.push(s2);
		stack.push(s3);
		for(Student s : stack) {
			System.out.println(s);
		}
		
		/*
		 * Deque - linearna kolekcija koja podrzava umetanje/uklanjanje sa oba kraja
		 * ArrayDeque - jedna od vise implementacija interfejsa Deque
		 */
		
		System.out.println("====ArrayDeque====");
		Deque<Student> dequeue = new ArrayDeque<Student>();
		dequeue.addFirst(s1);
		dequeue.addLast(s2);
		
		System.out.println("Deque, prvi element: " + dequeue.getFirst());
		System.out.println("Velicina: " + dequeue.size());
		
		/*
		 * Set - predstavlja kolekciju koja ne sadrzi duplikate (skup).
		 * HashSet - implementacija interfejsa Set. Neuredjeni skup.
		 * TreeSet - implementacija interfejsa Set. Uredjeni skup.
		 * U ovom slucaju TreeSet je uredjen leksikografski po imenima studenata rastuce.
		 */
		
		System.out.println("====HashSet====");
		Set<Student> hs = new HashSet<Student>();
		hs.add(s1);
		hs.add(s2);
		hs.add(s3);
		for(Student s : hs) {
			System.out.println(s);
		}
		System.out.println("Sadrzi s1: " + hs.contains(s1));
		System.out.println("Velicina: " + hs.size());
		
		System.out.println("====TreeSet====");
		Set<Student> ts = new TreeSet<Student>();
		ts.add(s1);
		ts.add(s2);
		ts.add(s3);
		for(Student s : ts) {
			System.out.println(s);
		}
		
		/*
		 * Map - predstavlja kolekciju koja mapira kljuceve na vrednosti.
		 * Pomocu kljuca je moguce dohvatiti vrednost iz mape.
		 * Ne sadrzi duplikate po kljucevima.
		 * HashSet - jedna implementacija interfejsa Map.
		 */
		
		System.out.println("====HashMap====");
		Map<Integer, Student> map = new HashMap<Integer, Student>();
		map.put(1, s1);
		map.put(2, s2);
		map.put(3, s1);
		Set<Entry<Integer, Student>> mapset = map.entrySet();
		for(Entry<Integer, Student> e : mapset) {
			System.out.println("Entry: [key = " + e.getKey() + " | val = " + e.getValue().name + "]");
		}
		System.out.println("==============");
	}
}