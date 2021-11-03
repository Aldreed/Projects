package raspored;

import java.util.ArrayList;
import java.util.List;

public class Sema {
	protected Vreme pocetak;
	protected Vreme kraj;
	protected String naziv;
	protected List<Sadrzaj>l1;
	protected int count;
	
	public Sema(String string,Vreme poc,Vreme kr) {
		naziv=string;
		pocetak = poc;
		kraj=kr;
		count = 0;
		l1 = new ArrayList<Sadrzaj>();
		
	}
	public Sadrzaj getSadrzaj(int i) throws GIndex {
		if(l1.isEmpty()||i<0||i>=l1.size()) {
			throw new GIndex();
		}
		else {
			return l1.get(i);
		}
	}
	public int getCount() {
		return count;
	}

	public Sema(String string) throws GVreme {
		this(string,new Vreme(8,0),new Vreme(22,0));
		
	}
	public Sema(String str, Vreme vr) throws GVreme {
		this(str, vr, new Vreme(22,00));
	}
	
	public void dodaj(Sadrzaj s1) throws GDodaj, GVreme {
		if(s1==null) {
			throw new GDodaj();
			}
		int orgPoc = s1.dohvPocetak().vremeUMinutima();
		Vreme step = new Vreme(0,15);
		while(s1.dohvPocetak().vremeUMinutima()<pocetak.vremeUMinutima()) {
			s1.pomeri(step);
		}
		if(l1.isEmpty()) {
			count++;
			l1.add(s1);
		}
		else if(s1.dohvPocetak().vremeUMinutima()+s1.getTrajanje().vremeUMinutima()<=kraj.vremeUMinutima()) {
			for (Sadrzaj sadrzaj : l1) {
				Vreme insert =sadrzaj.preklapaSe(s1);
				while(s1.dohvPocetak().vremeUMinutima()+s1.getTrajanje().vremeUMinutima()<=kraj.vremeUMinutima()&&(insert!=null&&insert.vremeUMinutima()<kraj.vremeUMinutima())) {
						s1.pomeri(new Vreme(0,15));
						insert=sadrzaj.preklapaSe(s1);
				}
			}
			if(s1.dohvPocetak().vremeUMinutima()+s1.getTrajanje().vremeUMinutima()>kraj.vremeUMinutima()) {
				s1.setPocetak(new Vreme(orgPoc/60,orgPoc%60));
				throw new GDodaj();
			}
			else {
				count++;
				int i = 0;
				while(i<l1.size()&&s1.dohvPocetak().vremeUMinutima()>l1.get(i).dohvPocetak().vremeUMinutima()) {
					i++;
				}
				if(i==l1.size()) {
					l1.add(s1);
				}
				else {
					l1.add(i, s1);
				}
			}
		}
		else {
			s1.setPocetak(new Vreme(orgPoc/60,orgPoc%60));
			throw new GDodaj();
		}
	}

	public double zauzetost() {
		int dur = kraj.vremeUMinutima()-pocetak.vremeUMinutima();
		int i=0;
		for (Sadrzaj sadrzaj : l1) {
			if(sadrzaj instanceof Ponavljajuci ) {
				int k = sadrzaj.dohvPocetak().vremeUMinutima();
				while(k+sadrzaj.getTrajanje().vremeUMinutima()<kraj.vremeUMinutima()) {
					i+=sadrzaj.getTrajanje().vremeUMinutima();
					k+=((Ponavljajuci) sadrzaj).getPeriod().vremeUMinutima();
				}
			}
			else {
				i+=sadrzaj.getTrajanje().vremeUMinutima();
			}
		}
		return (i*1f)/dur*100f;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(naziv);
		builder.append(" : ");
		builder.append(pocetak);
		builder.append(" - ");
		builder.append(kraj);
		builder.append("\n");
		for (Sadrzaj sadrzaj : l1) {
			builder.append(sadrzaj);
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	

}
