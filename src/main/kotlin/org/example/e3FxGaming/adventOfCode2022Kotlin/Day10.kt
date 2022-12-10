package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day10(override val inputReader: Reader) : Day {
    private val xRegAtTick: List<Int> = buildList {
        add(1)
        inputReader.closingReadLines().forEach {
            add(last())
            it.substringAfter("addx ", "").let { match ->
                if (match.isNotEmpty()) add(last() + match.toInt())
            }
        }
    }

    override fun part1(): Int = xRegAtTick.foldIndexed(0) { index, acc, i ->
        val cycle = index + 1
        if ((cycle - 20) % 40 == 0) acc + (cycle * i) else acc
    }

    override fun part2() {
        val displayGrid = Array(6) { rowIndex ->
            CharArray(40) { colIndex ->
                val cycle = (rowIndex * 40) + colIndex + 1
                val xReg = xRegAtTick[cycle - 1]
                if (colIndex in (xReg - 1)..(xReg + 1)) '#' else '.'
            }
        }

        displayGrid.joinToString("\n") {
            it.joinToString("")
        }.let(::println)
    }
}

fun main() {
    val inputReader = Day10::class.java.getResource("/input/day10.txt").toReader()
    Day10(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}