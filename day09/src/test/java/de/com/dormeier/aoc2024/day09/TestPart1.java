package de.com.dormeier.aoc2024.day09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;

/**
 * Tests Day 09 - Part 1.
 */
@TestWithResources
public class TestPart1 {

	@GivenTextResource("day09_part_1_test1.input")
	String inputPart1_1;
	
	@GivenTextResource("day09_part_1_test1.output")
	String expectedPart1_1;

	@Test
	public void TestPart1_1() throws IOException {
		Object result = Solution.solvePart1(inputPart1_1);

		assertEquals(expectedPart1_1, result.toString());
	}

	@Test
	public void TestCompactDiskmap() {
		String expected = "0099811188827773336446555566"; /* taken from adventofcode.com */
		var result = Solution.compactDiskMap(inputPart1_1);

		assertEquals(expected, String.join("", result.stream().map(i -> i.toString()).toList()));
	}
}