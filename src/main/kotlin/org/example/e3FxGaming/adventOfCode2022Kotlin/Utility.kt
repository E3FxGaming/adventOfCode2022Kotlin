package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.File
import java.io.Reader
import java.net.URL

fun URL?.toReader() = File(this?.toURI()!!).bufferedReader()

fun Reader.closingReadLines() = this.use(Reader::readLines)

fun Reader.closingReadText() = this.use(Reader::readText)

