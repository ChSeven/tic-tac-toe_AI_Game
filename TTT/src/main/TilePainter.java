package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public final class TilePainter extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final TicTacToe game;

	public TilePainter(TicTacToe game) {
		this.game = game;
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("Dialogue", Font.BOLD, game.getFontSize()));
		for (Tile t : game.getTiles()) {
			g.setColor(Color.BLACK);
			g.drawRect(t.getX() - 1, t.getY() - 1, t.getWidth() + 1,
					t.getHeight() + 1);
			t.paint(g2d);
		}
	}

}
x