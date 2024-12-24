package de.com.dormeier.aoc2024.day06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;

/**
 * Tests Day 06 - Part 2.
 */
@TestWithResources
public class TestPart2 {

	@GivenTextResource("day06_part_2_test1.input")
	String inputPart2_1;
	
	@GivenTextResource("day06_part_2_test1.output")
	String expectedPart2_1;

	@Test
	public void TestPart2_1() throws IOException {
		Object result = Solution.solvePart2(inputPart2_1);

		assertEquals(expectedPart2_1, result.toString());
	}

	private static Stream<Arguments> provideMovementTurnLeftParameters() {
		return Stream.of(Arguments.of(Movement.UP, Movement.LEFT), Arguments.of(Movement.RIGHT, Movement.UP),
				Arguments.of(Movement.DOWN, Movement.RIGHT), Arguments.of(Movement.LEFT, Movement.DOWN));

	}

	@ParameterizedTest
	@MethodSource("provideMovementTurnLeftParameters")
	public void movementTurnRight_ShouldReturnCorrectMovement(Movement initialMovement, Movement expectedMovement) {
		assertEquals(expectedMovement, initialMovement.turnLeft());
	}
}