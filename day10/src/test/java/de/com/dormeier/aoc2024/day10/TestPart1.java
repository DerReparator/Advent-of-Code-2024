package de.com.dormeier.aoc2024.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;

/**
 * Tests Day 10 - Part 1.
 */
@TestWithResources
public class TestPart1 {

	@GivenTextResource("day10_part_1_test1.input")
	String inputPart1_1;
	
	@GivenTextResource("day10_part_1_test1.output")
	String expectedPart1_1;

	@Test
	public void TestPart1_1() throws IOException {
		Object result = Solution.solvePart1(inputPart1_1);

		assertEquals(expectedPart1_1, result.toString());
	}

	@Test
	public void ShouldIdentifyTrailheadWithScore1Correctly() {
		String input = """
				0123
				1234
				8765
				9876
				""";
		Object result = Solution.solvePart1(input);

		assertEquals(1, result);
	}

	@Test
	public void ShouldIdentifyTrailheadWithScore2Correctly() {
		String input = """
				...0...
				...1...
				...2...
				6543456
				7.....7
				8.....8
				9.....9
				""";
		Object result = Solution.solvePart1(input);

		assertEquals(2, result);
	}

	@Test
	public void ShouldIdentifyTrailheadWithScore4Correctly() {
		String input = """
				..90..9
				...1.98
				...2..7
				6543456
				765.987
				876....
				987....
				""";
		Object result = Solution.solvePart1(input);

		assertEquals(4, result);
	}

	@Test
	public void ShouldCalculateCorrectScoreFor2Trailheads() {
		String input = """
				10..9..
				2...8..
				3...7..
				4567654
				...8..3
				...9..2
				.....01
				""";
		Object result = Solution.solvePart1(input);

		assertEquals(3, result);
	}
}