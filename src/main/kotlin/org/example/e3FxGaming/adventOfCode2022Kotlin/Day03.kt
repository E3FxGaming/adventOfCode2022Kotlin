package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.File

class Day03(override val inputFile: File) : Day {
    private val rucksackData = inputFile.readLines()

    private val priorities: Map<Char, Int> = buildMap {
        for (c in 'a'..'z')
            set(c, c - 'a' + 1)

        for (c in 'A'..'Z')
            set(c, c - 'A' + 27)
    }

    override fun part1(): Int =
        rucksackData.sumOf {
            val splitAt = it.length / 2
            val commonChar = it.take(splitAt).toSet()
                .intersect(it.substring(splitAt).toSet()).first()
            priorities.getValue(commonChar)
        }

    override fun part2(): Int =
        rucksackData.windowed(3, 3).sumOf { group ->
            group.map(String::toSet)
                .reduce(Set<Char>::intersect)
                .first()
                .let(priorities::getValue)
        }
}

fun main() {
    val inputFile = Day03::class.java.getResource("/input/day03.txt").toFile()
    Day03(inputFile).let {
        println(it.part1())
        println(it.part2())
    }
}