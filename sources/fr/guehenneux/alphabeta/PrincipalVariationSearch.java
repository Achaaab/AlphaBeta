package fr.guehenneux.alphabeta;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Jonathan Guéhenneux
 */
public class PrincipalVariationSearch implements DecisionAlgorithm {

	private static final Random RANDOM = new Random();

	private TwoPlayersZeroSumGame game;
	private int depth;
	private boolean deterministic;

	/**
	 * @param game
	 * @param depth
	 * @param determinisitic
	 */
	public PrincipalVariationSearch(TwoPlayersZeroSumGame game, int depth, boolean deterministic) {

		this.game = game;
		this.depth = depth;
		this.deterministic = deterministic;
	}

	@Override
	public Move getBestMove() {

		Player currentPlayer = game.getCurrentPlayer();
		List<Move> moves = currentPlayer.getMoves();
		sortMoves(moves);
		List<Move> bestMoves = new LinkedList<Move>();

		double moveValue;
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;

		for (Move move : moves) {

			move.play();
			moveValue = -getMoveValue(depth - 1, -beta, -alpha);
			move.cancel();

			if (moveValue > alpha) {

				bestMoves.clear();
				bestMoves.add(move);
				alpha = moveValue;

			} else if (moveValue == alpha && !deterministic) {

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
	 * @param alpha
	 * @param beta
	 * @return
	 */
	private double getMoveValue(int depth, double alpha, double beta) {

		Player currentPlayer = game.getCurrentPlayer();
		Player winner = game.getWinner();

		if (winner == currentPlayer) {

			alpha = game.getWinningMoveValue();

		} else if (game.isDraw()) {

			alpha = 0;

		} else if (depth == 0) {

			alpha = game.getHeuristicValue(currentPlayer);

		} else {

			List<Move> moves = currentPlayer.getMoves();
			sortMoves(moves);

			double moveValue;
			boolean firstMove = true;

			for (Move move : moves) {

				move.play();

				if (firstMove) {

					moveValue = -getMoveValue(depth - 1, -beta, -alpha);
					firstMove = false;

				} else {

					moveValue = -getMoveValue(depth - 1, -alpha - 1, -alpha);

					if (moveValue > alpha && moveValue < beta) {
						moveValue = -getMoveValue(depth - 1, -beta, -alpha);
					}
				}

				move.cancel();

				if (moveValue > alpha) {
					alpha = moveValue;
				}

				if (alpha >= beta) {
					break;
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

			moveValue = -getMoveValue(0, 0, 0);
			move.setValue(moveValue);

			move.cancel();
		}

		Collections.sort(moves);
	}
}