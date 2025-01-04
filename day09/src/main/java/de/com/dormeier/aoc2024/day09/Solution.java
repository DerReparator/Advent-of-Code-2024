package de.com.dormeier.aoc2024.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent of Code 2024 - Day 09
 */
public class Solution
{
	public static final int FREE_SPACE = -1;

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
		String diskmap = input;

		List<Integer> compactedDiskmap = compactDiskMap(diskmap);
		return calculateFilesystemChecksum(compactedDiskmap);
	}

	public static long calculateFilesystemChecksum(List<Integer> compactedDiskmap) {
		long checksum = 0;

		for (int i = 0; i < compactedDiskmap.size(); ++i) {
			checksum += i * compactedDiskmap.get(i);
		}

		return checksum;
	}

	public static List<Integer> compactDiskMap(String diskmap) {
		List<Integer> expandedDiskmap = new ArrayList<>();
		int idNumber = 0;

		for (int diskmapIndex = 0; diskmapIndex < diskmap.length(); ++diskmapIndex) {
			if (diskmapIndex % 2 == 0) {
				/* file length */
				for (int j = 0; j < Integer.parseInt("" + diskmap.charAt(diskmapIndex)); ++j) {
					expandedDiskmap.add(idNumber);
				}
				++idNumber;
			} else {
				/* free space */
				for (int j = 0; j < Integer.parseInt("" + diskmap.charAt(diskmapIndex)); ++j) {
					expandedDiskmap.add(FREE_SPACE);
				}
			}
		}

		System.out.println("Expanded to: " + expandedDiskmap);

		while (expandedDiskmap.indexOf(FREE_SPACE) > -1) {
			expandedDiskmap.set(expandedDiskmap.indexOf(FREE_SPACE), expandedDiskmap.removeLast());
		}

		return expandedDiskmap;
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
