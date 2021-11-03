package paket;

public class Test {

	public static void main(String[] args) {
		
		Uredjivac uredjivac = new RastuciUredjivac();
		
		Uporediv[] niz = new Uporediv[] {
				new Broj(5),
				new Broj(-3),
				/* new Pravougaonik(2, 2), */
				new Broj(0),
				new Broj(4)
		};
		
		uredjivac.uredi(niz);
		
		for(Uporediv u : niz) {
			System.out.println(u);
		}
		
		/*Uredjivac opadajuciUredjivac = new Uredjivac() {
			
			@Override
			public void uredi(Uporediv[] niz) {
				for(int i = 0; i < niz.length - 1; i++) {
					for(int j = i; j < niz.length; j++) {
						if(niz[j].veca(niz[i])) {
							Uporediv tmp = niz[i];
							niz[i] = niz[j];
							niz[j] = tmp;
						}
					}
				}
			}
			
		};*/
		
		/*Uporediv tekst = new Uporediv() {
		 * 
		 * private String tekst;
			
			@Override
			public boolean veca(Uporediv u) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean jednaka(Uporediv u) {
				// TODO Auto-generated method stub
				return false;
			}
		};*/
		
		Uredjivac opadajuciUredjivac = x -> {
			try {
				for(int i = 0; i < x.length - 1; i++) {
					for(int j = i; j < x.length; j++) {
						if(x[j].veca(x[i])) {
							Uporediv tmp = x[i];
							x[i] = x[j];
							x[j] = tmp;
						}
					}
				}
			} catch(GreskaNeuporedivi g) {
				System.err.println(g);
			}
		};
		
		opadajuciUredjivac.uredi(niz);

		for(Uporediv u : niz) {
			System.out.println(u);
		}
		
	}

}
