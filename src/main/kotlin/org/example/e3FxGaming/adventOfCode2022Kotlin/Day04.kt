package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day04(override val inputReader: Reader) : Day {
    private val elfPairings = inputReader
        .closingReadLines()
        .map { line ->
            line.split(',').map { elfAssignment ->
                elfAssignment.split('-')
                    .map(String::toInt)
                    .let { it[0] to it[1] }
            }.let { elfAssignments ->
                elfAssignments[0] to elfAssignments[1]
            }
        }


    private fun Pair<Int, Int>.containedIn(other: Pair<Int, Int>): Boolean =
        this.first >= other.first && this.second <= other.second

    override fun part1(): Int =
        elfPairings.count {
            it.first.containedIn(it.second) || it.second.containedIn(it.first)
        }

    private fun Pair<Int, Int>.startOrStopInOther(other: Pair<Int, Int>): Boolean =
        (first >= other.first && first <= other.second) || (second >= other.first && second <= other.second)

    override fun part2(): Int =
        elfPairings.count {
            it.first.startOrStopInOther(it.second) || it.second.startOrStopInOther(it.first)
        }
}

fun main() {
    val inputReader = Day04::class.java.getResource("/input/day04.txt").toReader()
    Day04(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}