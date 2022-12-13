package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.Reader
import java.util.stream.Stream

class Day13Test {

    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day13Test::class.java.getResource("/input/day13.txt").toReader(), 13, 140)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day13(inputReader: Reader, part1: Int, part2: Int) {
        Day13(inputReader).let {
            assertEquals(part1, it.part1(), "Part 1")
            assertEquals(part2, it.part2(), "Part 2")
        }
    }
}