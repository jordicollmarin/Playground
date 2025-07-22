package com.example.playground.ui.prompts

import androidx.lifecycle.ViewModel
import com.example.playground.data.prompts.PromptsRepository
import com.example.playground.data.prompts.model.PromptsData
import kotlinx.coroutines.flow.Flow

class PromptsViewModel(
    private val promptsRepository: PromptsRepository = PromptsRepository()
) : ViewModel() {

    private val _promptsFlow: Flow<PromptsData> = promptsRepository.prompts
    val promptsFlow: Flow<PromptsData> = _promptsFlow

    private val _prompts: List<String> = promptsRepository.promptsList
    val prompts: List<String> = _prompts


    fun getFirstPrompt(): PromptsData = promptsRepository.getFirstPrompt()
}