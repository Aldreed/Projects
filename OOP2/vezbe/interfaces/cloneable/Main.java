package interfaces.cloneable;

public class Main {

	public static void main(String[] args) {

		Part p1 = new CoolPart();
		Whole w1 = new Whole(p1, "name");

		Whole w2 = w1.clone();

		System.out.println(w1);
		System.out.println(w2);
		System.out.println(w1.getPart() == w2.getPart());
		System.out.println(w1.getName() == w2.getName());
	}
}
