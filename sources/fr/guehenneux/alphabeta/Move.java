package fr.guehenneux.alphabeta;

/**
 * @author Jonathan Guéhenneux
 */
public interface Move {

	/**
	 * play the move
	 */
	public abstract void play();

	/**
	 * cancel the move
	 */
	public abstract void cancel();
}