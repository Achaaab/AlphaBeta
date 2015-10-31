package fr.guehenneux.alphabeta;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class TwoPlayersZeroSumGame extends ZeroSumGame {

	protected Player player0;
	protected Player player1;

	protected Player currentPlayer;

	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public void nextPlayer() {

		if (currentPlayer == player0) {
			currentPlayer = player1;
		} else {
			currentPlayer = player0;
		}
	}

	@Override
	public void previousPlayer() {

		if (currentPlayer == player0) {
			currentPlayer = player1;
		} else {
			currentPlayer = player0;
		}
	}
}