package igra;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	static Igra me = null;
	private Checkbox[] myCheckBoxes;
	Label povrce;
	private Button dugme;
	private Panel bastaPanel;
	
	private Igra(Igra t) {
		super("Igra");
		if(t==null) {
			
			Label tezina = new Label("Tezina",Label.CENTER);
			tezina.setFont(Font.decode("Arial-BOLD"));
			tezina.setPreferredSize(new Dimension(150,0));
			
			myCheckBoxes = new Checkbox[3];
			CheckboxGroup group = new CheckboxGroup();
			Checkbox lako = new Checkbox("Lako", true, group);
			Checkbox srednje = new Checkbox("Srednje",false, group);
			Checkbox tesko = new Checkbox("Tesko",false,group);
			myCheckBoxes[0]=lako;
			myCheckBoxes[1]=srednje;
			myCheckBoxes[2]=tesko;
			
			
			dugme = new Button("Kreni");
			dugme.addActionListener(e->{
				if(dugme.getLabel()=="Kreni") {
					for (int i = 0; i < myCheckBoxes.length; i++) {
						if(myCheckBoxes[i].getState()==true) {
							switch (i) {
							case 0: 
								((Basta) this.bastaPanel).setInterval(1000);
								((Basta) this.bastaPanel).setSteps(10);
								break;
							case 1: 
								((Basta) this.bastaPanel).setInterval(750);
								((Basta) this.bastaPanel).setSteps(8);
								break;
							case 2: 
								((Basta) this.bastaPanel).setInterval(500);
								((Basta) this.bastaPanel).setSteps(6);
								break;
							}
						}
					}
					for (Checkbox checkbox : myCheckBoxes) {
						checkbox.setEnabled(false);
					}
					dugme.setLabel("Stani");
					((Basta) this.bastaPanel).pokreni();
				}
				else {
					((Basta) this.bastaPanel).zaustavi();
					
					for (Checkbox checkbox : myCheckBoxes) {
						checkbox.setEnabled(true);
					}
					dugme.setLabel("Kreni");
				}
			});
			
			povrce = new Label("Povrce: 100",Label.CENTER);
			povrce.setFont(Font.decode("Arial-BOLD"));
			Panel innerPanel = new Panel(new BorderLayout());
			innerPanel.add(povrce,BorderLayout.CENTER);
			
			Panel outerPanel = new Panel(new GridLayout(0, 1, 0, 1));
			
			outerPanel.add(tezina);
			outerPanel.add(lako);
			outerPanel.add(srednje);
			outerPanel.add(tesko);
			outerPanel.add(dugme);
			
			Panel content=new Panel(new GridLayout(0, 1, 0, 5));
			content.add(outerPanel);
			content.add(innerPanel);
			add(content,BorderLayout.EAST);
			bastaPanel = new Basta(4,4);
			
			
			add(bastaPanel,BorderLayout.CENTER);
			
			setSize(800, 600);
			setVisible(true);
			addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent arg0) {
					((Basta) bastaPanel).zaustavi();
					dispose();
				}
				
			});
			
			
			me=this;
			
			
		}
	}
	
	public Igra(){
		this(me);
	}
	
	public Igra getIgra() {
		return me;
	}

	public static void main(String[] args) {
		Igra i = new Igra();
	}
}
