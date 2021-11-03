package karting;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

public class Krivina extends Specificnost implements Cloneable {

	private double allowedSpeed;
	private List<InfoClass>listaUslihVozila;
	private class InfoClass{
		private double brzinaPreKrivine;
		private Vozilo id;
		private InfoClass(Vozilo v,double brz) {
			brzinaPreKrivine=brz;
			id=v;
		}
	}
	public Krivina(double d) {
		allowedSpeed=d;
		listaUslihVozila = new ArrayList<Krivina.InfoClass>();
	}

	@Override
	public void ispoljiEfekat(Object v3) throws GNeodgovarajuciObjekat {
		if(!(v3 instanceof Vozilo)) {
			throw new GNeodgovarajuciObjekat();
		}
		else {
			if(allowedSpeed<=((Vozilo) v3).dohvMaksBrzinu()) {
				listaUslihVozila.add(new InfoClass(((Vozilo) v3),((Vozilo) v3).dohvMaksBrzinu()));
				((Vozilo)v3).postMaksBrzinu(allowedSpeed*((Vozilo) v3).dohvUpravljivost());
			}
		}
		
	}

	@Override
	public void ponistiEfekat(Object v3) throws GNeodgovarajuciObjekat {
		if(!(v3 instanceof Vozilo)) {
			throw new GNeodgovarajuciObjekat();
		}
		else if(!listaUslihVozila.isEmpty()){
			boolean found =false;
			int index=-1;
			for (InfoClass infoClass : listaUslihVozila) {
				if(infoClass.id==(Vozilo)v3) {
					found=true;
					index = listaUslihVozila.indexOf(infoClass);
					break;
				}
			}
			if(found) {
				((Vozilo)v3).postMaksBrzinu(listaUslihVozila.get(index).brzinaPreKrivine);
				listaUslihVozila.remove(index);
			}
		}

	}
	
	@Override
	public Krivina clone() {
		Krivina temp = new Krivina(allowedSpeed);
		for (InfoClass infoClass : listaUslihVozila) {
			temp.listaUslihVozila.add(infoClass);
		}
		return temp;
	}
	@Override
	public String toString() {
		return "K"+allowedSpeed;
	}

	public double getAllowedSpeed() {
		return allowedSpeed;
	}
	
}
