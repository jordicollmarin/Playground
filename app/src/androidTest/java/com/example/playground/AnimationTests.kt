package com.example.playground

import androidx.activity.ComponentActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.airbnb.lottie.compose.LottieClipSpec
import com.example.playground.ui.lottie.LOTTIE_ANIMATION_TEST_TAG
import com.example.playground.ui.lottie.LocalLottieClipSpec
import com.example.playground.ui.lottie.LottieScreen
import com.example.playground.ui.lottie.LottieAnimationUiModel
import com.example.playground.ui.theme.PlaygroundTheme
import org.junit.Rule
import org.junit.Test

class AnimationTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun animationIsShown() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalLottieClipSpec provides LottieClipSpec.Progress(0f, 0.99f)
            ) {
                // Given
                val uiModel = LottieAnimationUiModel(true)

                // When
                PlaygroundTheme { LottieScreen(uiModel) }
            }
        }

        composeTestRule.waitUntilDoesNotExist(matcher = hasTestTag(LOTTIE_ANIMATION_TEST_TAG), timeoutMillis = 10000)

        // Then
        composeTestRule
            .onNodeWithTag(LOTTIE_ANIMATION_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun offerProgressBarConfettiIsNotShown() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalLottieClipSpec provides LottieClipSpec.Progress(0f, 0.99f)
            ) {
                // Given
                val uiModel = LottieAnimationUiModel(false)

                // When
                PlaygroundTheme { LottieScreen(uiModel) }
            }
        }

        // Then
        composeTestRule
            .onNodeWithTag(LOTTIE_ANIMATION_TEST_TAG)
            .assertIsNotDisplayed()
    }
}
