package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader

class Day11(override val inputReader: Reader) : Day {
    private class Monkey(monkeyTextChunk: List<String>) {
        val id: Int = monkeyTextChunk[0].substring(7, monkeyTextChunk[0].lastIndex).toInt()
        private val originalFears = monkeyTextChunk[1].substringAfter("Starting items: ")
            .split(", ")
            .map(String::toLong)

        private var currentItems: MutableList<Long> = originalFears.toMutableList()

        var inspectionCount: Long = 0L
            private set

        fun resetMonkey() {
            currentItems = originalFears.toMutableList()
            inspectionCount = 0L
        }

        private val inspect: (Long) -> Long = monkeyTextChunk[2]
            .substringAfter("Operation: new = ")
            .split(' ').let { opDesc ->
                val firstOpOrNull = opDesc[0].toLongOrNull()
                val secondOpOrNull = opDesc[2].toLongOrNull()

                val connection: (Long, Long) -> Long = when (opDesc[1]) {
                    "+" -> Long::plus
                    "*" -> Long::times
                    else -> throw IllegalStateException("Operation ${opDesc[1]} not recognized")
                }

                { old ->
                    inspectionCount++
                    connection.invoke(firstOpOrNull ?: old, secondOpOrNull ?: old)
                }
            }

        val divBy = monkeyTextChunk[3].substringAfter("Test: divisible by ").toInt()
        val trueMonkey = monkeyTextChunk[4].substringAfter("If true: throw to monkey ").toInt()
        val falseMonkey = monkeyTextChunk[5].substringAfter("If false: throw to monkey ").toInt()

        private fun testPassToMonkey(inputFear: Long): Int =
            if (inputFear % divBy == 0L) trueMonkey else falseMonkey

        fun throwItems(monkeyMap: Map<Int, Monkey>, reductionStrategy: (Long) -> Long) {
            currentItems.forEach { nextItem ->
                val inspectedItem = inspect(nextItem) //inspect item
                val reducedItem = reductionStrategy(inspectedItem) //reduce fear
                val receiverMonkey = testPassToMonkey(reducedItem)
                monkeyMap.getValue(receiverMonkey).currentItems.add(reducedItem)
            }

            currentItems = mutableListOf()
        }
    }

    private val monkeys = inputReader.closingReadText()
        .split(System.lineSeparator().repeat(2))
        .map { oneMonkeyOneString ->
            oneMonkeyOneString
                .split(System.lineSeparator())
                .map(String::trim)
        }.map { Monkey(it) }
        .associateBy(Monkey::id)
        .toSortedMap()

    override fun part1(): Long {
        monkeys.values.forEach(Monkey::resetMonkey)

        for (round in 1..20) {
            for (monkey in monkeys) {
                monkey.value.throwItems(monkeys) { it / 3 }
            }
        }

        return monkeys.values
            .sortedByDescending(Monkey::inspectionCount)
            .take(2).let { (m1, m2) ->
                m1.inspectionCount * m2.inspectionCount
            }
    }

    override fun part2(): Long {
        monkeys.values.forEach(Monkey::resetMonkey)

        val numberCeiling = monkeys.values.fold(1) { acc, monkey ->
            if(acc % monkey.divBy == 0) acc else acc * monkey.divBy
        }

        for (round in 1..10_000) {
            for (monkey in monkeys) {
                monkey.value.throwItems(monkeys) { originalFear ->
                    originalFear % numberCeiling
                }
            }
        }

        return monkeys.values
            .sortedByDescending(Monkey::inspectionCount).take(2)
            .let { (m1, m2) ->
                m1.inspectionCount * m2.inspectionCount
            }
    }
}

fun main() {
    val inputReader = Day11::class.java.getResource("/input/day11.txt").toReader()
    Day11(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}