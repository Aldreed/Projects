package gui;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DrawingLines extends Frame {
	
	class Line {
		int begX, begY, endX, endY;
	}

	private ArrayList<Line> lines = new ArrayList<>();
	private Line line;
	
	@Override
	public void paint(Graphics g) {
		for(Line line : lines) {
			/*
			 * Crtanje linija pocevsi od njihovih koordinata (begX, begY) pa do (endX, endY)
			 */
			g.drawLine(line.begX, line.begY, line.endX, line.endY);
		}
		super.paint(g);
	}
	
	private class MouseEventHandler extends MouseAdapter {
		
		@Override
		public void mouseDragged(MouseEvent e) {
			/*
			 * Pamcenje kraja linije i slanje zahteva za iscrtavanje 
			 * kako bi korisnik video liniju koju trenutno iscrtava.
			 */
			line.endX = e.getX();
			line.endY = e.getY();
			repaint();
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			/*
			 * Na pritisak misa ce se kreirati novi objekat linije 
			 * zapamtiti pocetne koordinate novokreirane linije  
			 * i dodati linija u listu linija koje treba da se iscrtaju.
			 */
			line = new Line();
			lines.add(line);
			line.begX = e.getX();
			line.begY = e.getY();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			repaint();
		}
	}
	
	public DrawingLines() {
		
		setBounds(700, 200, 400, 300);
		setResizable(false);
		setTitle("Drawing Lines");
		
		addMouseListener(new MouseEventHandler());
		addMouseMotionListener(new MouseEventHandler());
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new DrawingLines();
	}
}
