package fr.guehenneux.alphabeta;

/**
 * @author Jonathan Guéhenneux
 */
public interface Game {

	/**
	 * @param player
	 * @return
	 */
	public abstract double getHeuristicValue(Player player);

	/**
	 * 
	 * @return le joueur dont c'est le tour
	 */
	public abstract Player getCurrentPlayer();

	/**
	 * 
	 */
	public abstract void nextPlayer();

	/**
	 * 
	 */
	public abstract void previousPlayer();

	/**
	 * @return the best move to play
	 */
	public abstract Move getBestMove();

	/**
	 * @return the winner if any or {@code null} if none (game not finished or draw)
	 */
	public abstract Player getWinner();
}