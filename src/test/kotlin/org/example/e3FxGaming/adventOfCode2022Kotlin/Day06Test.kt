package org.example.e3FxGaming.adventOfCode2022Kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.StringReader
import java.util.stream.Stream

internal class Day06Test {

    companion object {
        @JvmStatic
        fun argumentsProviderPart1(): Stream<Arguments> = Stream.of(
            Arguments.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, 19),
            Arguments.of("bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23),
            Arguments.of("nppdvjthqldpwncqszvftbrmjlhg", 6, 23),
            Arguments.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, 29),
            Arguments.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, 26)
        )
    }

    @ParameterizedTest
    @MethodSource("argumentsProviderPart1")
    fun day06(input: String, part1: Int, part2: Int) {
        val stringReader = StringReader(input)
        val day06 = Day06(stringReader)
        assertEquals(part1, day06.part1())
        assertEquals(part2, day06.part2())
    }
}