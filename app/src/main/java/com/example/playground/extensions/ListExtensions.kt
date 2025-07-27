package com.example.playground.extensions

import kotlinx.coroutines.delay

suspend fun <T> List<T>.loop(action: (T, T) -> Unit) {
    when {
        isEmpty() || size == 1 -> return

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


suspend fun <T> List<T>.loopSingleItem(action: (T) -> Unit) {
    when {
        isEmpty() || size == 1 -> return

        else -> {
            var random = random()
            while (true) {
                delay(1500)
                action(subtract(setOf(random)).random().also {
                    random = it
                })
            }
        }
    }
}