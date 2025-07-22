package com.example.playground.ui.lottie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playground.ui.lottie.model.AnimationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LottieViewModel : ViewModel() {
    private val _animationUiState: MutableLiveData<AnimationUiState> =
        MutableLiveData(AnimationUiState.Done(true))

    val animationUiState: LiveData<AnimationUiState>
        get() = _animationUiState


    fun setInitial() {
        _animationUiState.value = AnimationUiState.Initial
    }

    fun setInProgress() {
        _animationUiState.value = AnimationUiState.InProgress
    }

    fun setDone(shouldShowAnimation: Boolean) {
        viewModelScope.launch {
            delay(50)
            _animationUiState.value = AnimationUiState.Done(shouldShowAnimation)
        }
    }
}