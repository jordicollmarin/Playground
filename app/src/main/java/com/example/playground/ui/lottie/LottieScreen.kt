package com.example.playground.ui.lottie

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

const val LOTTIE_ANIMATION_FILE = "animation/small_confetti_centre.lottie"
const val LOTTIE_ANIMATION_TEST_TAG = "lottieAnimationTestTag"

@VisibleForTesting
var LocalLottieClipSpec = staticCompositionLocalOf { LottieClipSpec.Progress() }

@VisibleForTesting
var LocalLottieIterations = compositionLocalOf { 1 }

@Composable
fun LottieScreen(
    model: LottieAnimationUiModel
) {
    Column {
        if (model.shouldShowAnimation) {
            CustomLottieAnimation(
                onAnimationFinishedListener = {
                    Log.d("LottieAnimationArticle", "The animation has finished")
                }
            )
        }
    }
}

@Composable
private fun CustomLottieAnimation(
    modifier: Modifier = Modifier,
    onAnimationFinishedListener: () -> Unit,
) {
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset(LOTTIE_ANIMATION_FILE)
    )
    val animationProgress by animateLottieCompositionAsState(
        composition = lottieComposition,
        clipSpec = LocalLottieClipSpec.current,
        iterations = LocalLottieIterations.current
    )

    Log.d("LottieAnimationArticle", "AnimationProgress: $animationProgress")

    when (animationProgress) {
        1f -> onAnimationFinishedListener()
        else ->
            LottieAnimation(
                composition = lottieComposition,
                progress = { animationProgress },
                modifier = modifier
                    .size(250.dp)
                    .testTag(LOTTIE_ANIMATION_TEST_TAG)
            )
    }
}

@Composable
fun Fragment1Screen(
    fragmentOneOnClick: () -> Unit,
    fragmentOneTwoOnClick: () -> Unit,
    initialOnClick: () -> Unit,
    inProgressOnClick: () -> Unit,
    doneOnClick: (Boolean) -> Unit,
    uiState: AnimationUiState?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UpdateStateButtons(initialOnClick, inProgressOnClick, doneOnClick)

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { fragmentOneOnClick() }
        ) {
            Text("Fragment 2")
        }
        Button(
            onClick = { fragmentOneTwoOnClick() }
        ) {
            Text("Fragment 3")
        }

        if (uiState is AnimationUiState.Done && uiState.shouldShowAnimation) {
            ConfettiAnimation()
        }
    }
}

@Composable
fun Fragment2Screen(
    uiState: AnimationUiState?,
    initialOnClick: () -> Unit,
    inProgressOnClick: () -> Unit,
    doneOnClick: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UpdateStateButtons(initialOnClick, inProgressOnClick, doneOnClick)

        if (uiState is AnimationUiState.Done && uiState.shouldShowAnimation) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConfettiAnimation()
            }
        }
    }
}

@Composable
fun Fragment3Screen(
    uiState: AnimationUiState?,
    initialOnClick: () -> Unit,
    inProgressOnClick: () -> Unit,
    doneOnClick: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UpdateStateButtons(initialOnClick, inProgressOnClick, doneOnClick)

        if (uiState is AnimationUiState.Done && uiState.shouldShowAnimation) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConfettiAnimation()
            }
        }
    }
}

@Composable
private fun ConfettiAnimation(
    modifier: Modifier = Modifier,
    onConfettiAnimationFinishedListener: () -> Unit = {},
) {
    val lottieComposition by rememberLottieComposition(
        LottieCompositionSpec.Asset("animation/small_confetti_centre.lottie")
    )
    val animationProgress by animateLottieCompositionAsState(
        composition = lottieComposition,
        clipSpec = LocalLottieClipSpec.current,
    )
    when (animationProgress) {
        1f -> onConfettiAnimationFinishedListener()
        else ->
            LottieAnimation(
                composition = lottieComposition,
                progress = { animationProgress },
                modifier = modifier.size(250.dp)
            )
    }
}

@Composable
private fun UpdateStateButtons(
    initialOnClick: () -> Unit,
    inProgressOnClick: () -> Unit,
    doneOnClick: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { initialOnClick() }
        ) {
            Text("Update to INITIAL")
        }
        Button(
            onClick = { inProgressOnClick() }
        ) {
            Text("Update to IN PROGRESS")
        }
        Button(
            onClick = {
                initialOnClick()
                doneOnClick(true)
            }
        ) {
            Text("Show Animation")
        }
        Button(
            onClick = { doneOnClick(false) }
        ) {
            Text("Update to DONE (false)")
        }
    }

}