package gui.drawing.shapes;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/*
 * Canvas - predstavlja praznu pravougaonu povrs na ekranu po kojoj se moze crtati.
 * Za crtanje je neophodno redefinisati metodu paint. 
 */
@SuppressWarnings("serial")
public class ActiveCanvas extends Canvas implements Runnable {
	
	private Shape shape;
	private Color lineColor = Color.BLACK;
	private long sleepTime = 25;
	private int steps = 100;
	private Thread thread;
	
	public ActiveCanvas() {
		shape = new Heart();
	}
	
	/*
	 * Crtanje se obavlja u posebnoj niti. 
	 * Svaki put kada se pozove metod paint 
	 * prekine se prethodno iscrtavanje, 
	 * a zatim se kreira nova nit, 
	 * ciji je zadatak da iscrta izabrani oblik.
	 * 
	 * Zasto bismo pravili novu nit i iscrtavanje obavili u njenoj run metodi 
	 * umesto da iscrtavanje direktno vrsimo u paint metodi?
	 * 
	 * Kood metode paint izvrsava AWT nit (Event dispatch tread). 
	 * Njen zadatak je i da obradjuje interakciju korisnika sa GUI-jem. 
	 * U sustini, posao ove niti je da u petlji proverava red dogadjaja (event queue), 
	 * dohvata i obradjuje jedan po jedan dogadjaj (MouseEvent, ActionEvent, repaint zahtev itd.) 
	 * pozivom odgovarajuce metode odgovarajuceg osluskivaca dogadjaja.
	 * 
	 * Posto zelimo da iscrtavanje traje neko vreme 
	 * (da bismo golim okom videli sam proces crtanja) 
	 * crtanje se obavlja na sledeci nacin u steps koraka:
	 * - u svakom koraku se iscrta jedna linija, deo celog oblika
	 * - zatim se nit uspava na sleepTime period
	 * 
	 * Ukoliko bi ovaj posao obavljala AWT nit 
	 * onda bi GUI bio zamrznut za vreme crtanja, 
	 * tj. korisnik ne bi mogao da interaguje sa GUI-jem
	 * (da bira menije, klikce dugmice, unosi tekst u tekstualna polja itd.).
	 * 
	 * CanvasFail.java demonstrira crtanje izvrseno od strane AWT niti.
	 */
	@Override
	public void paint(Graphics g) {
		
		finish();
		
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		Shape shape = this.shape.clone();
		Graphics g = getGraphics();
		g.setColor(lineColor);
		g.translate(this.getWidth() / 2, this.getHeight() / 2);
		double inc = 2 * Math.PI / steps;
		int x = shape.getX(0), y = shape.getY(0), endX, endY;
		
		try {
			for(double angle = 0; angle < 2 * Math.PI; angle += inc) {
				Thread.sleep(sleepTime);
				if(Thread.interrupted())
					break;
				endX = shape.getX(angle);
				endY = shape.getY(angle);
				g.drawLine(x, y, endX, endY);
				x = endX;
				y = endY;
			}
		} catch (InterruptedException e) {}
		
		synchronized (this) {
			thread = null;
			notify();
		}
	}

	public synchronized void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public synchronized void setFactor(int factor) {
		if(shape != null)
			shape.setFactor(factor);
	}
	
	public synchronized void setColor(Color color) {
		lineColor = color;
	}
	
	public synchronized void setBgColor(Color color) {
		setBackground(color);
	}
	
	public synchronized void finish() {
		if(thread != null)
			thread.interrupt();
		while(thread != null)
			try {
				wait();
			} catch (InterruptedException e) {}
	}
}
