package raspored;

import java.util.ArrayList;
import java.util.List;

public class Ponavljajuci extends Sadrzaj {

	protected Vreme period;
	protected List<Vreme>l1;
	public Ponavljajuci(String naz, Vreme tr, Vreme vr) throws GVreme {
		super(naz,tr);
		this.VrstaSadrzaja='P';
		period=vr;
		l1 = new ArrayList<Vreme>();
		int i = pocetak.vremeUMinutima();
		while(i+trajanje.vremeUMinutima()<1440) {
			Vreme temp = new Vreme(Math.floorDiv(i, 60), Math.floorMod(i, 60));
			l1.add(temp);
			i+=period.vremeUMinutima();
		}
		
		
	}
	
	private void updateList() throws GVreme {
		l1.clear();
		int i = pocetak.vremeUMinutima();
		while(i+trajanje.vremeUMinutima()<1440) {
			Vreme temp = new Vreme(Math.floorDiv(i, 60), Math.floorMod(i, 60));
			l1.add(temp);
			i+=period.vremeUMinutima();
		}
	}
	
	@Override
	public void pomeri(Vreme pomeraj) throws GVreme {
		super.pomeri(pomeraj);
		updateList();
	}
	@Override
	public String toString() {
		
		return super.toString() +" T"+period;
	}
	@Override
	public Vreme preklapaSe(Sadrzaj s2) throws GVreme {
		int i =0;
		Vreme ret=null;
		if(s2 instanceof Ponavljajuci) {
			for (Vreme vreme : l1) {
				for (Vreme vreme2 : ((Ponavljajuci) s2).l1) {
					int pocetak1= vreme.vremeUMinutima();
					int pocetak2= vreme2.vremeUMinutima();
					if(pocetak2 + s2.trajanje.vremeUMinutima()<=pocetak1||pocetak2>=pocetak1+trajanje.vremeUMinutima()) {
						ret=null;
					}
					else {
						if(pocetak2>pocetak1) {
							ret=new Vreme(pocetak2/60,pocetak2%60);
							break;
						}
						else {
							ret=new Vreme(pocetak1/60,pocetak1%60);
							break;
						}
				}
			}
				if(ret!=null) {
					break;
				}
				}
			return ret;
			/*
			int r1 = s2.pocetak.vremeUMinutima();
			int r2 = pocetak.vremeUMinutima();
			while(r1+s2.trajanje.vremeUMinutima()<=pocetak.vremeUMinutima()&&r1<=1440) {
				r1+=period.vremeUMinutima();
			}
			if(r1>1440) {
				return null;
			}
			else if((r1-r2)<trajanje.vremeUMinutima()) {
				i= r2;
				return new Vreme(Math.floorDiv(i, 60),Math.floorMod(i, 60));
			}
			else {
				while(r1<=1440&&r2<=1440) {
					r2+= trajanje.vremeUMinutima();
					if(r2-trajanje.vremeUMinutima()>(r1+s2.trajanje.vremeUMinutima())) {
						r2-=trajanje.vremeUMinutima();
						r2+=period.vremeUMinutima();
						r1+=period.vremeUMinutima();
					}
					else if(r2<=r1) {
						r2-=trajanje.vremeUMinutima();
						r2+=period.vremeUMinutima();
						
						r1+=period.vremeUMinutima();
					}
					else {
						i=r2;
						return new Vreme(Math.floorDiv(i, 60),Math.floorMod(i, 60));
					}
				}
				
					return null;
				
			}
		*/	}
		else {
			for (Vreme vreme : l1) {
				if(!((vreme.vremeUMinutima()+trajanje.vremeUMinutima())<=s2.pocetak.vremeUMinutima()||(s2.trajanje.vremeUMinutima()+s2.pocetak.vremeUMinutima())<=vreme.vremeUMinutima())) {
					if(s2.pocetak.vremeUMinutima()>vreme.vremeUMinutima()) {
						i=s2.pocetak.vremeUMinutima();
					}
					else {
						i=vreme.vremeUMinutima();
					}
					return new Vreme(Math.floorDiv(i, 60),Math.floorMod(i, 60));
				}
			}
			return null;
		}
}
	
}
