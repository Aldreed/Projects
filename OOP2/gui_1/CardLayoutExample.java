package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class CardLayoutExample extends Frame {
	
	private static final int cardsCount = 5;
	private Panel cardPanel = new Panel(), southPanel = new Panel();
	private Button previous = new Button("<Previous"), next = new Button("Next>");
	
	private void addComponents() {
		
		/*
		 * Ovaj layout manager tretira svaku komponentu kontejnera na koji se odnosi 
		 * kao jednu kartu u spilu, tako da je samo jedna karta uvek vidljiva. 
		 * Prva komponenta koja je dodata u kontejner je ona koja je inicijalno vidljiva.
		 */
		CardLayout cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);
		
		Panel card;
		float c = 0, inc = 1.f / (cardsCount - 1);
		for(int i = 0; i < cardsCount; i++) {
			card = new Panel();
			card.setBackground(new Color(c, c, c));
			cardPanel.add(card);
			c += inc;
		}
		add(BorderLayout.CENTER, cardPanel);
		
		previous.addActionListener((ae) -> { cardLayout.previous(cardPanel); });
		next.addActionListener((ae) -> { cardLayout.next(cardPanel);} );
		
		southPanel.add(previous);
		southPanel.add(next);
		add(BorderLayout.SOUTH, southPanel);
	}
	
	public CardLayoutExample() {
		super("Cards");
		
		addComponents();
		
		setBounds(700, 200, 400, 300);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new CardLayoutExample();
	}
}
