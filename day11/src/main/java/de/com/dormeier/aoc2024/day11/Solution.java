package de.com.dormeier.aoc2024.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

		// Object solutionPart1 = solvePart1(inputPart1, BLINKS_PART1);
		Object solutionPart2 = solvePart2(inputPart2);

//		System.out.println("Solution Part 1: " + solutionPart1);
		System.out.println("Solution Part 2: " + solutionPart2);
    }

	protected static final int BLINKS_PART1 = 25;
	protected static final int BLINKS_PART2 = 75;

    /**
	 * Solve the first part.
	 * 
	 * @param input The textual input
	 * @return The solution to the first part.
	 */
	protected static Object solvePart1(String input, int blinks) {
		List<Long> stones = Arrays.stream(input.strip().split(" ")).map((s) -> Long.parseLong(s))
				.collect(Collectors.toList());

		for (int blink = 0; blink < blinks; blink++) {
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

			System.out.println(String.format("%10s @ %2d / %2d", input, blink, blinks));

			// System.out.println("Stones: " + stones.size());
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
		String[] stones = input.strip().split(" ");

		System.out.println("the stones for Part 2: " + stones);

		ExecutorService threadPool = Executors.newFixedThreadPool(stones.length);

		long totalSize = 0;

		List<Future<Long>> threads = new ArrayList<Future<Long>>(stones.length);

		for (String stone : stones) {
			threads.add(threadPool.submit(new Callable<Long>() {
				@Override
				public Long call() throws Exception {
					System.out.println("I will execute the stones for " + stone);
					Long result = (Long) solvePart1(stone, BLINKS_PART2);
					System.out.println("Done executing stones for " + stone);
					return result;
				}
			}));
		}

		for (Future<Long> completed : threads) {
			try {
				totalSize += completed.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		threadPool.shutdown();

		return totalSize;
	}
}
