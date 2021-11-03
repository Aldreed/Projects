package igra;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Basta extends Panel implements Runnable{
	
	private int rows;
	private int col;
	private Rupa[][] mat;

	private int povrce;
	private int steps;
	private double interval;
	
	private Thread t;
	private boolean radi;
	
	private Igra mojaIgra;
	
	
	public Basta(int r, int c) {
		super(new GridLayout(r,c,20,20));
		rows=r;
		col=c;
		mat = new Rupa[r][c];
		
		
		for (int i = 0;i<r;i++){
			for(int j = 0;j<c;j++){
				mat[i][j]=new Rupa(this);
				mat[i][j].addMouseListener(new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent e) {
					((Rupa) e.getSource()).zgazi();
					}
					
				});
				this.add(mat[i][j]);
		 
			}
		  }
		  
		povrce=100;
		this.setBackground(new Color(34,128,34));
		interval = 1000;
		steps = 10;
		t=null;
	}


	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while(!t.interrupted()||radi) {
			try {
				int i=(int) (Math.random()*rows);
				int j =(int)(Math.random()*col);
				
				while(mat[i][j].mojaZivotinja!=null) {
					i=(int) (Math.random()*rows);
					j =(int)(Math.random()*col);
				}
				
				synchronized (mat[i][j]) {
					t.sleep((long) interval);
					mat[i][j].mojaZivotinja=new Krtica(mat[i][j]);
					mat[i][j].stvoriNit();
					mat[i][j].pokreniNit();
					interval=interval*0.99;
				}
			} catch (InterruptedException e) {
				if(!radi)t.interrupt();
				else {
					//obavestebna
				}
			}
		
		}
		for (Rupa[] rupas : mat) {
			for (Rupa rupa : rupas) {
				if(rupa.pokernutaNit())rupa.zaustaviNit();
				rupa.mojaZivotinja=null;
			}
		}
		povrce=100;
	}
	
	public synchronized void pokreni() {
		t=new Thread(this);
		mojaIgra = Igra.me;
		radi=true;
		povrce =100;
		mojaIgra.povrce.setText("Povrce: " +povrce);
		t.start();
	}
	public synchronized void zaustavi() {
		this.radi=false;
		if(t!=null)t.interrupt();
	}
	public synchronized void obavesti() {
		t.interrupt();
	}
	
	
	public int getSteps() {
		return steps;
	}


	public void setSteps(int steps) {
			this.steps = steps;
			for (int i = 0;i<rows;i++){
				for(int j = 0;j<col;j++){
					mat[i][j].setSteps(steps);
				}
		 }
	 
		
	}
	
	public void setInterval(double inter) {
		interval= inter;
	}
	
	public synchronized void smanjiPovrce() {
		povrce--;
		this.mojaIgra.povrce.setText("Povrce: " + povrce);
		if(povrce==0)this.zaustavi();
	}

}
