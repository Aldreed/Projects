package karting;

public class Vozilo {
	private String name ;
	private double maxSpeed;
	private double curSpeed;
	private double handling;
	private double acc;
	public Vozilo(double d, double e, double f, String string) {
		name=string;
		maxSpeed=d;
		curSpeed=0;
		acc=e;
		if(f<=1&&f>=0) {
			handling=f;
		}
		else {
			handling =0;
		}
	}
	
	public void postIme(String s) {
		name=s;
	}
	
	public void postMaksBrzinu(double d) {
		if(curSpeed>d) {
			curSpeed=d;
		}
		maxSpeed=d;
		
	}

	public void postUbrzanje(double d) {
		acc=d;
		
	}

	public void postUpravljivost(double d) {
		if(d>=0&&d<=1) {
			handling=d;
		}
		
	}

	public double dohvMaksBrzinu() {
		return maxSpeed;
	}

	public double dohvUbrzanje() {
		return acc;
	}

	public double dohvUpravljivost() {
		return handling;
	}

	public double pomeriVozilo(double t) {
		
		double tempSpeed = curSpeed+t*acc;
		if(tempSpeed>maxSpeed) {
			double tillMax=(maxSpeed-curSpeed)/acc;
			double ostatak = t-tillMax;
			double s = curSpeed*tillMax + acc*Math.pow(tillMax, 2)/2f;
			s+=maxSpeed*ostatak;
			curSpeed=maxSpeed;
			return s;
			
		}
		else {
			double s = curSpeed*t + acc*Math.pow(t, 2)/2f;
			curSpeed=tempSpeed;
			return s;
		}
	}

	 public void postTrenBrzinu(double d) {
		if(d<=maxSpeed) {
			curSpeed=d;
		}
		
	}

	public String dohvIme() {
		return name;
	}

	public double dohvTrenBrzinu() {
		return curSpeed;
	}

	public double izracunajVreme(double sp) {
		double retTime=0;
		if(sp==0) {
			return 0;
		}
		if(sp<0) {
			return 0;
		}
		if(curSpeed<maxSpeed) {
			double s = (Math.pow(maxSpeed, 2)-Math.pow(curSpeed, 2))/(2*acc);
			if(s<sp) {
				retTime+=(maxSpeed-curSpeed)/acc;
				s=sp-s;
				retTime+=s/maxSpeed;
				return retTime;
			}
			else if(sp<s) {
				double tempSpeed = Math.sqrt(Math.pow(curSpeed, 2)+2*acc*(sp));
				retTime=(tempSpeed-curSpeed)/acc;
				return retTime;
				
			}
			else {
				retTime+=(maxSpeed-curSpeed)/acc;
				return retTime;
			}
		}
		else {
			retTime=sp/maxSpeed;
			return retTime;			
		}
	}
	@Override
	public String toString() {
		return name + " ["+maxSpeed+", "+acc+", "+handling+"]";
	}
}
