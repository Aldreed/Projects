package raspored;
public class Vreme {
	int sati;//todo preradi
	int minuti;
	
	public Vreme(int i,int  j) throws GVreme{
		if(i<0||j<0||i>23||j>59||Math.floorMod(j, 15)!=0) throw new GVreme();
		else {
			sati =i;
			minuti= j;
		}
	}
	public Vreme() throws GVreme {
		this(0,0);
	}
	public Vreme(int i) throws GVreme {
		this(i,0);
	}

	public boolean jednako(Vreme v) {
		if (sati==v.sati&&minuti==v.minuti) {
			return true;
		}
		else {
			return false;
		}
	}
	public static Vreme saberi(Vreme v1,Vreme v2) throws GVreme {
		int i = v1.vremeUMinutima()+v2.vremeUMinutima();
		return new Vreme(Math.floorDiv(i, 60)%24,Math.floorMod(i, 60));
	}
	
	@Override
	public String toString() {
		
		return "(" + sati +":"+ minuti+")" ;
	}
	
	public int vremeUMinutima() {
		return sati*60 + minuti;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vreme other = (Vreme) obj;
		if (this.vremeUMinutima() != other.vremeUMinutima())
			return false;
		return true;
	}
	
	
}
