package de.com.dormeier.aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Advent of Code 2024 - Day 07
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
		String[] inputLineByLine = input.split(System.lineSeparator());
      
		long sum = 0;

		for (String line : inputLineByLine) {
			Optional<Long> solution = isEquationSolvable(
				Long.parseLong(line.split(": ")[0]),
				Arrays.asList(line.split(": ")[1].split(" "))
				.stream().map(Long::parseLong).collect(Collectors.toList()));
			if (solution.isPresent()) {
				sum += solution.get();
			}
		}

        return sum;
	}

	private static Optional<Long> isEquationSolvable(long requiredSum, List<Long> terms) {
		long bitmask = -1;
		while (++bitmask < (long)Math.pow(2, terms.size() - 1) ) {
			long result = calculateEquation(terms, bitmask);
			if (result == requiredSum) {
				return Optional.of(result);
			}
		}
		return Optional.empty();
	}

	private static long calculateEquation(List<Long> terms, long operators) {
		long result = terms.get(0);
		for (int i = 0; i < terms.size() - 1; ++i) {
			if (((operators >> i) & 1) == 0) {
				result += terms.get(i + 1);
			}
			else {
				result *= terms.get(i + 1);
			}
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
		/* TODO your solution for Part 2 goes here. */        
        return "";
	}
}
