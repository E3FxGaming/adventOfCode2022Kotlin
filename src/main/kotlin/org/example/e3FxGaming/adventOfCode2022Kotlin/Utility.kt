package org.example.e3FxGaming.adventOfCode2022Kotlin

import java.io.File
import java.net.URL

fun URL?.toFile(): File = File(this?.toURI()!!)