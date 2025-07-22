package com.example.playground.ui.lottie.model

sealed class AnimationUiState {
    data object Initial : AnimationUiState()
    data object InProgress : AnimationUiState()
    data class Done(val shouldShowAnimation: Boolean) : AnimationUiState()
}

data class LottieAnimationUiModel(val shouldShowAnimation: Boolean)