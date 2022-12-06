package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day01(override val inputReader: Reader) : Day {
    private val elfCalories = inputReader.closingReadText().trim()
        .split(System.lineSeparator().repeat(2))
        .map { elf ->
            elf.split(System.lineSeparator()).map(String::toInt).sum()
        }

    override fun part1(): Int = elfCalories.max()

    override fun part2(): Int = elfCalories.sortedDescending().take(3).sum()
}

fun main() {
    val inputReader = Day01::class.java.getResource("/input/day01.txt").toReader()
    Day01(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}