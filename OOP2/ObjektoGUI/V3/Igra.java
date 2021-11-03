package baloni;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	private Scena mojaScena;
	
	public Igra(){
		super("Igra");
		mojaScena = new Scena(this);
		this.add(mojaScena);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				mojaScena.zaustavi();dispose();
			}
			
		});
		this.setSize(800, 600);
		this.setVisible(true);
		this.setResizable(false);
		mojaScena.pokreni();
	}
	
	public static void main(String[]args) {
		Igra i = new Igra();
	}
}
