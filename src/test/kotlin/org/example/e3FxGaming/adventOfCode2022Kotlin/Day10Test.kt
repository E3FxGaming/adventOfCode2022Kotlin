package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.Reader
import java.util.stream.Stream

internal class Day10Test {
    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day10Test::class.java.getResource("/input/day10.txt").toReader(), 13140, 1)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day10(inputReader: Reader, part1: Int, part2: Int) {
        Day10(inputReader).let {
            assertEquals(part1, it.part1(), "Part 1")
            it.part2()
            //assertEquals(part2, it.part2(), "Part 2")
        }
    }
}