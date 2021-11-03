package tekici;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {

	public Igrac(Polje p) {
		super(p);
	}


	@Override
	public void iscrtaj() {
		Graphics g = this.getMojePolje().getGraphics();
		g.setColor(Color.red);
		g.drawLine(0, this.getMojePolje().getHeight()/2,this.getMojePolje().getWidth(), this.getMojePolje().getHeight()/2);
		g.drawLine(this.getMojePolje().getWidth()/2, 0, this.getMojePolje().getWidth()/2, this.getMojePolje().getHeight());
	}

}
