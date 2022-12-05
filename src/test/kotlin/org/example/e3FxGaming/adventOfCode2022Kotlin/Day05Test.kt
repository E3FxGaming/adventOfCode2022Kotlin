package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream

internal class Day05Test {
    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day05Test::class.java.getResource("/input/day05.txt").toFile(), "CMZ", "MCD")
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day05(inputFile: File, part1: String, part2: String) {
        Day05(inputFile).let {
            assertEquals(part1, it.part1(), "Part 1")
            assertEquals(part2, it.part2(), "Part 2")
        }
    }
}