package main;

import java.awt.Color;

public enum Holder {
	X, O, GAME, ANY;

	public String getText() {
		switch (this) {
		case X:
			return "X";
		case O:
			return "O";
		default:
			return " ";
		}
	}

	public Holder getOpposite() {
		switch (this) {
		case X:
			return O;
		case O:
			return X;
		default:
			return GAME;
		}
	}

	public Color getColor() {
		switch (this) {
		case X:
			return Color.LIGHT_GRAY;
		case O:
			return Color.DARK_GRAY;
		default:
			return Color.WHITE;
		}
	}

	public Color getTextColor() {
		switch (this) {
		case X:
			return Color.BLACK;
		case O:
			return Color.WHITE;
		default:
			return Color.MAGENTA;
		}
	}
}
