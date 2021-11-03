package gui.drawing.coins;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class CollectCoins extends Frame {

	private Scene scene = new Scene(this);
	private Panel bottomPanel = new Panel();
	private Label time = new Label();
	Panel centerPanel = new Panel();
	/*
	 * TextArea - predstavlja komponentu koja prikazuje viselinijski tekst.
	 */
	TextArea log = new TextArea();
	Label score = new Label();
	Timer timer;

	private void populateWindow() {
		log.setColumns(20);
		log.setRows(1);
		log.setEditable(false);
		int dim = (getWidth() / 2) / scene.getRows() * scene.getRows();
		scene.setBackground(Color.GREEN);
		scene.setPreferredSize(new Dimension(dim, dim));
		/*
		 * Neophodno je postaviti hgap i vgap na 0,
		 * jer ce scena biti sadrzana u ovom panelu 
		 * i uzimace visinu i sirinu centralnog panela. 
		 */
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		centerPanel.add(scene);
		add(centerPanel, BorderLayout.CENTER);
		add(log, BorderLayout.EAST);

		TextField coins = new TextField("10");
		Button toss = new Button("Toss");
		toss.addActionListener((al) -> {
			scene.tossCoins(Integer.parseInt(coins.getText()));
			scene.repaint();
			if (timer != null) {
				timer.interrupt();
			}
			timer = new Timer(time);
			timer.start();
			timer.go();
			scene.requestFocus();
		});
		bottomPanel.add(new Label("Time: "));
		bottomPanel.add(time);
		bottomPanel.add(new Label("Score: "));
		bottomPanel.add(score);
		bottomPanel.add(new Label("Coins: "));
		bottomPanel.add(coins);
		bottomPanel.add(toss);
		add(bottomPanel, BorderLayout.SOUTH);
	}
	
	private void showHelpDialog() {
		Dialog help = new Dialog(this, ModalityType.APPLICATION_MODAL);
		help.setTitle("Help");
		help.add(new Label("Use a-s-w-d to move", Label.CENTER));
		help.setBounds(700, 200, 100, 100);
		help.setResizable(false);
		help.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				help.dispose();
			}
		});
		help.setVisible(true);
	}

	public CollectCoins() {

		setBounds(700, 200, 500, 300);
		setResizable(true);

		setTitle("Collect coins");

		populateWindow();
		showHelpDialog();
		pack();

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				scene.packScene();
				scene.repaint();
				pack();
			}
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (timer != null)
					timer.interrupt();
				dispose();
			}
		});
		setVisible(true);
	}

	public static void main(String[] args) {

		new CollectCoins();
	}
}
