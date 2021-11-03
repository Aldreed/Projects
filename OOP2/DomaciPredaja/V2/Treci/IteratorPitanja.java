package pitanja;

public class IteratorPitanja {
	private ZbirkaPitanja myZbirka;
	int trenutniIndex = 0;
	
	 IteratorPitanja(ZbirkaPitanja myZbi) {
		myZbirka=myZbi;
	};
	public boolean postoji() {
		try {
			myZbirka.dohvati(trenutniIndex);
		} catch (GNemaPitanja e) {
			return false;
		}
		return true;
	}
	public Pitanje dohvati() throws GNemaPitanja {
		return myZbirka.dohvati(trenutniIndex);
	}
	public void sledece() throws GNemaPitanja {
		myZbirka.dohvati(trenutniIndex);
		trenutniIndex++;
		
	}
	
	
}
