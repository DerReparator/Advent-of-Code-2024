package de.com.dormeier.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.awt.Point;

import org.junit.jupiter.api.Test;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;

/**
 * Tests Day 08 - Part 1.
 */
@TestWithResources
public class TestPart1 {

	@GivenTextResource("day08_part_1_test1.input")
	String inputPart1_1;
	
	@GivenTextResource("day08_part_1_test1.output")
	String expectedPart1_1;

	@Test
	public void TestPart1_1() throws IOException {
		Object result = Solution.solvePart1(inputPart1_1);

		assertEquals(expectedPart1_1, result.toString());
	}

	@GivenTextResource("day08_part_1_testEasy.input")
	String inputPart1Easy;
	
	@GivenTextResource("day08_part_1_testEasy.output")
	String expectedPart1Easy;

	@Test
	public void TestPart1Easy() throws IOException {
		Object result = Solution.solvePart1(inputPart1Easy);

		assertEquals(expectedPart1Easy, result.toString());
	}

	@GivenTextResource("day08_part_1_testOutOfMap.input")
	String inputPart1OutOfMap;
	
	@GivenTextResource("day08_part_1_testOutOfMap.output")
	String expectedPart1OutOfMap;

	@Test
	public void TestPart1OutOfMap() throws IOException {
		Object result = Solution.solvePart1(inputPart1OutOfMap);

		assertEquals(expectedPart1OutOfMap, result.toString());
	}

	@Test
	public void PointShouldBeHashable() {
		Point pointA = new Point(1, 2);
		Point pointB = new Point(3, 4);
		Point pointC = new Point(1, 2);

		Set<Point> points = new HashSet<>();

		points.add(pointA);
		points.add(pointB);
		points.add(pointC);

		assertEquals(2, points.size());
	}
}