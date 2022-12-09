package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.Reader
import java.util.stream.Stream

internal class Day09Test {
    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day09Test::class.java.getResource("/input/day09a.txt").toReader(), 13, 1),
            Arguments.of(Day09Test::class.java.getResource("/input/day09b.txt").toReader(), 88, 36)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day08(inputReader: Reader, part1: Int, part2: Int) {
        Day09(inputReader).let {
            assertEquals(part1, it.part1(), "Part 1")
            assertEquals(part2, it.part2(), "Part 2")
        }
    }
}