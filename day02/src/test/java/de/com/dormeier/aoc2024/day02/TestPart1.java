package de.com.dormeier.aoc2024.day02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;

/**
 * Tests Day 02 - Part 1.
 */
@TestWithResources
public class TestPart1 {

	@GivenTextResource("day02_part_1_test1.input")
	String inputPart1_1;
	
	@GivenTextResource("day02_part_1_test1.output")
	String expectedPart1_1;

	@Test
	public void TestPart1_1() throws IOException {
		Object result = Solution.solvePart1(inputPart1_1);

		assertEquals(expectedPart1_1, result.toString());
	}
}