package baloni;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Scena extends Canvas implements Runnable {
	private Igra mojaIgra;
	private Thread t;
	private Igrac mojIgrac;
	private double spdIgrac=10;
	private List<KruznaFigura> l;
	private List<KruznaFigura>garbage;
	public Scena(Igra i) {
		mojaIgra=i;
		t=null;
		l = new ArrayList<KruznaFigura>();
		garbage = new LinkedList<KruznaFigura>();
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					if(mojIgrac!=null) {
						mojIgrac.strafe(spdIgrac);;
					}
					break;
				case KeyEvent.VK_LEFT:
					if(mojIgrac!=null) {
						mojIgrac.strafe(-spdIgrac);;
					}
					break;
				default:
					break;
				}
			}
			
		});
	}
	
	
	
	@Override
	public void paint(Graphics arg0) {
		for (KruznaFigura kruznaFigura : l) {
			kruznaFigura.iscrtaj(arg0);
		}
	}



	@Override
	public void run() {
		Random r = new Random(System.nanoTime());
		while(!t.interrupted()) {
			synchronized (this) {
				//pomeri figure
				for (KruznaFigura kruznaFigura : l) {
					kruznaFigura.pomeriSe(0.06);
				}
				//izbaci smece
				for (KruznaFigura kruznaFigura : garbage) {
					l.remove(kruznaFigura);
				}
				garbage.clear();
				//obavesti o sudarima
				for (KruznaFigura I : l) {
					for (KruznaFigura J : l) {
						if(I!=J) {
							if(I.preklapaSe(J)) {
								I.obavesti(J);
							}
						}
					}
				}
				//dodaj balon
				if(r.nextDouble()<=0.1) {
					double x = r.nextDouble()*this.getWidth();
					Balon b = new Balon(new Vektor(x, 30), Color.red, 20.0, new Vektor(0, 50), this);
					l.add(b);
				}
				repaint();
			}
			
			try {
				t.sleep(60);
			} catch (InterruptedException e) {
				t.interrupt();
			}
		}
		
	}
	public synchronized void pokreni() {
		t= new Thread(this);
		KruznaFigura temp = new Igrac(new Vektor(this.getWidth()/2, this.getHeight()-30), 30, this);
		l.add(temp);
		mojIgrac=(Igrac) temp;
		t.start();
		this.requestFocus();
	}
	public synchronized void zaustavi() {
		t.interrupt();
	}
	
	public synchronized void dodaj(KruznaFigura k) {
		l.add(k);
	}
	public synchronized void izbaci(KruznaFigura k) {
		garbage.add(k);
	}
	private synchronized void pomeriIgraca(double dx) {
		mojIgrac.strafe(dx);
	}
}
