package de.com.dormeier.aoc2024.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Advent of Code 2024 - Day 11
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

	private static final int BLINKS = 25;

    /**
	 * Solve the first part.
	 * 
	 * @param input The textual input
	 * @return The solution to the first part.
	 */
	protected static Object solvePart1(String input) {
		List<Long> stones = Arrays.stream(input.strip().split(" ")).map((s) -> Long.parseLong(s))
				.collect(Collectors.toList());

		System.out.println("Stones: " + stones.size());

		for (int blink = 0; blink < BLINKS; blink++) {
			for (int index = stones.size() - 1; index >= 0; --index) {
				if (stones.get(index) == 0) {
					stones.set(index, 1L);
					continue;
				}
				String value = "" + stones.get(index);
				if ( value.length() % 2 == 0) {
					stones.set(index, Long.parseLong(value.substring(0, value.length() / 2)));
					stones.add(index + 1, Long.parseLong(value.substring(value.length() / 2)));
					continue;
				}
				stones.set(index, stones.get(index) * 2024L);
			}
			System.out.println("Stones: " + stones.size());
		}

		return stones.size();
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
