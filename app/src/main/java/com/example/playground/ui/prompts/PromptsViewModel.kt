package com.example.playground.ui.prompts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PromptsViewModel : ViewModel() {
    private val _prompts: Flow<String> = flow {
        val data = listOf(
            "Second prompt",
            "Third prompt",
            "Fourth prompt",
            "5th prompt",
            "Prompt for Medium",
            "Medium machine",
            "1",
            "2",
            "3",
            "4",
        )

        while (true) {
            delay(2000)
            emit(data.random())
        }
    }
    val prompts: Flow<String> = _prompts
}