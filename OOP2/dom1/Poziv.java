package dom1;

public class Poziv extends usluga {

	private int trajanje =-1;
	public Poziv(Broj od, Broj ka, int tr) {
		super(od, ka);
		this.trajanje=tr;
		if(this.trajanje<0) {
			System.exit(2);
		}
	}
	public int Cena() {
		if(this.trajanje==0) {
			return 0;
		}
		
		if(brojOD.Provera(brojKA)) {
			return 10*trajanje;
		}
		else return 30+ 50*trajanje;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("(");
		builder.append(this.trajanje/60);
		builder.append(":");
		builder.append(this.trajanje%60);
		builder.append(")");
		return builder.toString();
	}
	public static void main(String arg[]) {
		Broj prvi = new Broj(123,23,2235123,Indikator.Fiksan);
		Broj drugi = new Broj(78,910,88590403,Indikator.Mobilni);
		Broj treci = new Broj(78,279,100100023,Indikator.Fiksan);
		Broj cetvrti = new Broj(64500,2345,6787,Indikator.Fiksan);
		
		Poziv Biznis = new Poziv(prvi,cetvrti,3600);
		Poziv Pizza = new Poziv(drugi,treci,12315);
		Poziv GhostBusters = new Poziv(drugi,cetvrti,1231521);
		Poziv Maybe = new Poziv(prvi,treci,2512214);
		
		System.out.println(Biznis.Cena());
		System.out.println(Pizza.Cena());
		System.out.println(GhostBusters.Cena());
		System.out.println(Maybe.Cena());
		
		System.out.println(Biznis);
		System.out.println(Pizza);
		System.out.println(GhostBusters);
		System.out.println(Maybe);
	} 
}
