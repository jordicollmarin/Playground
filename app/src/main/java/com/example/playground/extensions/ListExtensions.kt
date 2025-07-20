package com.example.playground.extensions

import kotlinx.coroutines.delay

suspend fun <T> List<T>.loop(action: (T) -> Unit) {
    when {
        isEmpty() || size == 1 -> return

        else -> while (true) {
            delay(1500)
            action(random())
        }
    }
}