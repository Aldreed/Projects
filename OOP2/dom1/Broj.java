package dom1;

public class Broj {
	private int kodDrzave;
	private int pozivniBroj;
	private int brojTelefona;
	private Indikator mojIndikator;
	{
		kodDrzave=-1;
		pozivniBroj = -1;
		brojTelefona = -1;
		mojIndikator = Indikator.Fiksan;
	}
 public Broj(int k,int pb, int bt,Indikator ind) {
	 this.kodDrzave=k;
	 this.pozivniBroj=pb;
	 this.brojTelefona=bt;
	 this.mojIndikator=ind;
	 this.errorCheck();
 }
 private void errorCheck() {
	 if(this.brojTelefona<0||this.kodDrzave<0||this.pozivniBroj<0) {
		System.exit(1);
	 }
 }
 public boolean Provera(Broj d) {
	 if(d.kodDrzave==this.kodDrzave){
		 return true;
	 }
	 else return false;
 }
public Indikator getMojIndikator() {
	return mojIndikator;
}
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("+");
	builder.append(this.kodDrzave);
	builder.append(this.pozivniBroj);
	builder.append(" ");
	builder.append(this.brojTelefona);
	return builder.toString();
}


}

