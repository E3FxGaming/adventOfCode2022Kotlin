package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day06(override val inputReader: Reader) : Day {
    private val fileContent = inputReader.closingReadText()

    override fun part1(): Int =
        fileContent.windowedSequence(4).indexOfFirst {
            it.toSet().size == 4
        } + 4

    override fun part2(): Int =
        fileContent.windowedSequence(14).indexOfFirst {
            it.toSet().size == 14
        } + 14

}

fun main() {
    val inputReader = Day06::class.java.getResource("/input/day06.txt").toReader()
    Day06(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}