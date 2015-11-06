package fr.guehenneux.alphabeta;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Jonathan Guéhenneux
 */
public class MiniMax implements DecisionAlgorithm {

	private static final Random RANDOM = new Random();

	private TwoPlayersZeroSumGame game;
	private int maximumDepth;
	private boolean deterministic;

	/**
	 * @param game
	 * @param maximumDepth
	 * @param determinisitic
	 */
	public MiniMax(TwoPlayersZeroSumGame game, int maximumDepth, boolean deterministic) {

		this.game = game;
		this.maximumDepth = maximumDepth;
		this.deterministic = deterministic;
	}

	@Override
	public Move getBestMove() {

		Player currentPlayer = game.getCurrentPlayer();
		List<Move> moves = currentPlayer.getMoves();
		List<Move> bestMoves = new LinkedList<Move>();

		double moveValue;
		double minimax = Double.NEGATIVE_INFINITY;

		for (Move move : moves) {

			move.play();
			moveValue = -getMoveValue(1);
			move.cancel();

			if (moveValue > minimax) {

				bestMoves.clear();
				bestMoves.add(move);
				minimax = moveValue;

			} else if (moveValue == minimax && !deterministic) {

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
	 * @return
	 */
	private double getMoveValue(int depth) {

		double minimax;

		Player currentPlayer = game.getCurrentPlayer();
		Player winner = game.getWinner();

		if (winner == currentPlayer) {

			minimax = game.getWinningMoveValue() - depth;

		} else if (game.isDraw()) {

			minimax = 0;

		} else if (depth == maximumDepth) {

			minimax = game.getHeuristicValue(currentPlayer);

		} else {

			List<Move> moves = currentPlayer.getMoves();

			double moveValue;
			minimax = Double.NEGATIVE_INFINITY;

			for (Move move : moves) {

				move.play();
				moveValue = -getMoveValue(depth + 1);
				move.cancel();

				if (moveValue > minimax) {
					minimax = moveValue;
				}
			}
		}

		return minimax;
	}
}