package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.Reader
import java.util.stream.Stream

class Day11Test {
    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day11Test::class.java.getResource("/input/day11.txt").toReader(), 10605L, 2713310158L)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day10(inputReader: Reader, part1: Long, part2: Long) {
        Day11(inputReader).let {
            assertEquals(part1, it.part1(), "Part 1")
            assertEquals(part2, it.part2(), "Part 2")
        }
    }
}