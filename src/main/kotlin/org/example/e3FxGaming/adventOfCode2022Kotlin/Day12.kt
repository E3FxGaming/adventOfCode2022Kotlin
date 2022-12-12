package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day12(override val inputReader: Reader) : Day {
    private val field: Array<CharArray> = inputReader.closingReadLines()
        .map(String::toCharArray).toTypedArray()

    private val goalPair: Pair<Int, Int> = kotlin.run {
        val goalRow = field.indexOfFirst { it.contains('E') }
        val goalCol = field[goalRow].indexOfFirst { it == 'E' }
        goalRow to goalCol
    }

    private val dirs = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

    override fun part1(): Int {
        val startingRow = field.indexOfFirst { it.contains('S') }
        val startingCol = field[startingRow].indexOfFirst { it == 'S' }

        return goHiking(setOf(startingRow to startingCol))
    }

    override fun part2(): Int {
        val startingSet = buildSet {
            field.forEachIndexed { rowIndex, line ->
                line.forEachIndexed { colIndex, elevation ->
                    if (elevation == 'a') add(rowIndex to colIndex)
                }
            }
        }

        return goHiking(startingSet)
    }

    private fun goHiking(startingToDo: Set<Pair<Int, Int>>): Int {
        val visited = Array(field.size) {
            BooleanArray(field.first().size)
        }

        startingToDo.forEach { (startingRow, startingCol) ->
            visited[startingRow][startingCol] = true
        }

        val rowIndices = field.indices
        val colIndices = field.first().indices

        var steps = 0
        var toDo: Set<Pair<Int, Int>> = startingToDo

        while (toDo.isNotEmpty()) {
            steps += 1
            toDo = toDo.flatMap { (currentRow, currentCol) ->
                val currentElevationChar = field[currentRow][currentCol].let { if (it == 'S') 'a' else it }
                dirs.map {
                    currentRow + it.first to currentCol + it.second
                }.filter { (newRow, newCol) ->
                    newRow in rowIndices && newCol in colIndices
                            && !visited[newRow][newCol]
                            && ((currentElevationChar >= 'y' && field[newRow][newCol] == 'E') || field[newRow][newCol] in 'a'..(currentElevationChar + 1))
                }
            }.toSet()
                .also {
                    if (it.contains(goalPair)) return steps
                }
                .onEach { nextPosition ->
                    visited[nextPosition.first][nextPosition.second] = true
                }
        }

        throw IllegalStateException("Goal can't be reached")
    }
}

fun main() {
    val inputReader = Day12::class.java.getResource("/input/day12.txt").toReader()
    Day12(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}