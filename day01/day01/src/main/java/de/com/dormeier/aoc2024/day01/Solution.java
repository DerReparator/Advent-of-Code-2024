package de.com.dormeier.aoc2024.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Advent of Code 2024 - Day 01
 */
public class Solution
{
	public static void main(String[] args) throws IOException
    {
		if (args.length != 3) {
			System.out.println("Ivalid args. Expected exactly 2 args: pathToInputDay1 pathToInputDay2");
			return;
		}

		String inputPart1 = String.join(System.lineSeparator(), Files.readAllLines(Paths.get(args[1])));
		String inputPart2 = String.join(System.lineSeparator(), Files.readAllLines(Paths.get(args[2])));

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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Solve the second part.
	 * 
	 * @param input The textual input
	 * @return The solution to the second part.
	 */
	protected static Object solvePart2(String input) {
		// TODO Auto-generated method stub
		return null;
	}

}
