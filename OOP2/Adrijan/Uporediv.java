package paket;

public interface Uporediv {
	
	// int x = 5;

	boolean veca(Uporediv u) throws GreskaNeuporedivi;
	
	boolean jednaka(Uporediv u) throws GreskaNeuporedivi;
	
	default boolean manja(Uporediv u) throws GreskaNeuporedivi {
		return !veca(u) && !jednaka(u);
	}
	
}
