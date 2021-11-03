package baloni;

import java.awt.Color;
import java.awt.Graphics;

public class Krug {
	protected Vektor centar;
	protected double R;
	private Color boja;
	public Krug(Vektor v,Color b,double r) {
		centar =v;
		R=r;
		boja=b;
		
	}
	
	public boolean preklapaSe(Krug other) {
		double rastojanje = Math.sqrt(Math.pow(centar.getX()-other.centar.getX(), 2) +Math.pow(centar.getY()-other.centar.getY(), 2));
		if(rastojanje>=(other.R/2+this.R/2)) {
			return false;
		}
		else {
			return true;
		}
	}
	public void iscrtaj(Graphics g) {
		g.setColor(boja);
		g.fillOval((int)(centar.getX()-R/2), ((int)(centar.getY()-R/2)),(int) R,(int) R);
	}
}
