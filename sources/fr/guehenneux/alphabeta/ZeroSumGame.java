package fr.guehenneux.alphabeta;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class ZeroSumGame implements Game, Runnable {

	@Override
	public void run() {

		Player player;
		Move move;

		while (getWinner() == null && !isDraw()) {

			player = getCurrentPlayer();
			move = player.getMove();
			move.play();
			updateUI();

		}
	}

	/**
	 * 
	 */
	public abstract void updateUI();
}