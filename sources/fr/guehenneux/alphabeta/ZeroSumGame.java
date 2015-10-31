package fr.guehenneux.alphabeta;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class ZeroSumGame implements Game, Runnable {

	private static final Random RANDOM = new Random();

	protected int depth;

	/**
	 * 
	 */
	public ZeroSumGame() {
		this(2);
	}

	/**
	 * @param depth
	 */
	public ZeroSumGame(int depth) {
		this.depth = depth;
	}

	@Override
	public void run() {

		Player player;
		Move move;

		do {

			player = getCurrentPlayer();
			move = player.getMove();
			move.play();
			updateView();

		} while (getWinner() == null);
	}

	@Override
	public Move getBestMove() {

		Player currentPlayer = getCurrentPlayer();
		List<Move> moves = currentPlayer.getMoves();
		List<Move> bestMoves = new LinkedList<Move>();

		double moveValue;
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;

		for (Move move : moves) {

			move.play();
			moveValue = -alphaBeta(depth - 1, alpha, beta);
			move.cancel();

			if (moveValue > alpha) {

				bestMoves.clear();
				bestMoves.add(move);
				alpha = moveValue;

			} else if (moveValue == alpha) {

				bestMoves.add(move);
			}
		}

		int bestMoveCount = bestMoves.size();
		int bestMoveIndex = RANDOM.nextInt(bestMoveCount);
		Move bestMove = bestMoves.get(bestMoveIndex);

		return bestMove;
	}

	/**
	 * @param depth
	 *            the current depth
	 * @param alpha
	 * @param beta
	 * @return the current player value
	 */
	private double alphaBeta(int depth, double alpha, double beta) {

		Player currentPlayer = getCurrentPlayer();
		Player winner = getWinner();

		if (winner == currentPlayer) {

			alpha = getVictoryValue();

		} else if (depth == 0) {

			alpha = getHeuristicValue(currentPlayer);

		} else {

			List<Move> moves = currentPlayer.getMoves();

			double moveValue;
			boolean principalVariation = true;

			for (Move move : moves) {

				move.play();

				if (principalVariation) {

					moveValue = -alphaBeta(depth - 1, -beta, -alpha);

				} else {

					moveValue = -alphaBeta(depth - 1, -alpha - 1, -alpha);

					if (moveValue > alpha) {
						moveValue = -alphaBeta(depth - 1, -beta, -alpha);
					}
				}

				move.cancel();

				if (moveValue >= beta) {
					return moveValue;
				}

				if (moveValue > alpha) {

					alpha = moveValue;
					principalVariation = false;
				}
			}
		}

		return alpha;
	}

	/**
	 * @param moves
	 */
	private void sortMoves(List<Move> moves) {

		double moveValue;

		for (Move move : moves) {

			move.play();

			moveValue = -alphaBeta(0, 0, 0);
			move.setValue(moveValue);

			move.cancel();
		}

		Collections.sort(moves);
	}

	/**
	 * @return
	 */
	public abstract double getVictoryValue();

	/**
	 * 
	 */
	public abstract void updateView();
}