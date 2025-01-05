package de.com.dormeier.aoc2024.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
			if (compactedDiskmap.get(i) == FREE_SPACE) continue;
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
		String diskmap = input;

		List<Integer> compactedDiskmap = compactDiskMapWithoutFragemntation(diskmap);
		
		System.out.println("Compacted, non-fragemented disk map: " + compactedDiskmap);
		
		return calculateFilesystemChecksum(compactedDiskmap);
	}

	public static List<Integer> compactDiskMapWithoutFragemntation(String diskmap) {
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

		System.out.println("Expanded to: " + expandedDiskmap + " with len=" + expandedDiskmap.size());

		for (int currId = idNumber; currId >= 0; --currId) {
			int lastBlockOfCurrId = expandedDiskmap.lastIndexOf(currId);
			if (lastBlockOfCurrId == -1) continue;
			int lengthOfCurrId = 1;
			int currBlock = lastBlockOfCurrId - 1;
			if (currBlock < 0) continue; /* Block already at beginning */
			while (expandedDiskmap.get(currBlock) == currId) {
				++lengthOfCurrId;
				--currBlock;
				if (currBlock < 0) break;
			}
			if (currBlock < 0) break;
			++currBlock; // set it to the last-found (first-ordered) index of the current ID
			//System.out.println(String.format("Found BlockID=%d @%d len=%d", currId,currBlock, lengthOfCurrId));

			Optional<Integer> indexOfFittingFreeSpace = findFreespaceWithAtLeastLength(expandedDiskmap.subList(0, currBlock), lengthOfCurrId);
			
			if (indexOfFittingFreeSpace.isPresent()) {
				/* move block to fitting free space */
				for (int i = indexOfFittingFreeSpace.get(); i < indexOfFittingFreeSpace.get() + lengthOfCurrId; ++i) {
					expandedDiskmap.set(i, currId);
				}

				/* clear block */
				for (int i = currBlock; i < currBlock + lengthOfCurrId; ++i) {
					expandedDiskmap.set(i, FREE_SPACE);
				}
			}

			//System.out.println("After BlockID=" + currId + ": " + expandedDiskmap);
		}

		return expandedDiskmap;
	}

	protected static Optional<Integer> findFreespaceWithAtLeastLength(List<Integer> expandedDiskmap, int requiredLength) {
		int currIndex = expandedDiskmap.indexOf(FREE_SPACE);
		int currLength = 0;

		while (currIndex > -1) {
			for (int i = 0; i < expandedDiskmap.size() - currIndex; ++i) {
				//System.out.println(String.format("Searching @%d +%d", currIndex, i));
				if (expandedDiskmap.get(currIndex + i) == FREE_SPACE) {
					++currLength;
					if (currLength >= requiredLength) {
						//System.out.println("Found free space with len=" + currLength);
						return Optional.of(currIndex);
					}
				}
				else {
					break; /* This free space ended before it was large enough */
				}
			}

			int offsetForFurtherSearch = currIndex + currLength;
			currIndex = expandedDiskmap.subList(offsetForFurtherSearch, expandedDiskmap.size()).indexOf(FREE_SPACE);
			if (currIndex != -1)
				currIndex += offsetForFurtherSearch;
			currLength = 0;
		}

		//System.out.println(String.format("Could not find fitting Free Space with len=%d", requiredLength));
		return Optional.empty();
	}
}
