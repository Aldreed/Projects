package karting;

import java.util.ArrayList;
import java.util.List;

public class Deonica implements Cloneable{
	private int count;
	private double duzina;
	private List<Specificnost>listaSpecificnosti;
	
	
	public Deonica(double d) {
		duzina =d;
		listaSpecificnosti=new ArrayList<Specificnost>(); 
		count=0;
	}


	public void dodajSpecificnost(Specificnost s1) {
		listaSpecificnosti.add(s1);
		count++;
		
	}


	public void izbaciSpecificnost(int Id) {
		int i = 0;
		for (Specificnost specificnost : listaSpecificnosti) {
			if(specificnost.dohvatiId()==Id) {
				listaSpecificnosti.remove(i);
				count--;
				break;
			}
			i++;
		}
		
	}


	public Specificnost dohvSpecificnost(int i) {
		return listaSpecificnosti.get(i);
	}
	
	
	public int getCount() {
		return count;
	}
	

	public double getDuzina() {
		return duzina;
	}


	@Override
	public Deonica clone() throws CloneNotSupportedException{
		Deonica temp = new Deonica(duzina);
		for (Specificnost specificnost : listaSpecificnosti) {
			temp.dodajSpecificnost(specificnost.clone());
		}
		return temp;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("deonica(");
		builder.append(duzina);
		builder.append("m)");
		for (Specificnost specificnost : listaSpecificnosti) {
			builder.append(specificnost);
		}
		return builder.toString();
	}
	

}
