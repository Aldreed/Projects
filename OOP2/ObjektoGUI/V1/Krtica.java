package igra;

import java.awt.Color;

public class Krtica extends Zivotinja {
	
	public Krtica(Rupa r) {
		super(r);
	}

	@Override
	public void udarenaEff() {
		this.mojaRupa.zaustaviNit();

	}

	@Override
	public void pobeglaEff() {
		this.mojaRupa.mojaBasta.smanjiPovrce();

	}

	@Override
	public void iscrtaj() {
		double factor= mojaRupa.curStep/(mojaRupa.steps*1f);
		mojaRupa.getGraphics().setColor(Color.DARK_GRAY);
		int RX = (int) (mojaRupa.getWidth()*factor);
		int RY = (int) (mojaRupa.getHeight()*factor);
		int offX=(int) (mojaRupa.getWidth()/2-(mojaRupa.getWidth()/2)*factor);
		int offY = (int) (mojaRupa.getHeight()/2-(mojaRupa.getHeight()/2)*factor);
		mojaRupa.getGraphics().fillOval(offX, offY, RX, RY);
	}

}
