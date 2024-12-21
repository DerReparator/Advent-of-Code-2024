package de.com.dormeier.aoc2024.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Advent of Code 2024 - Day 04
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

		Object solutionPart1 = null;// solvePart1(inputPart1);
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
		String[] splitLines = input.split(System.lineSeparator());

		int occurrences = 0;
		
		for (int lineIndex = 0; lineIndex < splitLines.length; ++lineIndex) {
			for (int charIndex = 0; charIndex < splitLines[lineIndex].length(); ++charIndex) {
				if (splitLines[lineIndex].charAt(charIndex) == 'X') {
					occurrences += searchHorizontally(splitLines, lineIndex, charIndex);
					occurrences += searchVertically(splitLines, lineIndex, charIndex);
					occurrences += searchDiagonally(splitLines, lineIndex, charIndex);
				}
			}
		}

		return occurrences;
	}

	private static int searchHorizontally(String[] wordSearch, int lineIndex, int charIndex) {
		int ret = 0;
		if (charIndex >= 3) {
			/* search to the left */
			if (wordSearch[lineIndex].substring(charIndex - 3, charIndex + 1).equals("SAMX")) {
				++ret;
			}
		}
		if (charIndex <= wordSearch[lineIndex].length() - 4) {
			/* search to the right */
			if (wordSearch[lineIndex].substring(charIndex, charIndex + 4).equals("XMAS")) {
				++ret;
			}
		}

		if (ret > 0)
		System.out.println("Found %d Horizontal @%4d|%4d".formatted(ret, charIndex, lineIndex));

		return ret;
	}

	private static int searchVertically(String[] wordSearch, int lineIndex, int charIndex) {
		int ret = 0;

		if (lineIndex >= 3) {
			/* search upwards */
			if (wordSearch[lineIndex - 1].charAt(charIndex) == 'M' && wordSearch[lineIndex - 2].charAt(charIndex) == 'A'
					&& wordSearch[lineIndex - 3].charAt(charIndex) == 'S') {
				++ret;
			}
		}

		if (lineIndex <= wordSearch.length - 4) {
			/* search downwards */
			if (wordSearch[lineIndex + 1].charAt(charIndex) == 'M' && wordSearch[lineIndex + 2].charAt(charIndex) == 'A'
					&& wordSearch[lineIndex + 3].charAt(charIndex) == 'S') {
				++ret;
			}
		}

		if (ret > 0)
		System.out.println("Found %d Vertical   @%4d|%4d".formatted(ret, charIndex, lineIndex));

		return ret;
	}

	private static int searchDiagonally(String[] wordSearch, int lineIndex, int charIndex) {
		int ret = 0;

		if (charIndex >= 3) {
			if (lineIndex >= 3) {
				/* search to left upwards */
				if (wordSearch[lineIndex - 1].charAt(charIndex - 1) == 'M'
						&& wordSearch[lineIndex - 2].charAt(charIndex - 2) == 'A'
						&& wordSearch[lineIndex - 3].charAt(charIndex - 3) == 'S') {
					++ret;
				}
			}

			if (lineIndex <= wordSearch.length - 4) {
				/* search left downwards */
				if (wordSearch[lineIndex + 1].charAt(charIndex - 1) == 'M'
						&& wordSearch[lineIndex + 2].charAt(charIndex - 2) == 'A'
						&& wordSearch[lineIndex + 3].charAt(charIndex - 3) == 'S') {
					++ret;
				}
			}
		}

		if (charIndex <= wordSearch[lineIndex].length() - 4) {
			if (lineIndex >= 3) {
				/* search right upwards */
				if (wordSearch[lineIndex - 1].charAt(charIndex + 1) == 'M'
						&& wordSearch[lineIndex - 2].charAt(charIndex + 2) == 'A'
						&& wordSearch[lineIndex - 3].charAt(charIndex + 3) == 'S') {
					++ret;
				}
			}

			if (lineIndex <= wordSearch.length - 4) {
				/* search right downwards */
				if (wordSearch[lineIndex + 1].charAt(charIndex + 1) == 'M'
						&& wordSearch[lineIndex + 2].charAt(charIndex + 2) == 'A'
						&& wordSearch[lineIndex + 3].charAt(charIndex + 3) == 'S') {
					++ret;
				}
			}
		}

		if (ret > 0)
		System.out.println("Found %d Diagonal   @%4d|%4d".formatted(ret, charIndex, lineIndex));

		return ret;
	}

	/**
	 * Solve the second part.
	 * 
	 * @param input The textual input
	 * @return The solution to the second part.
	 */
	protected static Object solvePart2(String input) {
		String[] splitLines = input.split(System.lineSeparator());

		int occurrences = 0;

		for (int lineIndex = 1; lineIndex < splitLines.length - 1; ++lineIndex) {
			for (int charIndex = 1; charIndex < splitLines[lineIndex].length() - 1; ++charIndex) {
				if (splitLines[lineIndex].charAt(charIndex) == 'A') {
					String surrounding = ""
							+ splitLines[lineIndex - 1].charAt(charIndex - 1)
							+ splitLines[lineIndex - 1].charAt(charIndex + 1)
							+ splitLines[lineIndex + 1].charAt(charIndex - 1)
							+ splitLines[lineIndex + 1].charAt(charIndex + 1);

					occurrences += surrounding.equals("MSMS") || surrounding.equals("MMSS")
							|| surrounding.equals("SSMM") || surrounding.equals("SMSM") ? 1 : 0;
				}
			}
		}

		return occurrences;
	}
}
