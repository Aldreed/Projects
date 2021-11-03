package baloni;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends KruznaFigura {

	public Igrac(Vektor c,double r, Scena s) {
		super(c,Color.GREEN,r,new Vektor(0, 0), s);
	}

	public void strafe(double dx) {
		Vektor temp= Vektor.saberi(centar, new Vektor(dx, 0));
		if(!(temp.getX()>mojaScena.getWidth()||temp.getX()<0)){
			centar=temp;
		}
	}

	@Override
	public void iscrtaj(Graphics g) {
		super.iscrtaj(g);
		g.setColor(Color.BLUE);
		g.fillOval((int)(centar.getX()-R/4), ((int)(centar.getY()-R/4)),(int) R/2,(int) R/2);
	}

	@Override
	public void obavesti(KruznaFigura other) {
		if (other instanceof Balon) {
			mojaScena.zaustavi();
		}
	}
	
	
}
