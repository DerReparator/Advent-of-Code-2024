package de.com.dormeier.aoc2024.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent of Code 2024 - Day 01
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
		List<Integer> firstList = new ArrayList<Integer>();
		List<Integer> secondList = new ArrayList<Integer>();
		
		for (String line : input.lines().toArray(String[]::new)) {
			String[] splitLine = line.split("   ");
			
			firstList.add(Integer.parseInt(splitLine[0]));
			secondList.add(Integer.parseInt(splitLine[1]));
		}

		firstList.sort((i1, i2) -> Integer.compare(i1, i2));
		secondList.sort((i1, i2) -> Integer.compare(i1, i2));

		int result = 0;

		for (int i = 0; i < firstList.size(); ++i) {
			result += Math.abs(firstList.get(i) - secondList.get(i));
		}

		return result;
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
