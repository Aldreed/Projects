package tekici;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	
	private Button start;
	int osvojeni_poeni=0;
	Label poeni;
	TextField novcici;
	private Label novac;
	Checkbox[] editSetings;
	private CheckboxGroup myGroup;
	private Panel mreza;
	boolean rezimIgranja=true;
	private MenuBar meni;
	
	
	public Igra() {
		super("Igra");
		myGroup= new CheckboxGroup();
		editSetings= new Checkbox[2];
		editSetings[0]= new Checkbox("Trava",true,myGroup);
		editSetings[1]= new Checkbox("Zid",false,myGroup);
		
		Panel right = new Panel(new BorderLayout());
		Panel bottomRight = new Panel(new BorderLayout());
		right.setBackground(Color.green);
		bottomRight.setBackground(Color.lightGray);
		right.add(editSetings[0],BorderLayout.CENTER);
		bottomRight.add(editSetings[1],BorderLayout.CENTER);
		right.setPreferredSize(new Dimension(100,0));
		bottomRight.setPreferredSize(new Dimension(100,0));
		
		
		Panel rightSetings= new Panel(new GridLayout(0, 1));
		rightSetings.add(right);
		rightSetings.add(bottomRight);
		
		Label l1 = new Label("Podloga: ");
		
		Panel setings= new Panel(new BorderLayout());
		setings.add(l1,BorderLayout.WEST);
		setings.add(rightSetings,BorderLayout.EAST);
		setings.setPreferredSize(new Dimension(200,0));
		
		this.add(setings,BorderLayout.EAST);
		
		novac = new Label("Novcica: ",Label.LEFT);
		novcici = new TextField("12", 2);
		poeni = new Label("Poena: 0 ");
		start = new Button("Pocni");
		
		start.addActionListener((a)->{
			((Mreza)mreza).zaustavi();
			((Mreza) mreza).inicijalizuj();
			osvojeni_poeni=0;
			poeni.setText("Poena: 0 ");
			this.requestFocus();
		});
		
		Panel bottPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bottPanel.add(novac);
		bottPanel.add(novcici);
		bottPanel.add(poeni);
		bottPanel.add(start);
		
		this.add(bottPanel,BorderLayout.SOUTH);
		
		//mreza
		mreza = new Mreza(this,17);
		this.add(mreza,BorderLayout.CENTER);
		
		//Meni
		meni = new MenuBar();
		Menu m1 = new Menu("Rezim");
		m1.add("Rezim izmena");
		m1.add("Rezim igranje");
		m1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String option = arg0.getActionCommand();
				switch (option) {
				case "Rezim izmena":
					((Mreza) mreza).zaustavi();
					rezimIgranja=false;
					//stop mreza
					novcici.setEnabled(false);
					start.setEnabled(false);
					break;
				case "Rezim igranje":
					rezimIgranja=true;
					novcici.setEnabled(true);
					start.setEnabled(true);
				default:
					break;
				}
				
			}
		});
		meni.add(m1);
		this.setMenuBar(meni);
		
		//odazivi na tastaturi
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(((Mreza)mreza).t!=null&&rezimIgranja&&((Mreza)mreza).t.isAlive()) {
					switch (arg0.getKeyCode()) {
					case KeyEvent.VK_W:
						((Mreza) mreza).pomeriIgraca(-1, 0);
						break;
					case KeyEvent.VK_A:
						((Mreza) mreza).pomeriIgraca(0, -1);
						break;
					case KeyEvent.VK_S:
						((Mreza) mreza).pomeriIgraca(1, 0);
						break;
					case KeyEvent.VK_D:
						((Mreza) mreza).pomeriIgraca(0, 1);
						break;
					default:
						break;
					}
				}
			}
		});
		
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				((Mreza) mreza).zaustavi();
				dispose();
			}
		});
		
		
		this.setSize(800, 600);
		this.setVisible(true);
		
	}
	
	public static void main(String[]args) {
		Igra i= new Igra();
	}

}
