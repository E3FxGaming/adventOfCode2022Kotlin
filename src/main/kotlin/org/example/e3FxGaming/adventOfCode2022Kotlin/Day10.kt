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

    override fun part1(): Int = (19..(xRegAtTick.lastIndex) step 40)
        .sumOf { (it + 1) * xRegAtTick[it] }

    override fun part2(): String =
        xRegAtTick.take(240).mapIndexed { index, xReg ->
            if(index % 40 in (xReg - 1)..(xReg + 1)) '#' else '.'
        }.chunked(40).joinToString("\n") {
            it.joinToString("")
        }.also(::println)
}

fun main() {
    val inputReader = Day10::class.java.getResource("/input/day10.txt").toReader()
    Day10(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}