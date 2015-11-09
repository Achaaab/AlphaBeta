package fr.guehenneux.alphabeta;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class AbstractMove implements Move {

	protected double value;
	private Game game;

	/**
	 * @param game
	 */
	public AbstractMove(Game game) {
		this.game = game;
	}

	@Override
	public int compareTo(Move move) {

		int comparison;

		double moveValue = move.getValue();

		if (value < moveValue) {
			comparison = 1;
		} else if (value == moveValue) {
			comparison = 0;
		} else {
			comparison = -1;
		}

		return comparison;
	}

	@Override
	public void play() {

		game.addMove(this);
		game.nextPlayer();
	}

	@Override
	public void cancel() {

		game.removeMove();
		game.previousPlayer();
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}
}