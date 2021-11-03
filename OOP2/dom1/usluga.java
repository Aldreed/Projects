package dom1;

public abstract class usluga {
	protected Broj brojOD;
	protected Broj brojKA;
	{
		this.brojKA=null;
		this.brojOD=null;
	}
	
	public usluga (Broj od,Broj ka) {
		this.brojKA=ka;
		this.brojOD=od;
	}
	public abstract int Cena();
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(brojOD);
		builder.append(" -> ");
		builder.append(brojKA);
		return builder.toString();
	}

	
	
	

}
