package pitanja;

import java.util.ArrayList;
import java.util.List;

public class ZbirkaPitanja { 
	private IteratorPitanja iterator;
	private List<Pitanje> pitanja = new ArrayList<Pitanje>();
	public ZbirkaPitanja() {
		
	}
	public void dodaj(Pitanje p3) {
	pitanja.add(p3);
	}
	public Pitanje dohvati(int i) throws GNemaPitanja {
		
		try {
			return pitanja.get(i);	
		}
		catch(IndexOutOfBoundsException e) {
			throw new GNemaPitanja();
		}
		 
	}
	public int getBroj() {
		return pitanja.size();
		}
	public IteratorPitanja iterator() {
		iterator=new IteratorPitanja(this);
		iterator.trenutniIndex=0;
		return iterator;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Pitanje pitanje : pitanja) {
			builder.append(pitanje);
			builder.append('\n');
		}
		return builder.toString();
	}
}
