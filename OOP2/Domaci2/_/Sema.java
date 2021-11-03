package raspored;

import java.util.ArrayList;
import java.util.List;

public class Sema {

	protected String naziv;
	protected Vreme pocetak ;
	protected Vreme kraj;
	protected List<Sadrzaj>l1;
	{
		try {
			l1= new ArrayList<Sadrzaj>();
			pocetak=new Vreme(8,0);
			kraj = new Vreme(22,0);
		} catch (GVreme e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Sema(String string) {
		naziv =string;
	}
	public Sema(String string, Vreme v1,Vreme v2) {
		pocetak=v1;
		kraj =v2;
	}
	
	public Sadrzaj getIndex(int i) throws GIndex {
		try {
			
		}
		catch(IndexOutOfBoundsException e) {
			throw new GIndex();
		}
		return l1.get(i);
	}

	public void dodaj(Sadrzaj s1) throws GDodaj {
		if(l1.isEmpty()) {
			l1.add(s1);
		}
		else {
			int temp=s1.pocetak.vremeUMinutima();
			int i=0;
			while(i<l1.size()&&temp>(l1.get(i).pocetak.vremeUMinutima()+l1.get(i).trajanje.vremeUMinutima())) {
			i++;	
			}
			if((temp+s1.trajanje.vremeUMinutima())>kraj.vremeUMinutima()) {
				throw new GDodaj();
			}
			else if(i==l1.size()) {
				l1.add(s1);
			}
			else {
				try {
					Sadrzaj temp1= new Ponavljajuci(s1.getNaziv(), s1.getTrajanje(),s1.getTrajanje());
					temp1.setPocetak(s1.dohvPocetak());
					temp1.pomeri(Vreme.saberi(l1.get(i).dohvPocetak(),l1.get(i).getTrajanje()));
					temp=temp1.getTrajanje().vremeUMinutima()+temp1.dohvPocetak().vremeUMinutima();
					while(i<(l1.size()-1)&&temp>l1.get(i+1).pocetak.vremeUMinutima()&&temp<=kraj.vremeUMinutima()) {
						
						temp1.pomeri(Vreme.saberi(l1.get(i+1).dohvPocetak(),l1.get(i+1).getTrajanje()));
						i++;
						temp=temp1.getTrajanje().vremeUMinutima()+temp1.dohvPocetak().vremeUMinutima();
						}
					if(temp>kraj.vremeUMinutima()) {
						throw new GDodaj();
					}
					else if(i==(l1.size()-1)) {
						s1.setPocetak(temp1.pocetak);
						l1.add(s1);
					}
				}
				catch(GVreme g) {
					throw new GDodaj();
					
				}
				
			}
		}
		
	}

	public double zauzetost() {
		int i = kraj.vremeUMinutima() - pocetak.vremeUMinutima();
		int j= 0;
		for (Sadrzaj sadrzaj : l1) {
			j += sadrzaj.getTrajanje().vremeUMinutima();
		}
		return (j/(i*1f))*100f;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("naziv :");
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
