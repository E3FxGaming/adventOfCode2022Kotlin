package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day10(override val inputReader: Reader) : Day {
    private sealed interface Instruction {
        object Noop: Instruction

        class AddX(val by: Int): Instruction
    }

    private val instructions = inputReader
        .closingReadLines()
        .map {line ->
            if(line == "noop") Instruction.Noop else
                Instruction.AddX(line.split(' ')[1].toInt())
        }

    private val xRegAtTick = instructions.flatMap {
        when(it) {
            is Instruction.Noop -> listOf(it)
            is Instruction.AddX -> listOf(Instruction.Noop, it)
        }
    }.runningFold(1) { xReg, instruction ->
        if(instruction is Instruction.AddX) {
            xReg + instruction.by
        } else xReg
    }

    override fun part1(): Int = xRegAtTick.foldIndexed(0) { index, acc, i ->
            val cycle = index + 1
            if((cycle-20) % 40 == 0) acc + (cycle*i) else acc
        }

    override fun part2(): Any {
        val displayGrid = Array(6) { rowIndex ->
            CharArray(40) {colIndex ->
                val cycle = (rowIndex * 40) + colIndex+1
                val xReg = xRegAtTick[cycle-1]
                if(colIndex in (xReg-1)..(xReg+1)) '#' else '.'
            }
        }

        println(displayGrid.joinToString("\n") {
            it.joinToString("")
        })

        return Unit
    }
}

fun main() {
    val inputReader = Day10::class.java.getResource("/input/day10.txt").toReader()
    Day10(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}