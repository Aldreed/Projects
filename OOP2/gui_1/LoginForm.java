package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class LoginForm extends Frame {
	
	private class QuitDialog extends Dialog {
		/*
		 * Button - dugme sa natpisom koje se moze pritisnuti.
		 */
		private Button ok = new Button("Ok"), cancel = new Button("Cancel");
		
		@Override
		public void paint(Graphics g) {
			g.drawString("Are you sure you want to quit?", 20, 70);
			super.paint(g);
		}
		
		/*
		 * owner je vlasnik ovog dijaloga, u ovom slucaju glavni prozor
		 */
		public QuitDialog(Frame owner) {
			super(owner);
			setBounds(owner.getX() + owner.getWidth()/2, owner.getY() + owner.getHeight()/2, 200, 150);
			setTitle("Quit");
			/*
			 * Prikazivanjem ovog dijaloga blokira se interakcija sa svim ostalim prozorima
			 */
			setModalityType(ModalityType.APPLICATION_MODAL);
			setResizable(false);
			/*
			 * Panel je najjednostavnija kontejnerska komponenta. 
			 * Layout ovog kontejnera je Flow - komponente se podrazumevano rasporedjuju 
			 * centrirano, sa leva na desno redom u potrebnom broju linija.
			 */
			Panel buttons = new Panel();
			/*
			 * Potrebni su osluskivaci dogadjaja tipa ActionEvent, koji proizvodi komponenta Button. 
			 * U ovom slucaju su to lambda izrazi koji predstavljaju implementaciju 
			 * funkcionalnog interfejsa ActionLister.
			 */
			ok.addActionListener((ae) -> { LoginForm.this.dispose(); });
			cancel.addActionListener((ae) -> { QuitDialog.this.dispose(); });
			buttons.add(ok);
			buttons.add(cancel);
			add(buttons, BorderLayout.SOUTH);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});
			setVisible(true);
		}
	}
	
	/*
	 * TextField - tekst komponenta koja predstavlja jednolinijiski tekst 
	 * koji moze da se menja programskim putem ili interakcijom korisnika.
	 * Argument konstruktora u ovom slucaju je broj kolona za predstavljanje teksta, 
	 * sto je obicno sirina brKolona * prosecnaSirinaKaraktera.
	 */
	private TextField username = new TextField(10), password = new TextField(10);
	private Button submit = new Button("Submit");
	private Label passStrength = new Label("");
	
	private void populateWindow() {
		/*
		 * Panel kontejner kao podrazumevani layout (nacin rasporedjivanja komponenti) ima FlowLayout. 
		 * Ovde ce panel content imati GridLayout, sto znaci da ce komponente u kotejneru content 
		 * biti rasporedjene u resetku sa 1 kolonom.
		 * Posto je broj redova 0 to znaci u potrebnom broju redova.
		 * Ostali argumenti su hgap i vgap - horizontalna i vertikalna praznina izmedju kolona/redova.
		 * Komponente se dodaju redom po vrstama (prvo se popunjava prva vrsta, pa druga itd.).
		 */
		Panel content = new Panel(new GridLayout(0, 1, 0, 5));
		Panel userPanel = new Panel();
		/*
		 * Postavljanje boje pozadine panela.
		 */
		userPanel.setBackground(Color.RED);
		userPanel.add(new Label("Username: "));
		userPanel.add(username);
		content.add(userPanel);
		
		Panel passPanel = new Panel();
		/*
		 * TextListener predstavlja osluskivace TextEvent dogadjaja (promena teksta). 
		 * U ovom slucaju se menja tekst labele passStrength 
		 * u zavisnosti od duzine teksta u tekstualnom polju password.
		 */
		password.addTextListener((te) -> {
			int passLen = password.getText().length();
			if(passLen == 0) {
				passStrength.setText("");
			}
			else if(passLen < 5) {
				passStrength.setText("Weak password");
				passStrength.setForeground(Color.RED);
			}
			else if(passLen < 10) {
				passStrength.setText("Good password");
				passStrength.setForeground(Color.YELLOW);
			}
			else {
				passStrength.setText("Excelent password");
				passStrength.setForeground(Color.GREEN);
			}
			
			/*
			 * Sa obzirom da passStrengthPanel (videti nize) ima FlowLayout, 
			 * labela passStrength u okviru nje prostire se na potrebnoj velicini.
			 * Velicina labele zavisi od velicine teksta labele. 
			 * Sa obzirom da se velicina teksta labele menja, ona se moze povecati 
			 * i prevazici velicinu same labele. U tom slucaju komponenta labela je invalidirana 
			 * i mora se validirati kako bi mogla da prikaze duzi tekst.
			 */
			passStrength.revalidate();
		});
		/*
		 * Postavljanje karaktera koji ce se prikazivati kada se unosi tekst.
		 */
		password.setEchoChar('*');
		passPanel.add(new Label("Password: "));
		passPanel.setBackground(Color.YELLOW);
		passPanel.add(password);
		content.add(passPanel);
		
		Panel genderPanel = new Panel();
		genderPanel.add(new Label("Gender: "));
		genderPanel.setBackground(Color.GREEN);
		/*
		 * Ukoliko je neophodno koristiti radio dugmad 
		 * (mogucnost izbora najvise jedne opcije od vise ponudjenih)
		 * onda je neophodno grupisati checkbox-ove. 
		 */
		CheckboxGroup genderGroup = new CheckboxGroup();
		/*
		 * Inicijalno je radio dugme maleCb oznaceno (true argument konstruktora).
		 */
		Checkbox maleCb = new Checkbox("Male", true, genderGroup);
		Checkbox femaleCb = new Checkbox("Female", false, genderGroup);
		genderPanel.add(maleCb);
		genderPanel.add(femaleCb);
		content.add(genderPanel);
		
		Panel termsPanel = new Panel();
		/*
		 * Checkbox - dugme sa dva stanja (on, off)
		 */
		Checkbox termsCb = new Checkbox("I agree with terms and conditions.");
		/*
		 * ItemListener predstavlja osluskivace ItemEvent dogadjaja.
		 * U zavisnosti od stanja ovog checkbox-a dozvoljava se odnosno zabranjuje 
		 * interakcija sa dugmetom submit. 
		 */
		termsCb.addItemListener((ie) -> {
			if(ie.getStateChange() == ItemEvent.SELECTED)
				submit.setEnabled(true);
			else submit.setEnabled(false);
		});
		termsPanel.add(termsCb);
		content.add(termsPanel);
		
		submit.setEnabled(false);
		submit.addActionListener((ae) -> {
			System.out.println("========================");
			System.out.println("Username: " + username.getText());
			System.out.println("Password: " + password.getText());
			System.out.println("Gender: " + genderGroup.getSelectedCheckbox().getLabel());
		});
		Panel submitPanel = new Panel();
		submitPanel.add(submit);
		content.add(submitPanel);
		
		/*
		 * Postavljanje fonta za labelu passStrength - Arial, Bold, velicine 20
		 */
		passStrength.setFont(new Font("Arial", Font.BOLD, 20));
		Panel passStrengthPanel = new Panel();
		passStrengthPanel.setBackground(Color.LIGHT_GRAY);
		passStrengthPanel.add(passStrength);
		content.add(passStrengthPanel);
		
		add(content, BorderLayout.CENTER);
	}
	
	public LoginForm() {
		
		setLocation(700, 200);
		setResizable(true);
		setTitle("Login form");
		
		populateWindow();
		
		/*
		 * Ovde nije eksplicitno postavljena velicina prozora vec se ona odredjuje 
		 * na osnovu preferiranih velicina komponenti koje ovaj prozor sadrzi, 
		 * kao i na osnovu Layout menadzera. To je omoguceno pozivom metode pack.
		 */
		pack();
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				new QuitDialog(LoginForm.this);
			}
		});
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new LoginForm();
	}
}
