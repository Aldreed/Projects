package paket;

public class Broj implements Uporediv {
	
	private int broj;

	public Broj(int broj) {
		super();
		this.broj = broj;
	}

	public int getBroj() {
		if(broj == 0) throw new NeproverenIzuzetak();
		return broj;
	}

	@Override
	public String toString() {
		return "Broj [broj=" + broj + "]";
	}

	@Override
	public boolean veca(Uporediv u) throws GreskaNeuporedivi {
		if(!(u instanceof Broj)) {
			throw new GreskaNeuporedivi();
			// int i = 1;
		}
		return this.broj > ((Broj)u).broj;
	}

	@Override
	public boolean jednaka(Uporediv u) throws GreskaNeuporedivi {
		if(!(u instanceof Broj)) throw new GreskaNeuporedivi();
		return this.broj == ((Broj)u).broj;
	}

}
