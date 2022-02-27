package main;

import java.awt.Dimension;
import java.util.concurrent.TimeUnit;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class TicTacToe extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Tile[] TILES = new Tile[9];
	private final int TILE_SPACING = 96;
	private final int WIDTH = 96, HEIGHT = 96;
	private final JFrame GAMEFRAME = new JFrame("Tic-Tac-Toe");
	private final TilePainter PAINTER = new TilePainter(this);
	private final ClickHandler CLICK_HANDLER = new ClickHandler(this);
	private final boolean AI;
	private boolean aiTurn = false;
	private Holder turn = Holder.X;
	private int whoseTurn = 0;
	private final Dimension FRAME_SIZE = new Dimension(295, 304);
	private final int FONT_SIZE = 64;
	private int oWins = 0;
	private int xWins = 0;
	private boolean gameOver = false;
	private boolean nextTurn = false;
	public final AI GAME_AI = new AI(this);

	public void init() {
		TicTacToe game = new TicTacToe(true);
		game.newGame();
	}

	private final int[][] wins = { { 1, 1, 1, 0, 0, 0, 0, 0, 0 },

	{ 0, 0, 0, 1, 1, 1, 0, 0, 0 },

	{ 0, 0, 0, 0, 0, 0, 1, 1, 1 },

	{ 1, 0, 0, 0, 1, 0, 0, 0, 1 },

	{ 1, 0, 0, 1, 0, 0, 1, 0, 0 },

	{ 0, 1, 0, 0, 1, 0, 0, 1, 0 },

	{ 0, 0, 1, 0, 0, 1, 0, 0, 1 },

	{ 0, 0, 1, 0, 1, 0, 1, 0, 0 } };

	public boolean allFull() {
		for (Tile t : TILES) {
			if (!t.isClaimed()) {
				return false;
			}
		}
		return true;
	}

	public boolean hasWon(Holder h) {
		boolean hasWon;
		for (int[] i : wins) {
			hasWon = true;
			for (int j = 0; j < i.length; j++) {
				if (i[j] == 1) {
					if (TILES[j].getHolder() != h) {
						hasWon = false;
						j = i.length;
					}
				}
			}
			if (hasWon)
				return true;
		}
		return false;
	}

	public int toWin(Holder h, int[] win, Tile[] tiles) {
		int total = 0;
		for (int j = 0; j < win.length; j++) {
			if (win[j] == 1) {
				total++;
				if (tiles[j].getHolder() == h) {
					total--;
				} else if (tiles[j].getHolder() != Holder.GAME) {
					return -1;
				}
			}
		}
		return total;
	}

	public Tile getWin(Holder h, int[] win, Tile[] tiles) {
		for (int j = 0; j < win.length; j++) {
			if (win[j] == 1) {
				if (tiles[j].getHolder() == Holder.GAME) {
					return TILES[j];
				}
			}
		}
		return null;
	}

	public void endGame() {
		gameOver = true;
	}

	public int getFontSize() {
		return FONT_SIZE;
	}

	public TicTacToe(boolean ai) {
		this.AI = ai;
		PAINTER.setSize(FRAME_SIZE);
		buildFrame();
		loadTiles();
	}

	public void loadTiles() {
		int tile = 0;
		for (int i = 0; i < TILES.length / 3; i++) {
			for (int j = 0; j < TILES.length / 3; j++) {
				TILES[tile] = new Tile(i * this.TILE_SPACING, j
						* this.TILE_SPACING, this.WIDTH, this.HEIGHT, this);
				tile++;
			}
		}
	}

	private void nextTurn() {
		if (hasWon(turn)) {
			gameOver = true;
			sendWin(turn);
			return;
		}
		if (allFull()) {
			gameOver = true;
			sendDraw();
			return;
		}
		turn = turn.getOpposite();
		if (aiTurn && AI) {
			aiTurn = false;
		} else if (!aiTurn && AI) {
			aiTurn = true;
			GAME_AI.aiMove();
		} else {
			aiTurn = false;
		}
	}

	public void attemptClaim(int x, int y) {
		for (int i = 0; i < TILES.length; i++) {
			if (!TILES[i].isClaimed() && TILES[i].inArea(x, y)) {
				TILES[i].claim(turn);
				nextTurn = true;
				return;
			}
		}
	}

	public boolean isAiTurn() {
		return aiTurn;
	}

	private void buildFrame() {
		getGameframe().addMouseListener(CLICK_HANDLER);
		getGameframe().setSize(FRAME_SIZE);
		getGameframe().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getGameframe().setResizable(false);
		getGameframe().setMaximumSize(FRAME_SIZE);
		getGameframe().setMinimumSize(FRAME_SIZE);
		getGameframe().add(PAINTER);
		getGameframe().pack();
	}

	private JFrame outcome = new JFrame();

	private void sendWin(Holder winner) {
		outcome.setVisible(false);
		outcome.dispose();
		outcome = null;
		if (winner == Holder.X)
			xWins++;
		else if (winner == Holder.O)
			oWins++;
		outcome = new JFrame(winner.getText() + " has won!");
		JLabel winMessage = new JLabel("  " + winner.getText()
				+ " has won! Score is X: " + xWins + ", O: " + oWins);
		outcome.add(winMessage);
		outcome.setResizable(false);
		outcome.setAlwaysOnTop(true);
		outcome.pack();
		outcome.setVisible(true);
	}

	private void sendDraw() {
		outcome.setVisible(false);
		outcome.dispose();
		outcome = null;
		outcome = new JFrame("Draw!");
		JLabel drawMessage = new JLabel("  Its a Draw! Score is X: " + xWins
				+ ", O: " + oWins);
		outcome.add(drawMessage);
		outcome.setResizable(false);
		outcome.setAlwaysOnTop(true);
		outcome.pack();
		outcome.setVisible(true);
	}

	private void resetTiles() {
		for (Tile t : TILES)
			t.reset();
	}

	public void newGame() {
		while (true) {
			gameOver = false;
			resetTiles();
			GAMEFRAME.setVisible(true);
			if (whoseTurn == 1 && this.AI) {
				whoseTurn = 0;
				turn = Holder.O;
				aiTurn = true;
			} else if (this.AI) {
				turn = Holder.X;
				aiTurn = false;
				whoseTurn = 1;
			} else {
				turn = Holder.X;
			}
			if (aiTurn)
				GAME_AI.aiMove();
			while (!gameOver) {
				if (nextTurn) {
					nextTurn = false;
					nextTurn();
				}
				try {
					TimeUnit.MILLISECONDS.sleep(25);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				GAMEFRAME.repaint();
			}

			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getGameframe().setVisible(false);
		}
	}

	public int[][] getWins() {
		return wins;
	}

	public Holder getTurn() {
		return turn;
	}

	public Tile[] getTiles() {
		return TILES;
	}

	public static void main(String[] args) {
		TicTacToe game;
		if (args.length >= 1) {
			game = new TicTacToe(false);
			game.newGame();
		} else {
			game = new TicTacToe(true);
			game.newGame();
		}
	}

	public JFrame getGameframe() {
		return GAMEFRAME;
	}
}