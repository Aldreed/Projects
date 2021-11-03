package paket;

public class Pravougaonik implements Uporediv {
	
	private double duzina;
	private double sirina;
	
	public double povrsina() {
		return duzina * sirina;
	}

	public double getDuzina() {
		return duzina;
	}

	public double getSirina() {
		return sirina;
	}
	
	

	@Override
	public String toString() {
		return "Pravougaonik [duzina=" + duzina + ", sirina=" + sirina + "]";
	}

	public Pravougaonik(double duzina, double sirina) {
		super();
		this.duzina = duzina;
		this.sirina = sirina;
	}

	@Override
	public boolean veca(Uporediv u) throws GreskaNeuporedivi {
		if(!(u instanceof Pravougaonik)) throw new GreskaNeuporedivi();
		return this.povrsina() > ((Pravougaonik)u).povrsina();
	}

	@Override
	public boolean jednaka(Uporediv u) throws GreskaNeuporedivi {
		if(!(u instanceof Pravougaonik)) throw new GreskaNeuporedivi();
		return this.povrsina() == ((Pravougaonik)u).povrsina();
	}

}
