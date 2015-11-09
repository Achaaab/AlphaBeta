package fr.guehenneux.alphabeta;

import java.util.Stack;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class ZeroSumGame implements Game, Runnable {

	protected Stack<Move> moves;

	/**
	 * 
	 */
	public ZeroSumGame() {
		moves = new Stack<>();
	}

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
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println(getWinner());
	}

	/**
	 * 
	 */
	public abstract void updateUI();

	@Override
	public Move getLastMove() {

		Move lastMove;

		if (moves.isEmpty()) {
			lastMove = null;
		} else {
			lastMove = moves.peek();
		}

		return lastMove;
	}

	@Override
	public Stack<Move> getMoves() {
		return moves;
	}

	@Override
	public void addMove(Move move) {
		moves.push(move);
	}

	@Override
	public void removeMove() {
		moves.pop();
	}
}