package fr.guehenneux.alphabeta;

/**
 * @author Jonathan Gu�henneux
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