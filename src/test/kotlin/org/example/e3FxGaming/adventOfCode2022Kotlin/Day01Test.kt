package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream

internal class Day01Test {

    companion object {
        @JvmStatic
        fun argumentsProviderPart1(): Stream<Arguments> = Stream.of(
            Arguments.of(Day01Test::class.java.getResource("/input/day01.txt").toFile(), 24000, 45000)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProviderPart1")
    fun day01(inputFile: File, part1: Int, part2: Int) {
        val day01 = Day01(inputFile)
        assertEquals(part1, day01.part1(), "Part 1")
        assertEquals(part2, day01.part2(), "Part 2")
    }
}
