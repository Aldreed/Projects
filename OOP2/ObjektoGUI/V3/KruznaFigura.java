package baloni;

import java.awt.Color;
import java.awt.Graphics;

public class KruznaFigura extends Krug{
	private boolean sudar=false;
	private Vektor spd;
	protected Scena mojaScena;
	public KruznaFigura(Vektor v,Color c,double r,Vektor brz,Scena s) {
		super(v,c,r);
		spd=brz;
		mojaScena=s;
	}
	
	public void pomeriSe(double t) {
		this.centar = Vektor.saberi(centar, Vektor.pomnozi(spd, t));
		if(this.centar.getX()>mojaScena.getWidth()||this.centar.getY()>mojaScena.getHeight()||this.centar.getX()<0||this.centar.getY()<0) {
			mojaScena.izbaci(this);
		}
	}
	public void obavesti(KruznaFigura other) {
		sudar=true;
	}

	@Override
	public void iscrtaj(Graphics g) {
		
		super.iscrtaj(g);
	}
	
}
