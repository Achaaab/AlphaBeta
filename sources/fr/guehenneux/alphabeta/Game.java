package fr.guehenneux.alphabeta;

import java.util.Stack;

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
	 * @param move
	 */
	void addMove(Move move);

	/**
	 * @param move
	 */
	void removeMove();

	/**
	 * @return the last move if any, {@code null} if none
	 */
	Move getLastMove();

	/**
	 * @return the played moves
	 */
	Stack<Move> getMoves();

	/**
	 * update the user interface
	 */
	void updateUI();
}