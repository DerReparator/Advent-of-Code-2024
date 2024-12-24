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
 * Tests Day 06 - Part 1.
 */
@TestWithResources
public class TestPart1 {

	@GivenTextResource("day06_part_1_test1.input")
	String inputPart1_1;
	
	@GivenTextResource("day06_part_1_test1.output")
	String expectedPart1_1;

	@Test
	public void TestPart1_1() throws IOException {
		Object result = Solution.solvePart1(inputPart1_1);

		assertEquals(expectedPart1_1, result.toString());
	}

	private static Stream<Arguments> provideMovementTurnRightParameters() {
		return Stream.of(Arguments.of(Movement.UP, Movement.RIGHT), Arguments.of(Movement.RIGHT, Movement.DOWN),
				Arguments.of(Movement.DOWN, Movement.LEFT), Arguments.of(Movement.LEFT, Movement.UP));

	}

	@ParameterizedTest
	@MethodSource("provideMovementTurnRightParameters")
	public void movementTurnRight_ShouldReturnCorrectMovement(Movement initialMovement, Movement expectedMovement) {
		assertEquals(expectedMovement, initialMovement.turnRight());
	}
}