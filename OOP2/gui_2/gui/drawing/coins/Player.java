package gui.drawing.coins;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Figure {
	
	public Player(int x, int y, int width) {
		super(x, y, width);
	}

	@Override
	public void paint(Graphics g) {
		Color prevColor = g.getColor();
		g.setColor(Color.RED);
		g.drawLine(x, y - width/2, x, y + width/2 - 1);
		g.drawLine(x - width/2, y, x + width/2 - 1, y);
		g.setColor(prevColor);
	}
}
