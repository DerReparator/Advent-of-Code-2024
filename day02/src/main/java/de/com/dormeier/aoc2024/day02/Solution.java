package de.com.dormeier.aoc2024.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Advent of Code 2024 - Day 02
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
		int safeReports = 0;

		for (String report : input.split(System.lineSeparator())) {
			if (IsPart1ReportSafe(Arrays.stream(report.split(" ")).mapToInt(Integer::parseInt).boxed().toList())) {
				++safeReports;
			}
		}

		return safeReports;
	}

	private static boolean IsPart1ReportSafe(List<Integer> report) {
		int factor = 1; /* based on increasing report or decreasing report */
		if (report.get(0) == report.get(1))
			return false;
		if (report.get(0) > report.get(1))
			factor = -1;

		for (int i = 1; i < report.size(); ++i) {
			int diff = (report.get(i) - report.get(i - 1)) * factor;
			if (diff < 1 || diff > 3) {
				return false;
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
		int safeReports = 0;

		for (String report : input.split(System.lineSeparator())) {
			if (IsPart2ReportSafe(
							Arrays.stream(report.split(" ")).mapToInt(Integer::parseInt)
							.boxed().toList())) {
				++safeReports;
			}
		}

		return safeReports;
	}

	private static boolean IsPart2ReportSafe(List<Integer> report) {
		int factor = 1; /* based on increasing report or decreasing report */
		if (report.get(0) > report.get(1))
			factor = -1;

		for (int i = 1; i < report.size(); ++i) {
			int diff = (report.get(i) - report.get(i - 1)) * factor;
			if (diff < 1 || diff > 3 || report.get(0) == report.get(1)) {
				for (int j = 0; j < report.size(); ++j) {
					List<Integer> dampenedReport = new ArrayList<Integer>(report);
					dampenedReport.remove(j);
					if (IsPart1ReportSafe(dampenedReport)) {
						System.out.println("Removed index " + j + " which made the report safe.");
						return true;
					}
				}
				System.out.println("Could not make "
						+ report.stream().map(integer -> integer.toString()).collect(Collectors.joining(","))
						+ " safe.");
				return false; // even with Dampener, the report is not safe
			}
		}

		return true;
	}
}
