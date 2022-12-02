package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.File

class Day01(override val inputFile: File) : Day {
    private val elfCalories = inputFile.readText().trim()
        .split(System.lineSeparator().repeat(2))
        .map { elf ->
            elf.split(System.lineSeparator()).map(String::toInt).sum()
        }

    override fun part1(): Int = elfCalories.max()

    override fun part2(): Int = elfCalories.sortedDescending().take(3).sum()
}

fun main() {
    val inputFile = Day01::class.java.getResource("/input/day01.txt").toFile()
    Day01(inputFile).let {
        println(it.part1())
        println(it.part2())
    }
}