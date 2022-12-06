package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day02(override val inputReader: Reader) : Day {
    private val charToHandsign = mutableMapOf(
        'A' to Handsign.ROCK,
        'B' to Handsign.PAPER,
        'C' to Handsign.SCISSORS,
        'X' to Handsign.ROCK,
        'Y' to Handsign.PAPER,
        'Z' to Handsign.SCISSORS
    )

    private val games = inputReader.closingReadLines().map { line ->
        line.split(' ')
            .let { charToHandsign.getValue(it.component1().first()) to it.component2().first() }
    }

    override fun part1(): Any = games.sumOf {
        val secondSign = charToHandsign.getValue(it.second)
        secondSign.against(it.first) + secondSign.signValue
    }

    override fun part2(): Any = games.sumOf {
        val ownSign = it.first.outcomeToOwnSign(it.second)
        ownSign.against(it.first) + ownSign.signValue
    }

    private enum class Handsign(val signValue: Int) {
        ROCK(1) {
            override fun against(other: Handsign): Int = when (other) {
                ROCK -> 3
                PAPER -> 0
                SCISSORS -> 6
            }

            override fun outcomeToOwnSign(outcome: Char): Handsign = when(outcome) {
                'X' -> SCISSORS //lose
                'Y' -> ROCK //draw
                'Z' -> PAPER //win
                else -> throw IllegalStateException("Illegal outcome")
            }
        },
        PAPER(2) {
            override fun against(other: Handsign): Int = when (other) {
                ROCK -> 6
                PAPER -> 3
                SCISSORS -> 0
            }

            override fun outcomeToOwnSign(outcome: Char): Handsign = when(outcome) {
                'X' -> ROCK //lose
                'Y' -> PAPER //draw
                'Z' -> SCISSORS //win
                else -> throw IllegalStateException("Illegal outcome")
            }
        },
        SCISSORS(3) {
            override fun against(other: Handsign): Int = when (other) {
                ROCK -> 0
                PAPER -> 6
                SCISSORS -> 3
            }

            override fun outcomeToOwnSign(outcome: Char): Handsign = when(outcome) {
                'X' -> PAPER //lose
                'Y' -> SCISSORS //draw
                'Z' -> ROCK //win
                else -> throw IllegalStateException("Illegal outcome")
            }
        };

        abstract fun against(other: Handsign): Int

        abstract fun outcomeToOwnSign(outcome: Char): Handsign
    }
}

fun main() {
    val inputReader = Day02::class.java.getResource("/input/day02.txt").toReader()
    Day02(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}