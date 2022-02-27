package main;

import java.awt.Graphics;

public final class Tile {

	private final int x, y, width, height;
	private boolean claimed = false;
	private Holder heldBy = Holder.GAME;
	private final TicTacToe game;

	public Tile(int x, int y, int width, int height, TicTacToe game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.game = game;
	}

	public void claim(Holder h) {
		if (heldBy == Holder.GAME && !claimed) {
			claimed = true;
			heldBy = h;
		}
	}

	public Holder getHolder() {
		return heldBy;
	}

	public void paint(Graphics g) {
		g.setColor(heldBy.getColor());
		g.fillRect(x, y, width, height);
		g.setColor(heldBy.getTextColor());
		g.drawString(heldBy.getText(), middleX() - (game.getFontSize() / 3),
				middleY() + (game.getFontSize() / 3));
	}

	private int middleX() {
		return (x + width / 2);
	}

	private int middleY() {
		return (y + height / 2);
	}

	public void reset() {
		this.heldBy = Holder.GAME;
		this.claimed = false;
	}

	public boolean inArea(int x, int y) {
		return (this.x <= x && this.x + this.width >= x)
				&& (this.y <= y && this.y + this.height >= y);
	}

	public boolean isClaimed() {
		return claimed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
