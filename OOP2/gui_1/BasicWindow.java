package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * Programiranje grafickih korisnickih interfejsa u Java-i moguce je uz podrsku dva paketa:
 * - java.awt (abstract windowing toolkit) - teske komponente (platformski zavisne), 
 * manji izbor komponenti (da bi awt bio prilagodljiv razlicitim platformama)
 * - javax.swing - lake (platformski nezavisne) komponente (osim par teskih top-level komponenti) 
 * napisane direktno u Java-i, iscrtavaju se preko teskih komponenti; 
 * noviji paket, veci izbor komponenti, namenjen da zameni awt (komponente pocinju slovom J)
 * - javaFX - namenjen da zameni swing
 * 
 * Lake komponente ne zahtevaju podrsku objekata OS-a za njihovu realizaciju, 
 * vec se oslanjaju na resurse svog pretka-komponente, koji je teska komponenta (najcesce prozor). 
 * To su najcesce labele, dugmad itd.
 * Teske komponente zahtevaju podrsku OS-a za njihovu realizaciju (npr. prozor).
 */

/*
 * Component - korena klasa u hijerarhiji klasa koje se odnose na objekte 
 * koji imaju graficku reprezentaciju i sa kojima korisnik moze da interaguje.
 * Container - predstavlja komponente koje mogu da sadrze druge komponente; izvedena iz Component
 * Window - top-level kontejner (ne sadrzi ga drugi kontejner) bez ivica, trake menija, naslova
 * Frame - prozor sa naslovom, ivicama, trakom menija
 * Panel - kontejner opste namene, grupise druge komponente
 */

@SuppressWarnings("serial")
public class BasicWindow extends Frame {
	
	private void populateWindow() {
		
		/*
		 * Label - komponenta koja predstavlja jednolinijski tekst 
		 * koji moze da se menja programskim putem
		 */
		Label west = new Label("west"), 
				south = new Label("south"), 
				east = new Label("east"), 
				north = new Label("north"), 
				center = new Label("center");
		/*
		 * Postavljanje boje povrsine komponente
		 */
		west.setBackground(Color.MAGENTA);
		/*
		 * Dodavanje komponente west u prozor.
		 * Svaka komponenta koja je kontejner sadrzi layout manager-a, 
		 * koji predstavlja nacin na koji se rasporedjuju komponente u okviru tog kontejnera.
		 * Window ima podrazumevani BorderLayout - 5 regiona (4 strane sveta i centar).
		 * Strane sveta nisu obavezne i region centar se siri na njihov racun.
		 * Strana sveta se aktivira dodavanjem komponente na odgovarajucu stranu.
		 */
		add(west, BorderLayout.WEST);
		south.setBackground(Color.GREEN);
		/*
		 * Horizontalno poravnanje teksta u okviru labele moze da se postavi (levo, centar, desno).
		 */
		south.setAlignment(Label.RIGHT);
		add(south, BorderLayout.SOUTH);
		east.setBackground(Color.YELLOW);
		add(east, BorderLayout.EAST);
		north.setBackground(Color.RED);
		north.setAlignment(Label.LEFT);
		add(north, BorderLayout.NORTH);
		center.setBackground(Color.LIGHT_GRAY);
		center.setAlignment(Label.CENTER);
		add(center, BorderLayout.CENTER);
	}
	
	public BasicWindow() {
		
		/*
		 * Postavljanje pocetne lokacije prozora u odnosu na virtuelni koordinatni pocetak, 
		 * sto predstavlja gornju-levu tacku ekrana. x raste na desno, y raste na dole.
		 * Pomeri prozor 700 na desno, 200 na dole, prozor je sirine 400, visine 300 piksela.
		 */
		setBounds(700, 200, 400, 300);
		/*
		 * Postavljanje indikatora koji odredjuje da li prozoru moze da se promeni velicina. 
		 * Podrazumevana vrednost je da moze. 
		 */
		setResizable(false);
		
		setTitle("Basic window");
		
		populateWindow();
		
		/*
		 * Interakcijom korisnika sa awt komponentama kreiraju se dogadjaji. 
		 * Prozori (Frame) mogu da budu izvori razlicitih tipova dogadjaja, 
		 * a jedan od njih je WindowClosing tip WindowEvent dogadjaja. 
		 * Da bi se sprovele odredjene akcije ukoliko se desi neki dogadjaj 
		 * neophodno je napraviti osluskivac tog tipa dogadjaja 
		 * i registrovati ga kod komponente koja moze da generise razlicite dogadjaje.
		 * 
		 * Da bi mogli da se obradjuju WindowEvent dogadjaji 
		 * neophodno je dodati osluskivac tipa WindowListener.
		 * Medjutim, ukoliko bismo pravili objekat anonimne klase koja implementira WindowListener 
		 * morali bismo da implementiramo sve metode tog interfejsa (barem praznog tela) 
		 * iako nas mozda neki tipovi dogadjaja prozora ne zanimaju. 
		 * Resenje za to su klase adapteri - klase koje implementiraju odgovarajuce interfejse 
		 * tako da su sve metode interfejsa implementirane praznim telom. 
		 * Onda je samo neophodno redefinisati metode klase adaptera koje su nam od interesa.
		 */		
		addWindowListener(new WindowAdapter() {
			
			/*
			 * Poziva se kada je prozor u procesu zatvaranja.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				/*
				 * Oslobadjanje svih resursa prozora (vracanje OS-u), cime prozor vise nije vidljiv.
				 */
				dispose();
			}
		});
		/*
		 * Prikazije prozor (true), odnosno sakriva ga (false).
		 */
		setVisible(true);
	}
	

	public static void main(String[] args) {
		
		new BasicWindow();
	}
}
