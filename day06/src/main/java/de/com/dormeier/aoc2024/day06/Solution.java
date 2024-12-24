package de.com.dormeier.aoc2024.day06;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Advent of Code 2024 - Day 06
 */
public class Solution
{
    public static void main(String[] args) throws IOException
    {
		if (args.length != 2) {
			System.out.println("Invalid args. Expected exactly 2 args: pathToInputDay1 pathToInputDay2");
			return;
		}

		String inputPart1 = String.join(System.lineSeparator(), Files.readAllLines(Paths.get(args[0])));
		String inputPart2 = String.join(System.lineSeparator(), Files.readAllLines(Paths.get(args[1])));

		Object solutionPart1 = solvePart1(inputPart1);
		// Object solutionPart2 = solvePart2(inputPart2);


		System.out.println("Solution Part 1: " + solutionPart1);
		// System.out.println("Solution Part 2: " + solutionPart2);
    }

	private static final char BARRIER = '#';
	private static final char MOVE_UP = '^';

    /**
	 * Solve the first part.
	 * 
	 * @param input The textual input
	 * @return The solution to the first part.
	 */
	protected static Object solvePart1(String input) {
		String[] map = input.split(System.lineSeparator());
		
		Movement currMovement = Movement.UP;
		Optional<Point> currPos = Optional.empty();
		Set<Point> visited = new HashSet<>();
		
		/* find current position */
		for (int line = 0; line < map.length; ++line) {
			int posInRow = map[line].indexOf(MOVE_UP);
			if (posInRow != -1) {
				currPos = Optional.of(new Point(posInRow, line));
				break;
			}
		}

		do {
			currPos = doWalk(map, currPos.get(), currMovement, visited);
			System.out.println("Moved to " + currPos);
			currMovement = currMovement.turnRight();
		} while (currPos.isPresent());
		
		printVisitedMap(map, visited);

		return visited.size();
	}

	/**
	 * For Part 1.
	 * 
	 * @param map
	 * @param currPosition
	 * @param currMovement
	 * @param visited
	 * @return
	 */
	private static Optional<Point> doWalk(String[] map, Point currPosition, Movement currMovement, Set<Point> visited) {
		do {
			visited.add(currPosition);
			Point nextPosition = new Point(currPosition.x + currMovement.horizontal,
					currPosition.y + currMovement.vertical);

			System.out.println("Trying to move to " + nextPosition);

			if (isOutOfBounds(map, nextPosition)) {
				return Optional.empty();
			}

			if (map[nextPosition.y].charAt(nextPosition.x) == BARRIER) {
				return Optional.of(currPosition);
			}

			currPosition = nextPosition;
		} while (true);
	}

	private static boolean isOutOfBounds(String[] map, Point pos) {
		if (pos.x < 0 || pos.y < 0
				|| pos.y >= map.length
				|| pos.x >= map[0].length()) {
			return true;
		}
		return false;
	}

	private static void printVisitedMap(String[] map, Collection<Point> visitedLocations) {
		for (int line = 0; line < map.length; ++line) {
			for (int col = 0; col < map[line].length(); ++col) {
				if (visitedLocations.contains(new Point(col, line))) {
					System.out.print('X');
				}
				else {
					System.out.print(map[line].charAt(col));
				}
			}
			System.out.println();
		}
	}

	/**
	 * Solve the second part.
	 * 
	 * @param input The textual input
	 * @return The solution to the second part.
	 */
	protected static Object solvePart2(String input) {
		String[] map = input.split(System.lineSeparator());

		Movement currMovement = Movement.UP;
		Optional<PointWithMovement> currPos = Optional.empty();
		Set<PointWithMovement> initialGuardPath = new HashSet<>();

		/* find current position */
		for (int line = 0; line < map.length; ++line) {
			int posInRow = map[line].indexOf(MOVE_UP);
			if (posInRow != -1) {
				currPos = Optional.of(new PointWithMovement(new Point(posInRow, line), Movement.UP));
				break;
			}
		}

		do {
			currPos = doWalk(map, currPos.get(), initialGuardPath);
			System.out.println("Moved to " + currPos);
			currMovement = currMovement.turnRight();
		} while (currPos.isPresent());
		
		printVisitedMap(map,
				initialGuardPath.stream().map(pointWithMove -> pointWithMove.pos()).collect(Collectors.toList()));

		return initialGuardPath.size();
	}

	/**
	 * For Part 2.
	 * 
	 * @param map
	 * @param currPosition
	 * @param currMovement
	 * @param visited
	 * @return
	 */
	private static Optional<PointWithMovement> doWalk(String[] map, PointWithMovement currPosition,
			Set<PointWithMovement> visited) {
		do {

			Point nextPosition = new Point(currPosition.pos().x + currPosition.direction().horizontal,
					currPosition.pos().y + currPosition.direction().vertical);

			System.out.println("Trying to move to " + nextPosition);

			if (isOutOfBounds(map, nextPosition)) {
				visited.add(currPosition);
				return Optional.empty();
			}

			if (map[nextPosition.y].charAt(nextPosition.x) == BARRIER) {
				/*
				 * if we hit a barrier, the pos in front of the barrier gets "visited" after
				 * turning right.
				 */
				PointWithMovement turnedCurrPosition = new PointWithMovement(currPosition.pos(),
						currPosition.direction().turnRight());
				visited.add(turnedCurrPosition);
				return Optional.of(turnedCurrPosition);
			}
			else {
				visited.add(currPosition);
			}

			currPosition = new PointWithMovement(new Point(nextPosition), currPosition.direction());
		} while (true);
	}
}