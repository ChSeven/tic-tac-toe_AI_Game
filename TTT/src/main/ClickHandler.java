package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickHandler implements MouseListener {

	private final TicTacToe game;

	public ClickHandler(TicTacToe game) {
		this.game = game;
	}

	public void mouseReleased(MouseEvent e) {
		if (!game.isAiTurn()) {
			game.attemptClaim(e.getX() - 3, e.getY() - 26);
			game.getGameframe().repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
