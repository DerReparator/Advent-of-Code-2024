package de.com.dormeier.aoc2024.day06;

public enum Movement {

	UP(-1, 0),
	RIGHT(0,1),
	DOWN(1,0),
	LEFT(0, -1);

	public final int vertical;
	public final int horizontal;

	Movement(int vertical, int horizontal) {
		this.vertical = vertical;
		this.horizontal = horizontal;
	}

	public Movement turnRight() {
		return Movement.values()[(this.ordinal() + 1) % Movement.values().length];
	}
}