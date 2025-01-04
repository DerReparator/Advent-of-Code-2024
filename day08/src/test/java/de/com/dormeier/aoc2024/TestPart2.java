package de.com.dormeier.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;

/**
 * Tests Day 08 - Part 2.
 */
@TestWithResources
public class TestPart2 {

	@GivenTextResource("day08_part_2_test1.input")
	String inputPart2_1;
	
	@GivenTextResource("day08_part_2_test1.output")
	String expectedPart2_1;

	@Test
	public void TestPart2_1() throws IOException {
		Object result = Solution.solvePart2(inputPart2_1);

		assertEquals(expectedPart2_1, result.toString());
	}
}