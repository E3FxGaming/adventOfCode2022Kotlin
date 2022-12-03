package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream

internal class Day03Test {
    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day03Test::class.java.getResource("/input/day03.txt").toFile(), 157, 70)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day03(inputFile: File, part1: Int, part2: Int) {
        Day03(inputFile).let {
            assertEquals(part1, it.part1(), "Part 1")
            assertEquals(part2, it.part2(), "Part 2")
        }
    }
}