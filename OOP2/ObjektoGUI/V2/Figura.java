package tekici;


public abstract class Figura {
	private Polje mojePolje;
	
	public Figura(Polje p) {
		mojePolje = p;
	}

	public void pomeri(Polje p) {
		if(p!=null&&p.dozvoljeno(this)) {
			Polje oldPolje=mojePolje;
			mojePolje=p;
			oldPolje.repaint();
		}
	}
	
	public Polje getMojePolje() {
		return mojePolje;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Figura other = (Figura) obj;
		if (mojePolje == null) {
			if (other.mojePolje != null)
				return false;
		} else if (mojePolje!=other.mojePolje)
			return false;
		return true;
	}
	
	public abstract void iscrtaj();
	
	
}
