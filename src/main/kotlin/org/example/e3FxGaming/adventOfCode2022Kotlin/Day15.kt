package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader
import java.time.Duration
import java.time.Instant
import kotlin.math.abs

class Day15(override val inputReader: Reader, private val part1RequestedRow: Int, val part2SearchZeroTo: Int) : Day {
    private val inputRegex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()

    private val sensorsAndClosestBeacon = inputReader.closingReadLines().map { line ->
        inputRegex.find(line)?.let { matchResult ->
            matchResult.groupValues.drop(1).map(String::toInt).let {
                Pair(it[0] to it[1], it[2] to it[3])
            }
        } ?: throw IllegalStateException("No match in line \"$line\"")
    }.toMap()

    override fun part1(): Int {
        println("Part 1")
        val requestedRowPositions = mutableSetOf<Int>()

        sensorsAndClosestBeacon.toList().forEachIndexed { index, (sensor, beacon) ->
            println("Beacon ${index + 1} out of ${sensorsAndClosestBeacon.size}")
            val manhattanDistance = abs(sensor.first - beacon.first) + abs(sensor.second - beacon.second)
            val minRow = sensor.second - manhattanDistance
            val maxRow = sensor.second + manhattanDistance

            if (part1RequestedRow in minRow..maxRow) {
                val leftRightSpread = manhattanDistance - abs(sensor.second - part1RequestedRow)
                val minCol = sensor.first - leftRightSpread
                val maxCol = sensor.first + leftRightSpread
                requestedRowPositions.addAll(minCol..maxCol)
            }
        }

        sensorsAndClosestBeacon.values.forEach { (beaconCol, beaconRow) ->
            if (beaconRow == part1RequestedRow) requestedRowPositions.remove(beaconCol)
        }

        return requestedRowPositions.size
    }

    fun Duration.customText() = buildString {
        appendLine("Minutes: ${toMinutesPart()}")
        appendLine("Seconds: ${toSecondsPart()}")
        appendLine("Milliseconds: ${toMillisPart()}")
        appendLine("Nanoseconds: ${toNanos()}")
    }

    override fun part2(): Long {
        println("\nPart 2")
        val startTime = Instant.now()
        val coverageMap = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()

        sensorsAndClosestBeacon.toList().forEachIndexed { index, (sensor, beacon) ->
            println("Beacon ${index + 1} out of ${sensorsAndClosestBeacon.size}")
            val manhattanDistance = abs(sensor.first - beacon.first) + abs(sensor.second - beacon.second)
            val minRow = (sensor.second - manhattanDistance).coerceAtLeast(0)
            val maxRow = (sensor.second + manhattanDistance).coerceAtMost(part2SearchZeroTo)

            for (currentRowNr in minRow..maxRow) {
                val leftRightSpread = manhattanDistance - abs(sensor.second - currentRowNr)
                val minCol = (sensor.first - leftRightSpread).coerceAtLeast(0)
                val maxCol = (sensor.first + leftRightSpread).coerceAtMost(part2SearchZeroTo)

                coverageMap.getOrPut(currentRowNr) { mutableListOf() }.add(minCol to maxCol)
            }
        }

        for ((rowNr, pieces) in coverageMap) {
            var nextStartAt = 0

            for ((from, to) in pieces.sortedBy { it.first }) {
                if (from <= nextStartAt) {
                    nextStartAt = nextStartAt.coerceAtLeast(to + 1)
                } else {
                    val result = (nextStartAt.toLong() * 4_000_000) + rowNr
                    val stopTime = Instant.now()
                    val duration = Duration.between(startTime, stopTime)
                    println("Part 2 in\n${duration.customText()}")
                    return result
                }
            }

            if (nextStartAt != part2SearchZeroTo + 1) {
                val result = (nextStartAt.toLong() * 4_000_000) + rowNr
                val stopTime = Instant.now()
                val duration = Duration.between(startTime, stopTime)
                println("Part 2 in\n${duration.customText()}")
                return result
            }

        }

        throw IllegalStateException("All positions in search field covered")
    }
}

fun main() {
    val inputReader = Day15::class.java.getResource("/input/day15.txt").toReader()
    Day15(inputReader, 2_000_000, 4_000_000).let {
        println(it.part1())
        println(it.part2())
    }
}