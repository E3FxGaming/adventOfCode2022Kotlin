package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.Reader
import java.util.LinkedList

class Day07(override val inputReader: Reader) : Day {
    private val rootDirectory = Directory(null)

    init {
        var currentDir = rootDirectory

        inputReader.closingReadLines().forEach { inputLine ->
            val segments = inputLine.split(' ')
            if (segments[0] == "$") {
                if (segments[1] == "cd") {
                    currentDir = when (segments[2]) {
                        "/" -> rootDirectory
                        ".." -> currentDir.parentDir!!
                        else -> currentDir.getChildDir(segments[2])
                    }
                }
            } else {
                if (segments[0] == "dir") {
                    currentDir.getChildDir(segments[1])
                } else {
                    currentDir.registerContainedFile(segments[1], segments[0].toLong())
                }
            }
        }
    }

    override fun part1(): Long {
        var result = 0L

        val toDo = LinkedList<Directory>()
        toDo.add(rootDirectory)

        while (toDo.isNotEmpty()) {
            val currentDir = toDo.pop()
            if (currentDir.dirSize <= 100_000L) {
                result += currentDir.dirSize
            }
            toDo.addAll(currentDir.getChildDirectories())
        }


        return result
    }

    override fun part2(): Long {
        val totalDiskSpace = 70_000_000L
        val spaceNeeded = 30_000_000L

        val spaceToFree = spaceNeeded - (totalDiskSpace - rootDirectory.dirSize)

        var deleteDirWithSize = Long.MAX_VALUE

        val toDo = LinkedList<Directory>()
        toDo.add(rootDirectory)

        while (toDo.isNotEmpty()) {
            val currentDir = toDo.pop()
            if (currentDir.dirSize >= spaceToFree) {
                if(currentDir.dirSize < deleteDirWithSize) {
                    deleteDirWithSize = currentDir.dirSize
                }

                toDo.addAll(currentDir.getChildDirectories())
            }
        }

        return deleteDirWithSize
    }


    private class Directory(val parentDir: Directory?) {
        private val containsFiles = mutableMapOf<String, Long>()
        private val containsDirs = mutableMapOf<String, Directory>()

        fun getChildDir(name: String): Directory =
            containsDirs.getOrPut(name) { Directory(this) }

        fun registerContainedFile(fileName: String, size: Long) =
            containsFiles[fileName]?.also { registeredSize ->
                assert(size == registeredSize) { "File $fileName with size $size already registered with size $size" }
            } ?: containsFiles.put(fileName, size)

        val dirSize: Long by lazy {
            containsFiles.values.sum() + containsDirs.values.sumOf { it.dirSize }
        }

        fun getChildDirectories() = containsDirs.values.toList()
    }
}

fun main() {
    val inputReader = Day07::class.java.getResource("/input/day07.txt").toReader()
    Day07(inputReader).let {
        println(it.part1())
        println(it.part2())
    }
}