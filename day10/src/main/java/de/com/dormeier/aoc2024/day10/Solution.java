package de.com.dormeier.aoc2024.day10;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * Advent of Code 2024 - Day 10
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
		Object solutionPart2 = solvePart2(inputPart2);

		System.out.println("Solution Part 1: " + solutionPart1);
		System.out.println("Solution Part 2: " + solutionPart2);
    }

    /**
	 * Solve the first part.
	 * 
	 * @param input The textual input
	 * @return The solution to the first part.
	 */
	protected static Object solvePart1(String input) {
		String[] inputLineByLine = input.split("\n");
		int[][] map = new int[inputLineByLine.length][inputLineByLine[0].strip().length()];
		for (int y = 0; y < inputLineByLine.length; ++y) {
			for (int x = 0; x < inputLineByLine[y].strip().length(); ++x) {
				map[y][x] = inputLineByLine[y].charAt(x) == '.' ? -9999
						: Integer.parseInt("" + inputLineByLine[y].charAt(x));
			}
		}

		int totalScore = 0;

		for (int y = 0; y < map.length; ++y) {
			for (int x = 0; x < map.length; ++x) {
				if (map[y][x] == 0) {
					Set<Point> peaks = new HashSet<Point>();
					calculateScoreFrom(map, peaks, x, y, -1, -1);
					totalScore += peaks.size();
				}
			}
		}

		return totalScore;
	}

	private static boolean isOutOfBounds(int[][] map, int x, int y) {
		if (x < 0 || y < 0 || y >= map.length || x >= map[0].length) {
			return true;
		}
		return false;
	}

	/* UP, RIGHT, DOWN, LEFT */
	private static final int[] dirY = { -1, 0, 1, 0 };
	private static final int[] dirX = { 0, 1, 0, -1 };

	/**
	 * This method tries to walk the trail further from {@code x}, {@code y} and
	 * stores found peaks uniquely in the parameter {@code foundPeaks}
	 * 
	 * @param map
	 * @param foundPeaks
	 * @param x
	 * @param y
	 * @param prevX
	 * @param prevY
	 */
	private static void calculateScoreFrom(int[][] map, Set<Point> foundPeaks, int x, int y, int prevX, int prevY) {
		System.out.println(String.format("Visiting [%3d;%3d] after [%3d;%3d]", x, y, prevX, prevY));

		if (map[y][x] == 9) {
			Point peak = new Point(x, y);
			System.out.println("Found peak @" + peak);
			if (foundPeaks.contains(peak)) {
				System.out.println("old peak");
				return;
			} else {
				foundPeaks.add(peak);
				System.out.println("NEW PEAK");
				return;
			}
		}

		int height = map[y][x];

		for (int dir = 0; dir < dirY.length; ++dir) {
			int nextX = x + dirX[dir];
			int nextY = y + dirY[dir];

			if (!isOutOfBounds(map, nextX, nextY) && !(nextX == prevX && nextY == prevY)
					&& map[nextY][nextX] == height + 1) {
				calculateScoreFrom(map, foundPeaks, nextX, nextY, x, y);
			}
		}
	}

	/**
	 * Solve the second part.
	 * 
	 * @param input The textual input
	 * @return The solution to the second part.
	 */
	protected static Object solvePart2(String input) {
		/* TODO your solution for Part 2 goes here. */        
        return "";
	}
}
