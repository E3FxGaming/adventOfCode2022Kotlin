package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader
import kotlin.math.abs

class Day09(override val inputReader: Reader) : Day {
    private enum class Directions(val xDif: Int, val yDif: Int) {
        UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0), NEUTRAL(0, 0);
    }

    private operator fun Pair<Int, Int>.plus(dir: Directions): Pair<Int, Int> {
        return first + dir.xDif to second + dir.yDif
    }

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return first + other.first to second + other.second
    }

    private val instructions = inputReader.closingReadLines().map { line ->
        line.split(' ').let { (letter, amount) ->
            when (letter.first()) {
                'U' -> Directions.UP
                'D' -> Directions.DOWN
                'L' -> Directions.LEFT
                'R' -> Directions.RIGHT
                else -> throw IllegalStateException("Direction $letter not recognized")
            } to amount.toInt()
        }
    }

    override fun part1(): Int = simulateRope(2)

    override fun part2(): Int = simulateRope(10)

    private fun simulateRope(knotNumbers: Int): Int {
        val knotNames = (1..knotNumbers).toList()
        val knotFollowsKnot = knotNames.zipWithNext()

        val knotPositions = knotNames.associateWith { 0 to 0 }.toMutableMap()

        val tailPositions: MutableSet<Pair<Int, Int>> = mutableSetOf(0 to 0)

        instructions.forEach { (dir, amount) ->
            //println("${dir.name} $amount")
            for (stepsTaken in 1..amount) {
                //move head knot
                knotPositions[knotNames.first()] = knotPositions.getValue(knotNames.first()) + dir

                //calculate non-head knot movements
                knotFollowsKnot.forEach { (firstKnotName, secondKnotName) ->
                    //get knot information
                    val firstKnotPosition = knotPositions.getValue(firstKnotName)
                    val secondKnotPosition = knotPositions.getValue(secondKnotName)

                    val xDif = firstKnotPosition.first - secondKnotPosition.first
                    val yDif = firstKnotPosition.second - secondKnotPosition.second

                    //translation of second knot
                    val xCorr = when {
                        xDif == 2 || (abs(yDif) == 2 && xDif == 1) -> Directions.RIGHT
                        xDif == -2 || (abs(yDif) == 2 && xDif == -1) -> Directions.LEFT
                        else -> Directions.NEUTRAL
                    }

                    val yCorr = when {
                        yDif == 2 || (abs(xDif) == 2 && yDif == 1) -> Directions.UP
                        yDif == -2 || (abs(xDif) == 2 && yDif == -1) -> Directions.DOWN
                        else -> Directions.NEUTRAL
                    }

                    knotPositions[secondKnotName] = secondKnotPosition + xCorr + yCorr


                    //record tail movement
                    if (secondKnotName == knotNames.last()) {
                        tailPositions.add(knotPositions.getValue(secondKnotName)) //update tail steps map
                    }
                }
                //println(paintPoints(knotPositions.values.toSet()))
            }
        }
        return tailPositions.size
    }

    private fun paintPoints(points: Set<Pair<Int, Int>>): String {
        var minX = 0
        var maxX = 0
        var minY = 0
        var maxY = 0
        points.forEach { (x, y) ->
            minX = minX.coerceAtMost(x)
            maxX = maxX.coerceAtLeast(x)
            minY = minY.coerceAtMost(y)
            maxY = maxY.coerceAtLeast(y)
        }

        return buildString {
            for (rowNr in maxY downTo minY) {
                for (colNr in minX..maxX) {
                    append(if (colNr to rowNr in points) '#' else '.')
                }
                append("\n")
            }
        }
    }
}

fun main() {
    val inputReader = Day09::class.java.getResource("/input/day09.txt").toReader()
    Day09(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}