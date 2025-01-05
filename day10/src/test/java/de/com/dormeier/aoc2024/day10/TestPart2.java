package de.com.dormeier.aoc2024.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;

/**
 * Tests Day 10 - Part 2.
 */
@TestWithResources
public class TestPart2 {

	@GivenTextResource("day10_part_2_test1.input")
	String inputPart2_1;
	
	@GivenTextResource("day10_part_2_test1.output")
	String expectedPart2_1;

	@Test
	public void TestPart2_1() throws IOException {
		Object result = Solution.solvePart2(inputPart2_1);

		assertEquals(expectedPart2_1, result.toString());
	}

	@Test
	public void ShouldIdentifyTrailheadWithRating13Correctly() {
		String input = """
				..90..9
				...1.98
				...2..7
				6543456
				765.987
				876....
				987....
				""";
		Object result = Solution.solvePart2(input);

		assertEquals(13, result);
	}

	@Test
	public void ShouldIdentifyTrailheadWithRating3Correctly() {
		String input = """
				.....0.
				..4321.
				..5..2.
				..6543.
				..7..4.
				..8765.
				..9....
				""";
		Object result = Solution.solvePart2(input);

		assertEquals(3, result);
	}

	@Test
	public void ShouldIdentifyTrailheadWithRating227Correctly() {
		String input = """
				012345
				123456
				234567
				345678
				4.6789
				56789.
				""";
		Object result = Solution.solvePart2(input);

		assertEquals(227, result);
	}
}