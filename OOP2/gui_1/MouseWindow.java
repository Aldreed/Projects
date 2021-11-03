package gui;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class MouseWindow extends Frame {
	
	/*
	 * text - Tekst koji ce se ispisivati po prozoru
	 * x, y - trenutne koordinate misa (od gornje leve tacke koponente (0, 0))
	 */
	private String text = "";
	private int x, y;
	
	/*
	 * paint - metod koji vrsi iscrtavanje u okviru same komponente
	 * Graphics - graficki kontekst komponente 
	 * koji omogucava iscrtavanje po njoj (boja, font, oblici, ...)
	 */
	@Override
	public void paint(Graphics g) {
		g.drawString(text, x, y);
		super.paint(g);
	}
	
	private void getCoordinates(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	private void adjust(String text, MouseEvent e) {
		getCoordinates(e);
		MouseWindow.this.text = text + " (" + x + ", " + y + ")";
		/*
		 * Za teske komponente repaint predstavlja asinhroni poziv metode update, 
		 * za lake komponente asinhroni poziv paint.
		 * Podrazumevano, update metoda prebrise pozadinu komponente 
		 * pre nego sto se pozove paint kako bi se vrsilo iscrtavanje na komponenti.
		 */
		repaint();
	}
	
	private class MouseEventHandler extends MouseAdapter {
		
		/*@Override
		public void mouseClicked(MouseEvent e) {
			adjust("mouse clicked", e);
		}*/
		
		@Override
		public void mousePressed(MouseEvent e) {
			adjust("mouse pressed", e);
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			adjust("mouse released", e);
		}
	}
	
	private class MouseMotionEventHandler extends MouseMotionAdapter {
		
		@Override
		public void mouseDragged(MouseEvent e) {
			adjust("mouse dragged", e);
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			adjust("mouse moved", e);
		}
	}
	
	public MouseWindow() {
		
		setBounds(700, 200, 400, 300);
		setResizable(false);
		setTitle("Mouse window");
		
		addMouseListener(new MouseEventHandler());
		addMouseMotionListener(new MouseMotionEventHandler());
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new MouseWindow();
	}
}
