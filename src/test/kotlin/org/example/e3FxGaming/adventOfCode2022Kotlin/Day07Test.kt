package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.Reader
import java.util.stream.Stream

internal class Day07Test {
    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day07Test::class.java.getResource("/input/day07.txt").toReader(), 95437L, 24933642L)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day07(inputReader: Reader, part1: Long, part2: Long) {
        Day07(inputReader).let {
            assertEquals(part1, it.part1(), "Part 1")
            assertEquals(part2, it.part2(), "Part 2")
        }
    }
}