package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.Reader
import java.util.stream.Stream

class Day15Test {
    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day15Test::class.java.getResource("/input/day15.txt").toReader(), 26, 56000011L)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day15(inputReader: Reader, part1: Int, part2: Long) {
        Day15(inputReader, 10, 20).let {
            assertEquals(part1, it.part1(), "Part 1")
            assertEquals(part2, it.part2(), "Part 2")
        }
    }
}