package fr.guehenneux.alphabeta;

import java.util.List;

/**
 * @author Jonathan Guéhenneux
 */
public interface Player {

	/**
	 * @return the possible moves
	 */
	public abstract List<Move> getMoves();

	/**
	 * @return the chosen move
	 */
	public abstract Move getMove();
}