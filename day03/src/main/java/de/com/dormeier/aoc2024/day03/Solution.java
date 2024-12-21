package de.com.dormeier.aoc2024.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Advent of Code 2024 - Day 03
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

    private static final String PART_1_REGEX = "mul\\(\\d{1,3},\\d{1,3}\\)";
    
    /**
	 * Solve the first part.
	 * 
	 * @param input The textual input
	 * @return The solution to the first part.
	 */
	protected static Object solvePart1(String input) {
		Pattern part1Pattern = Pattern.compile(PART_1_REGEX);
		Matcher m = part1Pattern.matcher(input);
		
		int sum = 0;
		
		while (m.find()) {
			sum += parseAndCalculateMulInstruction(m.group());
		}
		return sum;
	}

	private static int parseAndCalculateMulInstruction(String mulInstr) {
		String numbers = mulInstr.substring("mul(".length(), mulInstr.length() - 1);
		String[] splitNumbers = numbers.split(",");

		return Integer.parseInt(splitNumbers[0]) * Integer.parseInt(splitNumbers[1]);
	}

	/**
	 * Solve the second part.
	 * 
	 * @param input The textual input
	 * @return The solution to the second part.
	 */
	protected static Object solvePart2(String input) {
		// pre process the do / don't instructions and then run Part1
		String removedDonts = input.replaceAll("don't\\(\\).*do\\(\\)", "");

		return solvePart1(removedDonts);
	}
}
