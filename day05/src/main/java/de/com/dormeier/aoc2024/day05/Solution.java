package de.com.dormeier.aoc2024.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Advent of Code 2024 - Day 05
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
		/* TODO your solution for Part 1 goes here. */
		List<Entry<Integer, Integer>> allRules = new ArrayList<Entry<Integer, Integer>>();
		List<List<Integer>> allUpdates = new ArrayList<List<Integer>>();
		
		String[] inputLineByLine = input.split(System.lineSeparator());
		int parseIndex = 0;
		while (!inputLineByLine[parseIndex].equals("")) {
			String[] rule = inputLineByLine[parseIndex].split("\\|");
			allRules.add(Map.entry(Integer.parseInt(rule[0]), Integer.parseInt(rule[1])));
			++parseIndex;
		}
		
		++parseIndex; // skip empty line
		
		while (parseIndex < inputLineByLine.length) {
			String[] update = inputLineByLine[parseIndex++].split(",");
			allUpdates.add(Arrays.stream(update).map(Integer::parseInt).collect(Collectors.toUnmodifiableList()));
		}

		int sum = 0;

		for (List<Integer> update : allUpdates) {
			if (IsUpdateValid(allRules, update)) {
				sum += update.get(update.size() / 2);
			}
			else {
				System.out.println("Invalid update: " + update);
			}
		}

		return sum;
	}

	private static boolean IsUpdateValid(List<Entry<Integer, Integer>> allRules, List<Integer> update) {
		for (int currIndex = update.size() - 1; currIndex >= 0; --currIndex) {
			for (int indexBeforeIt = currIndex - 1; indexBeforeIt >= 0; --indexBeforeIt) {
				for (int ruleNr = 0; ruleNr < allRules.size(); ++ruleNr) {
					if (allRules.get(ruleNr).getKey() == update.get(currIndex)
							&& allRules.get(ruleNr).getValue() == update.get(indexBeforeIt)) {
						return false;
					}
				}
			}
		}

		return true;
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
