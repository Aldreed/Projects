package raspored;



public abstract class Sadrzaj {
	protected String naziv;
	protected Vreme pocetak;
	protected Vreme trajanje;
	protected char vrsta;
	
	
	public char getVrsta() {
		return vrsta;
	};
	

	public Sadrzaj(String naz,Vreme tra) {
		naziv = naz;
		trajanje=tra;
		try {
			pocetak= new Vreme(0,0);
		} catch (GVreme e) {
			
		}
		
	}


	public void setPocetak(Vreme pocetak) {
		this.pocetak = pocetak;
	}







	public String getNaziv() {
		return naziv;
	}







	public Vreme getTrajanje() {
		return trajanje;
	}


	public Vreme getPocetak() {
		return pocetak;
	}




	public abstract Vreme preklapaSe(Sadrzaj s)throws GVreme;
	
	public Vreme dohvPocetak() {
		
		return pocetak;
	}


		  @Override
			public String toString() {
				  int r2 = pocetak.vremeUMinutima()+trajanje.vremeUMinutima();
				Vreme temp;
				try {
					temp = new Vreme(r2/60,Math.floorMod(r2, 60));
					return vrsta +", "+naziv+ " | " + pocetak +" - "+temp;
				} catch (GVreme e) {
					
				}
				return null;
			}







		public void pomeri(Vreme pomeraj) throws GVreme {
			pocetak = Vreme.saberi(pocetak, pomeraj);
			
		}
	
}
