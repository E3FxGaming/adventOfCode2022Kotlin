package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day08(override val inputReader: Reader) : Day {
    private val grid: Array<IntArray> = inputReader.closingReadLines()
        .map { line -> line.map(Char::digitToInt).toIntArray() }
        .toTypedArray()

    override fun part1(): Int {
        val visible = Array(grid.size) {
            BooleanArray(grid.first().size)
        }

        //scan rows
        rowLoop@ for ((rowIndex, row) in grid.withIndex()) {
            var leftMax = -1
            var rightMax = -1

            for ((leftIndex, leftElement) in row.withIndex()) {
                if (leftElement > leftMax) {
                    visible[rowIndex][leftIndex] = true
                    leftMax = leftElement
                }
                if (row[row.lastIndex - leftIndex] > rightMax) {
                    visible[rowIndex][row.lastIndex - leftIndex] = true
                    rightMax = row[row.lastIndex - leftIndex]
                }
                if (leftMax == 9 && rightMax == 9) continue@rowLoop
            }
        }

        //scan columns
        columnLoop@ for (columnIndex in grid.first().indices) {
            var topMax = -1
            var bottomMax = -1

            for (rowIndex in grid.indices) {
                if (grid[rowIndex][columnIndex] > topMax) {
                    visible[rowIndex][columnIndex] = true
                    topMax = grid[rowIndex][columnIndex]
                }
                if (grid[grid.lastIndex - rowIndex][columnIndex] > bottomMax) {
                    visible[grid.lastIndex - rowIndex][columnIndex] = true
                    bottomMax = grid[grid.lastIndex - rowIndex][columnIndex]
                }

                if (topMax == 9 && bottomMax == 9) continue@columnLoop
            }
        }

        return visible.sumOf { row ->
            row.count { it }
        }
    }

    override fun part2(): Int {
        val directions = listOf(
            1 to 0, -1 to 0, 0 to 1, 0 to -1
        )

        var maxScenicScore = 0

        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, stopAtHeight ->
                val currentScenicScore = directions.fold(1) { acc, currentDirection ->
                    when {
                        acc == 0 || rowIndex + currentDirection.first !in grid.indices || columnIndex + currentDirection.second !in grid.first().indices -> 0
                        else -> {
                            var directionScore = 0
                            while (directionScore == 0 || grid[rowIndex + (directionScore * currentDirection.first)][columnIndex + (directionScore * currentDirection.second)] < stopAtHeight
                            ) {
                                directionScore++
                                if(rowIndex + (directionScore * currentDirection.first) !in grid.indices
                                    || columnIndex + (directionScore * currentDirection.second) !in grid.first().indices) {
                                    directionScore--
                                    break
                                }
                            }
                            acc * directionScore
                        }
                    }
                }

                maxScenicScore = maxScenicScore.coerceAtLeast(currentScenicScore)
            }
        }

        return maxScenicScore
    }
}

fun main() {
    val inputReader = Day08::class.java.getResource("/input/day08.txt").toReader()
    Day08(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}