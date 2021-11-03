package raspored;

import java.util.List;

public class Ponavljajuci extends Sadrzaj {

	protected Vreme period;
	public Ponavljajuci(String naz, Vreme tra, Vreme vreme) {
		super(naz, tra);
		period=vreme;
		vrsta = 'P';
		
	}
	
	public Vreme getPeriod() {
		return period;
	}
	@Override
	public char getVrsta() {
		return vrsta;
	}
	@Override
	public String toString() {
		return super.toString() + "T"+period;
	}
	@Override
	public Vreme preklapaSe(Sadrzaj s) throws GVreme {
		if(s instanceof Ponavljajuci) {
			int t1=pocetak.vremeUMinutima();
			int t2=s.pocetak.vremeUMinutima();
			Vreme ret =null;
			while(t1+trajanje.vremeUMinutima()<1440) {
				while(t2+s.trajanje.vremeUMinutima()<1440) {
					if(t2+s.trajanje.vremeUMinutima()<=t1||t1+trajanje.vremeUMinutima()<=t2) {
						t2+=((Ponavljajuci) s).period.vremeUMinutima();
						}
					else {
						if(t2>=t1) {
							ret = new Vreme(t2/60, t2%60);
						}
						else {
							ret = new Vreme(t1/60, t1%60);
						}
						return ret;
					}
				}
					t1+=period.vremeUMinutima();
					t2=s.pocetak.vremeUMinutima();
			}
			return null;
		}
		else {
			int t1=pocetak.vremeUMinutima();
			int t2=s.pocetak.vremeUMinutima();
			Vreme ret = null;
			while(t1+trajanje.vremeUMinutima()<1440) {
				if(t2+s.trajanje.vremeUMinutima()<=t1||t1+trajanje.vremeUMinutima()<=t2) {
					t1+=period.vremeUMinutima();
				}
				else {
					if(t2>=t1) {
						ret = new Vreme(t2/60, t2%60);
					}
					else {
						ret = new Vreme(t1/60, t1%60);
					}
					return ret;
				}
			}
			return ret;
		}
	}

}
