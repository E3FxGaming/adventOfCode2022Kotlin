package org.example.e3FxGaming.adventOfCode2022Kotlin

class Playground {

    private sealed interface Container {
        class ListContainer(val children: List<Container>) : Container

        class IntContainer(val nr: Int) : Container
    }

    private fun stringifyMyContainer(myContainer: Container): String {
        return buildString {
            when(myContainer) {
                is Container.IntContainer -> append("${myContainer.nr}, ")
                is Container.ListContainer -> {
                    append('(')
                    myContainer.children.forEach {
                        append(stringifyMyContainer(it))
                    }
                    append("), ")
                }
            }
        }
    }

    fun startPlayground() {
        val myContainers = Container.ListContainer(
            listOf(
                Container.ListContainer(
                    listOf(
                        Container.IntContainer(5),
                        Container.IntContainer(6),
                        Container.IntContainer(7),
                    )
                ),
                Container.IntContainer(2)
            )
        )

        println(stringifyMyContainer(myContainers))
    }


}

fun main() {
    Playground().startPlayground()
}