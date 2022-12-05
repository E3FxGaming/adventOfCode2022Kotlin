package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.File
import java.util.LinkedList

class Day05(override val inputFile: File) : Day {
    private val cratesConfig: List<List<Char>>
    private val instructions: List<Triple<Int, Int, Int>>

    init {
        var cratesRead = false
        val prepareCratesConfig: MutableMap<Int, MutableList<Char>> = mutableMapOf()
        val prepareInstructions = mutableListOf<Triple<Int, Int, Int>>()

        val instructionRegex = "^move (\\d+) from (\\d+) to (\\d+)\$".toRegex()

        inputFile.readLines().forEach { line ->
            when {
                line.isBlank() -> cratesRead = true //switch from crate mode to instruction mode
                !cratesRead -> { //parse crate line
                    line.chunked(4) { segment ->
                        if(segment[0] == '[') {
                            segment[1]
                        } else null
                    }.forEachIndexed { index, c ->
                        if(c != null) {
                            prepareCratesConfig.computeIfAbsent(index) { mutableListOf() }
                                .add(c)
                        }
                    }
                }
                else -> { // parse instruction
                    instructionRegex.find(line)?.let { result ->
                        result.groupValues.slice(1..3).map { it.toInt() }.let {
                            Triple(it[0], it[1]-1, it[2]-1)
                        }.let(prepareInstructions::add)
                    }
                }
            }
        }

        this.cratesConfig =  List(prepareCratesConfig.keys.max() + 1) {
            prepareCratesConfig.getOrDefault(it, mutableListOf()).reversed()
        }

        this.instructions = prepareInstructions

    }


    override fun part1(): String {
        val localCratesConfig = List(cratesConfig.size) {
            LinkedList(cratesConfig[it])
        }

        instructions.forEach { (times, from, to) ->
            repeat(times) {
                localCratesConfig[to].add(localCratesConfig[from].removeLast())
            }
        }

        return localCratesConfig.mapNotNull { it.lastOrNull() }.joinToString("")
    }

    override fun part2(): String {
        val localCratesConfig = List(cratesConfig.size) {
            LinkedList(cratesConfig[it])
        }

        instructions.forEach { (size, from, to) ->
            val movedCrates = LinkedList<Char>()
            repeat(size) {
                movedCrates.addFirst(localCratesConfig[from].removeLast())
            }
            localCratesConfig[to].addAll(movedCrates)
        }

        return localCratesConfig.mapNotNull { it.lastOrNull() }.joinToString("")
    }
}

fun main() {
    val inputFile = Day05::class.java.getResource("/input/day05.txt").toFile()
    Day05(inputFile).let {
        println(it.part1())
        println(it.part2())
    }
}