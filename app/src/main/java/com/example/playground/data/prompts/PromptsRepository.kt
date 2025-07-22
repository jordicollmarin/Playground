package com.example.playground.data.prompts

import android.util.Log
import com.example.playground.data.prompts.model.PromptsData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PromptsRepository {
    companion object {
        private const val DELAY = 1500L
    }

    val promptsList = listOf(
        "First prompt",
        "Second prompt",
        "Third prompt",
        "Fourth prompt",
        "Fifth prompt",
        "Prompt for Medium",
        "Medium machine",
    )

    private var last = ""

    fun getFirstPrompt(): PromptsData = PromptsData(last, getRandomAndUpdateLastPrompt())

    val prompts: Flow<PromptsData> =
        flow {
            while (true) {
                delay(DELAY)
                val newPrompt = getRandomAndUpdateLastPrompt()
                emit(PromptsData(last, newPrompt))
                last = newPrompt
            }
        }

    private fun getRandomAndUpdateLastPrompt(): String {
        var newPrompt: String
        do {
            newPrompt = promptsList.random()
        } while (last == newPrompt)

        return newPrompt
    }
}