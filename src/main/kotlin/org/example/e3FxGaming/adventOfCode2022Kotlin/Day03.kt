package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.File

class Day03(override val inputFile: File) : Day {
    private val rucksackData = inputFile.readLines().map {
        val splitAt = it.length / 2
        it.substring(0, splitAt) to it.substring(splitAt)
    }

    private val priorities = buildMap<Char, Int> {
        for(c in 'a'..'z')
            set(c, c - 'a' + 1)

        for(c in 'A'..'Z')
            set(c, c - 'A' + 27)
    }

    override fun part1(): Int =
        rucksackData.sumOf {
            priorities.getValue(it.first.toSet().intersect(it.second.toSet()).first())
        }

    override fun part2(): Int =
        rucksackData.windowed(3,3).sumOf { group ->
            group.map {
                it.first.toSet() + it.second.toSet()
            }.reduce { acc, chars ->
                acc.intersect(chars)
            }.first().let { priorities.getValue(it) }
        }
}

fun main() {
    val inputFile = Day03::class.java.getResource("/input/day03.txt").toFile()
    Day03(inputFile).let {
        println(it.part1())
        println(it.part2())
    }
}