package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Rupa extends Canvas implements Runnable{

	Basta mojaBasta;
	Zivotinja mojaZivotinja;
	int steps;
	int curStep;
	
	private Thread t;
	private boolean radi;
	
	public Rupa(Basta b) {
		mojaBasta=b;
		curStep=1;//zbog for u runu
		steps=b.getSteps();
		mojaZivotinja=null;
		t=null;
		this.setBackground(new Color(139,69,19));
		radi=false;
	}
	
	
	
	
	@Override
	public void paint(Graphics g) {
		if(mojaZivotinja!=null) {
			mojaZivotinja.iscrtaj();
		}
	}




	@Override
	public void run() {
			try {
				if(mojaZivotinja!=null) {
					for(;curStep<steps;curStep++) {
						t.sleep(100);
						repaint();
					}
					t.sleep(2000);
					synchronized (mojaZivotinja) {
						mojaZivotinja.pobeglaEff();
						mojaZivotinja=null;
					}
					mojaBasta.obavesti();
					t.interrupt();
				}
			}catch (InterruptedException e) {
				t.interrupt();
			}
			if(mojaZivotinja!=null) {
				synchronized (mojaZivotinja) {
					mojaZivotinja=null;
				}
			}
		radi=false;
		curStep=0;
		repaint();
		
			
	}

	public synchronized void stvoriNit() {
		t = new Thread(this);
	}
	
	public synchronized void pokreniNit() {
		radi=true;
		curStep=1;
		t.start();
	}
	
	public synchronized void zaustaviNit() {
		synchronized (mojaZivotinja) {
			mojaZivotinja=null;
		
	}
		
		if(t!=null) {
			t.interrupt();
			
			mojaBasta.obavesti();
		}
	
	}
	public synchronized boolean pokernutaNit() {
		if(t!=null)return t.isAlive();
		return false;
	}

	public void zgazi() {
			if(mojaZivotinja!=null)mojaZivotinja.udarenaEff();
	}

	
	public Zivotinja getMojaZivotinja() {
		return mojaZivotinja;
	}




	public void setMojaZivotinja(Zivotinja mojaZivotinja) {
		synchronized (mojaZivotinja) {
			this.mojaZivotinja = mojaZivotinja;
		}
		
	}




	public int getSteps() {
		return steps;
	}




	public void setSteps(int steps) {
		this.steps = steps;
	}

	
}
