package igra;

public abstract class Zivotinja {
	protected Rupa mojaRupa;
	
	public Zivotinja(Rupa r) {
		mojaRupa=r;
	}
	
	public abstract void udarenaEff();
	public abstract void pobeglaEff();
	
	public abstract void iscrtaj();
}
