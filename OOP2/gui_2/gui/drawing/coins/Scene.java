package gui.drawing.coins;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Scene extends Canvas {

	private int rows = 10, squareWidth;
	private int score;
	private CollectCoins owner;
	private ArrayList<Figure> figures = new ArrayList<>();
	private Player player;

	private int getDim() {
		int width = owner.centerPanel.getWidth();
		int height = owner.centerPanel.getHeight();
		int w = width / rows * rows, h = height / rows * rows;
		return Math.max(w, h);
	}

	private void recalculatePositions() {

		int dim = getDim();

		int oldSquareWidth = squareWidth;
		squareWidth = dim / rows;
		int x, y;
		for (Figure f : figures) {
			x = ((int) (f.getX() / 1. / oldSquareWidth) * squareWidth) + squareWidth / 2;
			y = ((int) (f.getY() / 1. / oldSquareWidth) * squareWidth) + squareWidth / 2;
			f.setX(x);
			f.setY(y);
			f.setWidth(squareWidth / 2);
		}
		if (player != null)
			player.setWidth(squareWidth);
	}

	public void packScene() {
		recalculatePositions();
		int dim = getDim();
		setPreferredSize(new Dimension(dim, dim));
	}

	public void tossCoins(int coins) {
		owner.score.setText("");
		owner.log.setText("");
		score = 0;
		figures = new ArrayList<>();
		player = null;
		if (coins >= rows * rows) {
			coins /= 4;
		}
		int x, y, coinR = squareWidth / 2;
		for (int i = 0; i < coins; i++) {
			x = (int) (Math.random() * rows) * squareWidth + coinR;
			y = (int) (Math.random() * rows) * squareWidth + coinR;
			Coin c = new Coin(x, y, coinR);
			if (figures.contains(c)) {
				i--;
				continue;
			}
			figures.add(c);
		}
		do {
			x = (int) (Math.random() * rows) * squareWidth + squareWidth / 2;
			y = (int) (Math.random() * rows) * squareWidth + squareWidth / 2;
			player = new Player(x, y, coinR * 2);
		} while (figures.contains(player));
		figures.add(player);
	}

	private void adjustScore() {
		for (Figure f : figures) {
			if (player != f && player.equals(f)) {
				score++;
				owner.score.setText("" + score);
				owner.log.append("Coin collected at: " + owner.timer + "\n");
				figures.remove(f);
				if (figures.size() == 1) {
					owner.timer.interrupt();
				}
				break;
			}
		}
	}

	private void drawLines() {
		int dim = getDim();
		int step = dim / rows;
		Graphics g = getGraphics();
		g.setColor(Color.BLUE);
		for (int i = -1; i < dim; i += step) {
			g.drawLine(i < 0 ? 0 : i, 0, i < 0 ? 0 : i, dim - 1);
			g.drawLine(0, i < 0 ? 0 : i, dim - 1, i < 0 ? 0 : i);
		}
	}

	private void drawFigures() {
		if (player == null)
			return;
		for (Figure f : figures)
			f.paint(getGraphics());
		player.paint(getGraphics());
	}

	@Override
	public void paint(Graphics g) {
		adjustScore();
		drawLines();
		drawFigures();
	}

	public int getRows() {
		return rows;
	}

	public Scene(CollectCoins center) {
		this.owner = center;

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char key = Character.toUpperCase(e.getKeyChar());
				switch (key) {
				case KeyEvent.VK_W: {
					int y = player.getY() - squareWidth;
					player.setY(y < 0 ? player.getY() : y);
					break;
				}
				case KeyEvent.VK_S: {
					int y = player.getY() + squareWidth;
					player.setY(y > getHeight() ? player.getY() : y);
					break;
				}
				case KeyEvent.VK_A: {
					int x = player.getX() - squareWidth;
					player.setX(x < 0 ? player.getX() : x);
					break;
				}
				case KeyEvent.VK_D: {
					int x = player.getX() + squareWidth;
					player.setX(x > getWidth() ? player.getX() : x);
					break;
				}
				}
				repaint();
			}
		});
	}
}
