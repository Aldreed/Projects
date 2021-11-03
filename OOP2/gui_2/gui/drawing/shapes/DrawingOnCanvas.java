package gui.drawing.shapes;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class DrawingOnCanvas extends Frame {
	
	private ActiveCanvas scene = new ActiveCanvas();
	//private CanvasFail scene = new CanvasFail();
	private Button drawButton = new Button("Draw!");
	
	private void populateWindow() {
		
		/*
		 * Menu - predstavlja padajuce menije koji mogu sadrzati druge menije. 
		 * Meniji se dodaju u traku menija. Menu klasa je izvedena iz klase MenuItem.
		 * MenuItem - predstavlja pojedinacne stavke menija.
		 */
		Menu bgColorMenu = new Menu("Bg color");		
		MenuItem bgWhite = new MenuItem("white");
		MenuItem bgGray = new MenuItem("gray");
		/*
		 * Dodavanje pojedinacnih stavki menija u padajuci meni.
		 */
		bgColorMenu.add(bgWhite);
		bgColorMenu.add(bgGray);
		/*
		 * Moguce je osluskivati i obradjivati dogadjaje menija.
		 */
		bgWhite.addActionListener((ae) -> { 
			scene.setBgColor(Color.WHITE);
		});
		bgGray.addActionListener((ae) -> { 
			scene.setBgColor(Color.GRAY);
		});
		
		Menu factorMenu = new Menu("Size");		
		MenuItem small = new MenuItem("3");
		MenuItem big = new MenuItem("5");
		factorMenu.add(small);
		factorMenu.add(big);
		small.addActionListener((ae) -> { 
			scene.setFactor(Integer.parseInt(((MenuItem)ae.getSource()).getLabel()));
		});
		big.addActionListener((ae) -> { 
			scene.setFactor(Integer.parseInt(((MenuItem)ae.getSource()).getLabel()));
		});
		
		/*
		 * MenuShortcut - predstavlja precicu menija.
		 */
		MenuItem quitMenu = new MenuItem("Quit", new MenuShortcut(KeyEvent.VK_E));
		quitMenu.addActionListener((ae) -> {
			dispose();
		});
		
		Menu file = new Menu("File");
		file.add(bgColorMenu);
		file.add(factorMenu);
		/*
		 * Moguce je postaviti separator u padajucem meniju.
		 */
		file.addSeparator();
		file.add(quitMenu);
		
		/*
		 * MenuBar - traka menija, koja se pridruzuje prozoru i kojoj se meniji mogu dodavati.
		 */
		MenuBar menuBar = new MenuBar();
		menuBar.add(file);
		setMenuBar(menuBar);
		
		add(scene, BorderLayout.CENTER);
		
		drawButton.addActionListener((ae) -> {
			scene.repaint();
		});
		Panel buttonPanel = new Panel();
		
		/*
		 * Choice - predstavlja padajucu listu iz koje se moze izabrati jedna opcija.
		 */
		Choice chooseShape = new Choice();
		chooseShape.add("Heart");
		chooseShape.add("Circle");
		
		/*
		 * Osluskivac dogadjaja padajuce liste, koja sadrzi opcije.
		 */
		chooseShape.addItemListener((ie) -> {
			String name = chooseShape.getSelectedItem();
			if("Heart".equals(name))
				scene.setShape(new Heart());
			else if("Circle".equals(name)) {
				scene.setShape(new Circle(15));
			}
		});
		
		/*
		 * List - lista opcija iz koje je moguce odabrati jednu ili vise opcija.
		 * Ne mesati sa java.util.List!
		 */
		List chooseColor = new List(2);
		chooseColor.add("Black");
		chooseColor.add("Red");
		chooseColor.add("Green");
		/*
		 * Izbor opcije programskim putem. Podrazumevano je obelezena prva opcija.
		 */
		chooseColor.select(0);
		
		/*
		 * Osluskivac dogadjaja liste. 
		 * Dogadjaj se generise interakcijom korisnika sa komponentom (izborom opcije).
		 */
		chooseColor.addItemListener((ie) -> {
			String item = chooseColor.getSelectedItem();
			if("Black".equals(item))
				scene.setColor(Color.BLACK);
			else if("Red".equals(item))
				scene.setColor(Color.RED);
			else if("Green".equals(item))
				scene.setColor(Color.GREEN);
		});
		
		buttonPanel.add(new Label("Shape: "));
		buttonPanel.add(chooseShape);
		buttonPanel.add(new Label("Color: "));
		buttonPanel.add(chooseColor);
		buttonPanel.add(drawButton);
		
		add(buttonPanel, BorderLayout.SOUTH);		
	}
	
	public DrawingOnCanvas() {
		
		setBounds(700, 200, 400, 300);
		setResizable(false);
		
		setTitle("Drawing");
		
		populateWindow();
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new DrawingOnCanvas();
	}
}
