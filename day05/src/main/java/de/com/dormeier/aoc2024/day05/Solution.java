package de.com.dormeier.aoc2024.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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

	/**
	 * For Part 1
	 * 
	 * @param allRules
	 * @param update
	 * @return
	 */
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
		Map<Integer, Collection<Integer>> allRules = new HashMap<>();
		List<List<Integer>> allUpdates = new ArrayList<List<Integer>>();

		String[] inputLineByLine = input.split(System.lineSeparator());
		int parseIndex = 0;
		while (!inputLineByLine[parseIndex].equals("")) {
			String[] rule = inputLineByLine[parseIndex].split("\\|");
			
			int predecessor = Integer.parseInt(rule[0]);
			int successor = Integer.parseInt(rule[1]);
			
			if (!allRules.containsKey(predecessor)) {
				allRules.put(predecessor, new HashSet<Integer>());
			}
			allRules.get(predecessor).add(successor);

			++parseIndex;
		}

		++parseIndex; // skip empty line

		while (parseIndex < inputLineByLine.length) {
			String[] update = inputLineByLine[parseIndex++].split(",");
			allUpdates.add(Arrays.stream(update).map(Integer::parseInt).collect(Collectors.toList()));
		}

		int sum = 0;

		for (List<Integer> update : allUpdates) {
			if (!IsUpdateValid(allRules, update)) {
				System.out.println("INVALID update: " + update);
				List<Integer> orderedUpdate = OrderUpdateCorrectly(allRules, update);
				sum += orderedUpdate.get(orderedUpdate.size() / 2);
			} else {
				System.out.println("VALID update: " + update);
			}
		}

		return sum;
	}

	/**
	 * For Part 2
	 * 
	 * @param allRules
	 * @param update
	 * @return
	 */
	private static boolean IsUpdateValid(Map<Integer, Collection<Integer>> allRules, List<Integer> update) {
		for (int currIndex = update.size() - 1; currIndex >= 0; --currIndex) {
			if (!allRules.containsKey(update.get(currIndex)))
				continue; // no rule present for this combination
			for (int indexBeforeIt = currIndex - 1; indexBeforeIt >= 0; --indexBeforeIt) {
				if (allRules.get(update.get(currIndex)).contains(update.get(indexBeforeIt))) {
					return false;
				}
			}
		}

		return true;
	}

	private static List<Integer> OrderUpdateCorrectly(Map<Integer, Collection<Integer>> allRules,
			List<Integer> update) {
		System.out.print("Sorting " + update + " -> ");

		update.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer i, Integer j) {
				if (i == j) {
					return 0;
				}

				if (allRules.containsKey(i)) {
					if (allRules.get(i).contains(j)) {
						return -1;
					}
				}

				return 1;
			}
		});

		System.out.println(update);
		return update;
	}
}
