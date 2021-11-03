package tekici;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {

	public Novcic(Polje p) {
		super(p);
	}

	@Override
	public void iscrtaj() {
		Graphics g = this.getMojePolje().getGraphics();
		g.setColor(Color.yellow);
		g.fillOval(this.getMojePolje().getWidth()/4,this.getMojePolje().getHeight()/4 , this.getMojePolje().getWidth()/2, this.getMojePolje().getHeight()/2);
	}

}
