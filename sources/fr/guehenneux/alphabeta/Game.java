package fr.guehenneux.alphabeta;

/**
 * @author Jonathan Guéhenneux
 */
public interface Game {

	/**
	 * @param player
	 * @return the heuristic value for the specified player
	 */
	double getHeuristicValue(Player player);

	/**
	 * @return the winning move value
	 */
	double getWinningMoveValue();

	/**
	 * @return the current player
	 */
	Player getCurrentPlayer();

	/**
	 * 
	 */
	void nextPlayer();

	/**
	 * 
	 */
	void previousPlayer();

	/**
	 * @return the winner if any or {@code null} if none (game not finished or draw)
	 */
	Player getWinner();

	/**
	 * @return whether the game is draw
	 */
	boolean isDraw();

	/**
	 * update the game GUI
	 */
	void updateGui();
}