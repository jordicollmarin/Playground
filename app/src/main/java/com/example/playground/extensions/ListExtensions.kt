package com.example.playground.extensions

import kotlinx.coroutines.delay

suspend fun <T> List<T>.loop(action: (T, T) -> Unit) {
    when {
        size < 2 -> return

        else -> {
            val last = mutableListOf(first())

            fun getRandom() =
                subtract(last.toSet()).random().also {
                    last.add(it)
                    if (last.size > 2) last.removeAt(0)
                }

            while (true) {
                delay(1500)
                val current = last.last()
                val next = getRandom()
                action(current, next)
            }
        }
    }
}

/**
 * Loops through the list, picking a random item and passing it to the action. The same
 * item will not be picked twice in a row.
 */
suspend fun <T> List<T>.loopSingleItem(action: (T) -> Unit) {
    when {
        size < 2 -> return

        else -> {
            var random = random()

            while (true) {
                // Introduce a delay to simulate a pause between actions
                delay(1500)

                // Execute the action with a random item that is not the same as the last one
                action(
                    // Subtract the last random item to ensure it's not picked again
                    subtract(
                        setOf(random)
                    ).random().also {
                        // Update the last random item
                        random = it
                    }
                )
            }
        }
    }
}