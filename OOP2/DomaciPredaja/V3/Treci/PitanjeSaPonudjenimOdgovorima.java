package pitanja;



public class PitanjeSaPonudjenimOdgovorima extends Pitanje {

	private String[] ponudjeni;
	public PitanjeSaPonudjenimOdgovorima(String string, int i, double d, String[] strings) {
		super(string,i,d);
		ponudjeni=strings;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append('\n');
		for(int i=0; i<ponudjeni.length;i++) {
			builder.append(i+1+". ");
			builder.append(ponudjeni[i]+'\n');
		}
		return builder.toString();
		}
	@Override
	public PitanjeSaPonudjenimOdgovorima clone() throws CloneNotSupportedException {
		PitanjeSaPonudjenimOdgovorima temp = (PitanjeSaPonudjenimOdgovorima)super.clone();
		return temp;
		
		
	}
	

}
