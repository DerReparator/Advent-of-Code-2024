package de.com.dormeier.aoc2024.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class TestPart1 {

	@Test
	public void TestPart1_1() throws IOException {

		try (InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("day01_1_test1.input"));
				BufferedReader reader = new BufferedReader(isr)) {
			String input = reader.lines().collect(Collectors.joining(System.lineSeparator()));
			String expected = Files.readString(Paths.get("test/resources/day01_1_test1.output"));

			Object result = Solution.solvePart1(input);

			assertEquals(expected, result.toString());
		}
	}
}