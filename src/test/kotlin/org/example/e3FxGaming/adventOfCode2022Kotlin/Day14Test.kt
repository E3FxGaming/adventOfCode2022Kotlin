package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.Reader
import java.util.stream.Stream

class Day14Test {

    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day14Test::class.java.getResource("/input/day14.txt").toReader(), 24, 93)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day14(inputReader: Reader, part1: Int, part2: Int) {
        Day14(inputReader).let {
            assertEquals(part1, it.part1(), "Part 1")
            assertEquals(part2, it.part2(), "Part 2")
        }
    }
}