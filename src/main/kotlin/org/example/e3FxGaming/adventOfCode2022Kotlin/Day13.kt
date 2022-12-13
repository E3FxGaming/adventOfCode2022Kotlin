package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader
import java.util.Stack

class Day13(override val inputReader: Reader) : Day {
    private sealed interface Container {
        class ListContainer : Container {
            private val children = mutableListOf<Container>()
            fun addContainer(newContainer: Container) {
                children.add(newContainer)
            }

            override fun compareWith(other: ListContainer): Boolean? {
                children.zip(other.children).forEach { (aContainer, bContainer) ->
                    aContainer.compareWith(bContainer)?.let { return it }
                }
                return when {
                    children.size < other.children.size -> true
                    children.size > other.children.size -> false
                    else -> null
                }
            }

            override fun compareWith(other: IntContainer): Boolean? =
                this.compareWith(ListContainer().also {
                    it.addContainer(other)
                })

            override fun toString(): String {
                return children.joinToString(", ", prefix = "[", postfix = "]")
            }
        }

        class IntContainer(val nr: Int) : Container {
            override fun compareWith(other: ListContainer): Boolean? =
                ListContainer().also {
                    it.addContainer(this)
                }.compareWith(other)

            override fun compareWith(other: IntContainer): Boolean? {
                if (nr < other.nr) return true
                if (nr > other.nr) return false
                return null
            }

            override fun toString(): String {
                return nr.toString()
            }
        }


        fun compareWith(other: Container) = when (other) {
            is ListContainer -> compareWith(other)
            is IntContainer -> compareWith(other)
        }

        fun compareWith(other: ListContainer): Boolean?
        fun compareWith(other: IntContainer): Boolean?
    }


    private fun parseLine(line: String): Container.ListContainer {
        val childContainerStack = Stack<Container.ListContainer>()

        var currentNr: Int? = null
        line.forEach {
            when (it) {
                '[' -> {
                    Container.ListContainer().apply {
                        if (childContainerStack.isNotEmpty()) {
                            childContainerStack.last().addContainer(this)
                        }

                        childContainerStack.add(this)
                    }
                }
                ']', ',' -> {
                    currentNr?.let { currentNrNotNull ->
                        childContainerStack.last().addContainer(Container.IntContainer(currentNrNotNull))
                        currentNr = null
                    }

                    if(it == ']') {
                        val lastPoppedContainer = childContainerStack.pop()
                        if(childContainerStack.isEmpty()) return lastPoppedContainer
                    }
                }
                else -> {
                    if (it.isDigit()) {
                        currentNr?.also { currentNrNotNull ->
                            currentNr = (currentNrNotNull * 10) + it.digitToInt()
                        } ?: kotlin.run { currentNr = it.digitToInt() }
                    } else throw IllegalStateException("Unrecognized char: $it")
                }
            }
        }

        throw IllegalStateException("Last container not popped")
    }

    private val containerPairs = inputReader.closingReadText().trim()
        .split(System.lineSeparator().repeat(2))
        .map { pairLines ->
            pairLines.split(System.lineSeparator())
                .map(::parseLine)
                .let { (aContainer, bContainer) ->
                    aContainer to bContainer
                }
        }


    override fun part1(): Int =
        containerPairs.mapIndexedNotNull { index, (aPacket, bPacket) ->
            if (aPacket.compareWith(bPacket) == true) index + 1 else null
        }.reduce(Int::plus)

    override fun part2(): Int {
        val dividerString = """
            [[2]]
            [[6]]
        """.trimIndent()

        val dividerPackets = dividerString.split(System.lineSeparator().repeat(2))
            .flatMap { pairLines ->
                pairLines.split(System.lineSeparator())
                    .map(::parseLine)
            }.toSet()

        val allPackets = dividerPackets + containerPairs.flatMap {
            listOf(it.first, it.second)
        }


        val sortedResult = allPackets.sortedWith { o1, o2 ->
            when (o1.compareWith(o2)) {
                true -> -1
                false -> 1
                else -> 0
            }
        }

        return sortedResult.indices.filter {
            sortedResult[it] in dividerPackets
        }.let { (indexA, indexB) ->
            (indexA + 1) * (indexB + 1)
        }
    }
}

fun main() {
    val inputReader = Day12::class.java.getResource("/input/day13.txt").toReader()
    Day13(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}