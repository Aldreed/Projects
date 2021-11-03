package tekici;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Tenk extends Figura implements Runnable {
	private Thread t;
	public Tenk(Polje p) {
		super(p);
	}
	
	@Override
	public void iscrtaj() {
		Graphics g = this.getMojePolje().getGraphics();
		g.setColor(Color.black);
		g.drawLine(0, 0, this.getMojePolje().getWidth(), this.getMojePolje().getHeight());
		g.drawLine(0, this.getMojePolje().getHeight(), this.getMojePolje().getWidth(), 0);
	}

	@Override
	public void run() {
		Random r = new Random(System.nanoTime());
		Mreza m = this.getMojePolje().getMojaMreza();
		while(!t.interrupted()) {
			try {
				t.sleep(500);
			} catch (InterruptedException e) {
				t.interrupt();
			}
			synchronized (this) {
				int dir = r.nextInt(4);
				
				switch (dir) {
				case 0://levo
					this.pomeri(this.getMojePolje().pomeraj(0, -1));
					break;
				case 1://desno
					this.pomeri(this.getMojePolje().pomeraj(0, 1));
					break;
				case 2://gore
					this.pomeri(this.getMojePolje().pomeraj(-1, 0));
					break;
				case 3://dole
					this.pomeri(this.getMojePolje().pomeraj(1, 0));
					break;				
				default:
					break;
				}
			}
		
		}
		
	}

	public synchronized void pokreni() {
		t= new Thread(this);
		t.start();
	}
	public synchronized void zaustavi() {
		if(t!=null)t.interrupt();
	}
}
