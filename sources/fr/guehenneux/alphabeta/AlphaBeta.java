package fr.guehenneux.alphabeta;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Jonathan Guéhenneux
 */
public class AlphaBeta implements DecisionAlgorithm {

	private static final Random RANDOM = new Random();

	private TwoPlayersZeroSumGame game;
	private int maximumDepth;
	private boolean deterministic;

	/**
	 * @param game
	 * @param maximumDepth
	 * @param determinisitic
	 */
	public AlphaBeta(TwoPlayersZeroSumGame game, int maximumDepth, boolean deterministic) {

		this.game = game;
		this.maximumDepth = maximumDepth;
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
		double alphaBeta = Double.NEGATIVE_INFINITY;

		for (Move move : moves) {

			move.play();
			moveValue = -getMoveValue(1, -beta, -alpha);
			move.cancel();

			if (moveValue > alphaBeta) {

				alphaBeta = moveValue;

				if (alphaBeta > alpha) {
					alpha = alphaBeta;
				}

				bestMoves.clear();
				bestMoves.add(move);

			} else if (moveValue == alphaBeta && !deterministic) {

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

		double alphaBeta;

		Player currentPlayer = game.getCurrentPlayer();
		Player winner = game.getWinner();

		if (winner == currentPlayer) {

			alphaBeta = game.getWinningMoveValue() - depth;

		} else if (game.isDraw()) {

			alphaBeta = 0;

		} else if (depth == maximumDepth) {

			alphaBeta = game.getHeuristicValue(currentPlayer);

		} else {

			List<Move> moves = currentPlayer.getMoves();
			sortMoves(moves);

			double moveValue;
			alphaBeta = Double.NEGATIVE_INFINITY;

			for (Move move : moves) {

				move.play();
				moveValue = -getMoveValue(depth + 1, -beta, -alpha);
				move.cancel();

				if (moveValue > alphaBeta) {

					alphaBeta = moveValue;

					if (alphaBeta > alpha) {

						alpha = alphaBeta;

						if (alpha >= beta) {
							break;
						}
					}
				}
			}
		}

		return alphaBeta;
	}

	/**
	 * @param moves
	 */
	private void sortMoves(List<Move> moves) {

		double moveValue;

		for (Move move : moves) {

			move.play();

			moveValue = -getMoveValue(maximumDepth, 0, 0);
			move.setValue(moveValue);

			move.cancel();
		}

		Collections.sort(moves);
	}
}