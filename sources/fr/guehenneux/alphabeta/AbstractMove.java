package fr.guehenneux.alphabeta;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class AbstractMove implements Move {

	protected double value;

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
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}
}