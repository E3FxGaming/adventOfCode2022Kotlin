package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day14(override val inputReader: Reader) : Day {
    private val wallCorners = inputReader.closingReadLines().map { wall ->
        wall.split(" -> ").map { wallCorner ->
            wallCorner.split(',').let { (xCoordinate, yCoordinate) ->
                xCoordinate.toInt() to yCoordinate.toInt()
            }
        }
    }



    private fun initField(): Array<CharArray> {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE

        var maxY = Int.MIN_VALUE

        wallCorners.flatten().forEach { (xCoordinate, yCoordinate) ->
            minX = minX.coerceAtMost(xCoordinate)
            maxX = maxX.coerceAtLeast(xCoordinate)

            maxY = maxY.coerceAtLeast(yCoordinate)
        }

        val minY = 0

        val xLength = (minX..maxX).count()
        val yLength = (minY..maxY).count()

        val field = Array(yLength) {
            CharArray(xLength) { '.' }
        }


        val grainXStart = 500 - minX
        if(grainXStart in field.first().indices) {
            field[0][grainXStart] = '+'
        }

        wallCorners.forEach { wallLine ->
            wallLine.zipWithNext().forEach { (pointA, pointB) ->
                val (xPointA, yPointA) = pointA
                val (xPointB, yPointB) = pointB

                for(xCoordinate in xPointA.coerceAtMost(xPointB)..xPointA.coerceAtLeast(xPointB)) {
                    for(yCoordinate in yPointA.coerceAtMost(yPointB)..yPointA.coerceAtLeast(yPointB)) {
                        field[yCoordinate][xCoordinate - minX] = '#'
                    }
                }
            }
        }

        return field
    }

    private fun stringifyField(field: Array<CharArray>) =
        field.joinToString(System.lineSeparator()) { it.joinToString("")}

    private val blockingChars = setOf('o', '#')

    override fun part1(): Int {
        val field = initField()
        println(stringifyField(field))

        var restingGrains = 0

        val grainXStart = field[0].indexOf('+')
        if(grainXStart == -1) return 0

        var currentX = grainXStart
        var currentY = 0

        while (true) {
            if(currentY !in field.indices || currentX !in field.first().indices)
                return restingGrains

            val fieldBelowBlocked = field.getOrNull(currentY+1)?.get(currentX).let(blockingChars::contains)
            if(!fieldBelowBlocked) {
                currentY++
            } else {
                val fieldDiagonalDownLeftBlocked = field.getOrNull(currentY+1)
                    ?.getOrNull(currentX-1).let(blockingChars::contains)

                if(!fieldDiagonalDownLeftBlocked) {
                    currentY++
                    currentX--
                } else {
                    val fieldDiagonalDownRightBlocked = field.getOrNull(currentY+1)
                        ?.getOrNull(currentX+1).let(blockingChars::contains)

                    if(!fieldDiagonalDownRightBlocked) {
                        currentY++
                        currentX++
                    } else {
                        field[currentY][currentX] = 'o'
                        restingGrains++
                        currentX = grainXStart
                        currentY = 0
                    }
                }
            }

        }
    }

    private fun hashField(): MutableMap<Pair<Int, Int>, Char?> {
        val hashField = mutableMapOf<Pair<Int, Int>, Char?>()

        var lowestY = 0

        wallCorners.forEach { wallLine ->
            wallLine.zipWithNext().forEach { (pointA, pointB) ->
                val (xPointA, yPointA) = pointA
                val (xPointB, yPointB) = pointB

                lowestY = lowestY.coerceAtLeast(yPointA).coerceAtLeast(yPointB)

                for(xCoordinate in xPointA.coerceAtMost(xPointB)..xPointA.coerceAtLeast(xPointB)) {
                    for(yCoordinate in yPointA.coerceAtMost(yPointB)..yPointA.coerceAtLeast(yPointB)) {
                        hashField[xCoordinate to yCoordinate] = '#'
                    }
                }
            }
        }

        return hashField.withDefault {
            if(it.second == lowestY + 2) '#' else null
        }
    }

    private fun stringifyHashField(hashField: Map<Pair<Int, Int>, Char?>): String {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE

        var maxY = Int.MIN_VALUE

        hashField.keys.forEach { (x, y) ->
            minX = minX.coerceAtMost(x)
            maxX = maxX.coerceAtLeast(x)

            maxY = maxY.coerceAtLeast(y)
        }

        val xLength = (minX..maxX).count()
        val yLength = (0..maxY).count()

        val field = Array(yLength) {
            CharArray(xLength) { '.' }
        }

        hashField.forEach { (x, y), c ->
            if(c != null) field[y][x-minX] = c
        }



        return field.joinToString(System.lineSeparator()) { it.joinToString("")}
    }

    override fun part2(): Int {
        val hashField = hashField()

        var restingGrains = 0

        var currentX = 500
        var currentY = 0

        println(stringifyHashField(hashField))

        while (true) {
            val rowBelow = currentY + 1

            val fieldBelowBlocked = hashField.getValue(currentX to rowBelow).let(blockingChars::contains)
            if(!fieldBelowBlocked) {
                currentY++
            } else {
                val fieldDiagonalDownLeftBlocked = hashField.getValue(currentX-1 to rowBelow).let(blockingChars::contains)
                if(!fieldDiagonalDownLeftBlocked) {
                    currentY++
                    currentX--
                } else {
                    val fieldDiagonalDownRightBlocked = hashField.getValue(currentX+1 to rowBelow).let(blockingChars::contains)

                    if(!fieldDiagonalDownRightBlocked) {
                        currentY++
                        currentX++
                    } else {
                        hashField[currentX to currentY] = 'o'
                        restingGrains++
                        if(currentX == 500 && currentY == 0) return restingGrains
                        currentX = 500
                        currentY = 0
                    }
                }
            }

        }
    }
}

fun main() {
    val inputReader = Day14::class.java.getResource("/input/day14.txt").toReader()
    Day14(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}