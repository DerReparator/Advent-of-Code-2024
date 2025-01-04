package de.com.dormeier.aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Advent of Code 2024 - Day 08
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
		Map<Character, Set<Point>> frequencyPositions = new HashMap<>();
		Set<Point> uniqueAntinodes = new HashSet<>();

		String[] inputByLines = input.split(System.lineSeparator());
		for (int y = 0; y < inputByLines.length; ++y) {
			for (int x = 0; x < inputByLines[0].length(); ++x) {
				char currChar = inputByLines[y].charAt(x);
				if (currChar == '.') continue;

				if (!frequencyPositions.containsKey(currChar)) {
					frequencyPositions.put(currChar, new HashSet<>());
				}
				frequencyPositions.get(currChar).add(new Point(x, y));

				System.out.println(String.format("Found %d different frequencies", frequencyPositions.size()));
			}
		}

		for (Collection<Point> antennasOfFrequency : frequencyPositions.values()) {
			uniqueAntinodes.addAll(findAllAntinodesForFrequency(inputByLines, antennasOfFrequency));
		}

		return uniqueAntinodes.size();
	}


	/*
	 * +-----+
	 * |.....|
	 * |.A...|
	 * |..A..|
	 * |.....|
	 * |.....|
	 * +-----+
	 */
	private static Set<Point> findAllAntinodesForFrequency(String[] map, Collection<Point> antennas) {
		Set<Point> antinodes = new HashSet<>();
		
		for (Point antenna : antennas) {
			for (Point partnerAntenna : antennas) {
				if (partnerAntenna == antenna) continue;
				Point potentialAntinode = new Point(partnerAntenna);
				potentialAntinode.translate(partnerAntenna.x - antenna.x, partnerAntenna.y - antenna.y);
				if (!isOutOfBounds(map, potentialAntinode)) {
					antinodes.add(potentialAntinode);
				}
			}
		}		
		
		System.out.println(String.format("Found %3d unique, valid antinodes for Frequency %c",
			antinodes.size(),
			map[antennas.iterator().next().y].charAt(antennas.iterator().next().x)));
		return antinodes;
	}

	private static boolean isOutOfBounds(String[] map, Point p) {
		return !(p.x >= 0 && p.x < map[0].length() && p.y >= 0 && p.y < map.length);
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
