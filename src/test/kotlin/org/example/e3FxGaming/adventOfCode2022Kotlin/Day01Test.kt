package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.Reader
import java.util.stream.Stream

internal class Day01Test {

    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day01Test::class.java.getResource("/input/day01.txt").toReader(), 24000, 45000)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day01(inputReader: Reader, part1: Int, part2: Int) {
        val day01 = Day01(inputReader)
        assertEquals(part1, day01.part1(), "Part 1")
        assertEquals(part2, day01.part2(), "Part 2")
    }
}
