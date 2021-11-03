package raspored;

public class Sadrzaj {

	String naziv ;
	Vreme trajanje;
	Vreme pocetak;
	
	 char VrstaSadrzaja;

	
	  @Override
	public String toString() {
		  int r2 = pocetak.vremeUMinutima()+trajanje.vremeUMinutima();
		Vreme temp;
		try {
			temp = new Vreme(r2/60,Math.floorMod(r2, 60));
			return VrstaSadrzaja +" , "+naziv+ " | " + pocetak +"-"+temp;
		} catch (GVreme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public char getVrstaSadrzaja() {
		return VrstaSadrzaja;
	}

	public String getNaziv() {
		return naziv;
	}

	public Vreme getTrajanje() {
		return trajanje;
	}

	public void setPocetak(Vreme pocetak) {
		this.pocetak = pocetak;
	}

	public Sadrzaj(String na, Vreme tr) throws GVreme {
		naziv=na;
		trajanje=tr;
		pocetak = new Vreme();
	}
	
	public void pomeri(Vreme pomeraj)throws GVreme {
		pocetak =Vreme.saberi(pocetak, pomeraj);
		
		
		
	}

	public Vreme dohvPocetak() {
		return pocetak;
	}

	public Vreme getPocetak() {
		return pocetak;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public void setTrajanje(Vreme trajanje) {
		this.trajanje = trajanje;
	}


	public void setVrstaSadrzaja(char vrstaSadrzaja) {
		VrstaSadrzaja = vrstaSadrzaja;
	}


	public Vreme preklapaSe(Sadrzaj s2) throws GVreme {
		int start1= pocetak.vremeUMinutima();
		int start2=s2.pocetak.vremeUMinutima();
		int kraj1 = start1 + trajanje.vremeUMinutima();
		int kraj2= start2 + s2.trajanje.vremeUMinutima();
		if(kraj2>kraj1) {
			if(start2<kraj1) {
				return new Vreme(Math.floorDiv(kraj1, 60),Math.floorMod(kraj1, 60));
			}
			else return null;
		}
		else if(kraj1>kraj2) {
			if(start1<kraj2) {
				return new Vreme(Math.floorDiv(kraj2, 60),Math.floorMod(kraj2, 60));
			}
			else return null;
		}
		else {
			return null;
		}
	
	}
}