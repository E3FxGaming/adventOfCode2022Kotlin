package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.Reader
import java.util.stream.Stream

internal class Day02Test {

    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(Day02Test::class.java.getResource("/input/day02.txt").toReader(), 15, 12)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun day02(inputReader: Reader, part1: Int, part2: Int) {
        val day02 = Day02(inputReader)
        assertEquals(part1, day02.part1(), "Part 1")
        assertEquals(part2, day02.part2(), "Part 2")
    }
}
