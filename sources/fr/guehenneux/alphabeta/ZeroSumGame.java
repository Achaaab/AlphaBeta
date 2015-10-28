package fr.guehenneux.alphabeta;

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
	public Move getBestMove() {

		Player currentPlayer = getCurrentPlayer();
		List<Move> moves = currentPlayer.getMoves();

		List<Move> bestMoves = new LinkedList<Move>();

		double moveValue;
		double bestMoveValue = Double.NEGATIVE_INFINITY;

		for (Move move : moves) {

			move.play();
			moveValue = -alphaBeta(depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			move.cancel();

			System.out.println(move + " --> " + moveValue);

			if (moveValue > bestMoveValue) {

				bestMoves.clear();
				bestMoves.add(move);
				bestMoveValue = moveValue;

			} else if (moveValue == bestMoveValue) {

				bestMoves.add(move);
			}
		}

		int bestMoveCount = bestMoves.size();
		int bestMoveIndex = RANDOM.nextInt(bestMoveCount);
		Move bestMove = bestMoves.get(bestMoveIndex);

		return bestMove;
	}

	@Override
	public void run() {

		Player player;
		Move move;

		do {

			player = getCurrentPlayer();
			move = player.getMove();

			System.out.println(move);

			move.play();
			updateView();

			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} while (getWinner() == null);
	}

	/**
	 * @return
	 */
	public abstract double getVictoryValue();

	/**
	 * 
	 */
	public abstract void updateView();

	/**
	 * @param depth
	 *            the current depth
	 * @param alpha
	 * @param beta
	 * @return the current player value
	 */
	private double alphaBeta(int depth, double alpha, double beta) {

		double alphaBeta;

		Player currentPlayer = getCurrentPlayer();
		Player winner = getWinner();

		if (winner == currentPlayer) {

			alphaBeta = getVictoryValue();

		} else if (depth == 0) {

			alphaBeta = getHeuristicValue(currentPlayer);

		} else {

			alphaBeta = Double.NEGATIVE_INFINITY;

			List<Move> moves = currentPlayer.getMoves();
			double alphaBetaMove;

			for (Move move : moves) {

				move.play();
				alphaBetaMove = -alphaBeta(depth - 1, -beta, -alpha);

				// for (int d = depth; d <= this.depth; d++) {
				// System.out.print('\t');
				// }
				// System.out.println(move + " --> " + alphaBetaMove);

				move.cancel();

				if (alphaBetaMove > alphaBeta) {

					alphaBeta = alphaBetaMove;

					if (alphaBeta > alpha) {

						alpha = alphaBeta;

						if (alpha >= beta) {
							return alphaBeta;
						}
					}
				}
			}
		}

		return alphaBeta;
	}
}