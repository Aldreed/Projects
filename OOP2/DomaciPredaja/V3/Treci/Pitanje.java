package pitanja;

public class Pitanje implements Cloneable {

	private String tekst;
	private int poeni;
	private double tezina;
	private static int GID=1;
	protected int ID;
	public Pitanje(String string, int i, double d) {
		tekst=string;
		poeni = i;
		if(d>10)d=10;
		if(d<1)d=1;
		tezina = d;
		ID=GID++;
	}
	public String getTekst() {
		return tekst;
	}
	public int getPoeni() {
		return poeni;
	}
	public double getTezina() {
		return tezina;
	}
	@Override
	public Pitanje clone() throws CloneNotSupportedException {
		Pitanje temp = (Pitanje)super.clone();
		return temp;
	}
	@Override
	public String toString() {
		return "Pitanje "+ID+": "+ tekst;
	}

}
