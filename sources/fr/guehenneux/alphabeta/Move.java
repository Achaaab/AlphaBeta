package fr.guehenneux.alphabeta;

/**
 * @author Jonathan Guéhenneux
 */
public interface Move extends Comparable<Move> {

	/**
	 * play the move
	 */
	void play();

	/**
	 * cancel the move
	 */
	void cancel();

	/**
	 * @return the move value
	 */
	double getValue();

	/**
	 * @param value
	 *            the move value
	 */
	void setValue(double value);
}