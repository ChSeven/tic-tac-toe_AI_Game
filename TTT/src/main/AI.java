package main;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AI {
	private final TicTacToe game;

	public AI(TicTacToe game) {
		this.game = game;
	}

	private void randomMove() {
		Tile[] possible = new Tile[0];
		Tile[] tiles = game.getTiles();
		Random random = new Random();
		for (Tile t : tiles) {
			if (!t.isClaimed()) {
				possible = Arrays.copyOfRange(possible, 0, possible.length + 1);
				possible[possible.length - 1] = t;
			}
		}
		if (possible.length > 0) {
			int toClaim = random.nextInt(possible.length);
			game.attemptClaim(possible[toClaim].getX(),
					possible[toClaim].getY());
		}
	}

	public void aiMove() {
		Random random = new Random();
		int[][] wins = game.getWins();
		Tile[] tiles = game.getTiles();
		try {
			TimeUnit.SECONDS.sleep(random.nextInt(3));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < wins.length; i++) {
			if (game.toWin(game.getTurn(), wins[i], tiles) == 1) {
				Tile winningTile = game.getWin(game.getTurn(), wins[i], tiles);
				game.attemptClaim(winningTile.getX() + 1,
						winningTile.getY() + 1);
				System.out.println("Claiming win...");
				return;
			}
			if (game.toWin(game.getTurn().getOpposite(), wins[i], tiles) == 1) {
				Tile defendingTile = game.getWin(game.getTurn().getOpposite(),
						wins[i], tiles);
				game.attemptClaim(defendingTile.getX() + 1,
						defendingTile.getY() + 1);
				System.out.println("Defending...");
				return;
			}
		}
		randomMove();
	}
}
