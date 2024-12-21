package de.com.dormeier.aoc2024.day04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;

/**
 * Tests Day 04 - Part 1.
 */
@TestWithResources
public class TestPart1 {

	@GivenTextResource("day04_part_1_test1.input")
	String inputPart1_1;
	
	@GivenTextResource("day04_part_1_test1.output")
	String expectedPart1_1;

	@GivenTextResource("day04_part_1_test2.input")
	String inputPart1_2;

	@GivenTextResource("day04_part_1_test2.output")
	String expectedPart1_2;

	@Test
	public void TestPart1_1() throws IOException {
		Object result = Solution.solvePart1(inputPart1_1);

		assertEquals(expectedPart1_1, result.toString());
	}

	@Test
	public void TestPart1_2() throws IOException {
		Object result = Solution.solvePart1(inputPart1_2);

		assertEquals(expectedPart1_2, result.toString());
	}
}