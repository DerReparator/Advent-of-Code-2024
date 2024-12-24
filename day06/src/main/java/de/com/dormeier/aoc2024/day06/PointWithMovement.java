package de.com.dormeier.aoc2024.day06;

import java.awt.Point;
import java.util.Objects;

public record PointWithMovement(Point pos, Movement direction) {
	public PointWithMovement {
		Objects.requireNonNull(pos);
		Objects.requireNonNull(direction);
	}
}
