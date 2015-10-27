package fr.guehenneux.alphabeta;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class ZeroSumGame implements Game {

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
			moveValue = negaMax(currentPlayer, depth);
			move.cancel();

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

	/**
	 * @return
	 */
	public abstract double getVictoryValue();

	/**
	 * @param previousPlayer
	 *            the player who played previously
	 * @param depth
	 *            the current depth
	 * @return the current player value
	 */
	private double negaMax(Player previousPlayer, int depth) {

		double negaMax;

		Player winner = getWinner();

		if (winner == previousPlayer) {

			negaMax = getVictoryValue();

		} else {

			if (depth == 0) {

				negaMax = getHeuristicValue(previousPlayer);

			} else {

				negaMax = Double.NEGATIVE_INFINITY;

				Player currentPlayer = getCurrentPlayer();

				List<Move> coups = currentPlayer.getMoves();
				double negaMaxCoup;

				for (Move coup : coups) {

					coup.play();
					negaMaxCoup = -negaMax(currentPlayer, depth - 1);
					coup.cancel();

					if (negaMaxCoup > negaMax) {
						negaMax = negaMaxCoup;
					}
				}
			}
		}

		return negaMax;
	}
}