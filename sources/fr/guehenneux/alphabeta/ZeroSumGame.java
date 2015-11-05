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
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	public abstract void updateUI();
}