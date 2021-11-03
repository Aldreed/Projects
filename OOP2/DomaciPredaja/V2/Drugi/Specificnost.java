package karting;

public abstract class Specificnost {
	static int nextID=0;
	private int ID=nextID++;
	public int dohvatiId() {
		return ID;
	}
	
	public abstract void ispoljiEfekat(Object v3)throws GNeodgovarajuciObjekat;
	public abstract void ponistiEfekat(Object v3)throws GNeodgovarajuciObjekat;
	
	@Override
	public abstract Specificnost clone()throws CloneNotSupportedException;
	

}
