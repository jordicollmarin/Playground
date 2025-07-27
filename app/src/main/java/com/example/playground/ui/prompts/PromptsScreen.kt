package com.example.playground.ui.prompts

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.playground.data.prompts.model.PromptsData
import com.example.playground.extensions.loop
import com.example.playground.extensions.loopSingleItem

@Composable
fun PromptsScreen(
    viewModel: PromptsViewModel
) {
    Scaffold { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            PromptsListApproach(Modifier.weight(1f), viewModel)
            PromptsListPairApproach(Modifier.weight(1f), viewModel)
            PromptsFlowApproach(Modifier.weight(1f), viewModel)
        }
    }
}

@Composable
fun PromptsListApproach(modifier: Modifier = Modifier, viewModel: PromptsViewModel) {
    val data = viewModel.prompts

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val prompt by produceState(initialValue = data.first()) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            data.loopSingleItem { prompt ->
                value = prompt
            }
        }
    }


    Log.d("Playground:Prompts", prompt)

    var input by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Column {
            Text(text = "List Approach", modifier = Modifier.padding(8.dp))
            TextField(
                value = input,
                onValueChange = { input = it },
                placeholder = {
                    AnimatedContent(
                        targetState = prompt,
                        transitionSpec = {
                            slideInVertically(animationSpec = tween(800)) { height -> height } +
                                    fadeIn(animationSpec = tween(800)) togetherWith
                                    slideOutVertically(animationSpec = tween(800)) { height -> -height } +
                                    fadeOut(animationSpec = tween(800)) using SizeTransform(
                                clip = false
                            )
                        },
                    ) {
                        Text(text = it)
                    }
                }
            )
        }
    }
}

/**
 * NEXT ARTICLE
 * This approach uses a [List] of Strings and a [Pair] to hold the CURRENT and NEXT prompts.
 * [produceState] emits ALWAYS the previous value before emitting the new one.
 */
@Composable
fun PromptsListPairApproach(modifier: Modifier = Modifier, viewModel: PromptsViewModel) {
    val data = viewModel.prompts
    val initialValue = PromptsData("", data.first())

    val transitionPair by produceState(initialValue = initialValue, key1 = data) {
        data.loop { currentPrompt, nextPrompt ->
            value = PromptsData(currentPrompt, nextPrompt)
        }
    }

    Log.d("Playground:Prompts", transitionPair.toString())

    val animatedTermState =
        remember(transitionPair) {
            MutableTransitionState(transitionPair.currentPrompt).apply {
                targetState = transitionPair.nextPrompt
            }
        }
    val animatedTermTransition = rememberTransition(animatedTermState)

    var input by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Column {
            Text(text = "List Pair-Approach", modifier = Modifier.padding(8.dp))
            TextField(
                value = input,
                onValueChange = { input = it },
                placeholder = {
                    animatedTermTransition.AnimatedContent(
                        transitionSpec = {
                            slideInVertically(animationSpec = tween(800)) { height -> height } +
                                    fadeIn(animationSpec = tween(800)) togetherWith
                                    slideOutVertically(animationSpec = tween(800)) { height -> -height } +
                                    fadeOut(animationSpec = tween(800)) using SizeTransform(
                                clip = false
                            )
                        },
                    ) {
                        Text(text = it)
                    }
                }
            )
        }
    }
}

/**
 * NEXT ARTICLE
 * This approach uses a [kotlinx.coroutines.flow.Flow] from the [PromptsViewModel] returned by
 * [com.example.playground.data.prompts.PromptsRepository]. I use the [PromptsData] to hold the
 * current and next prompts. [produceState] emits ALWAYS the previous value before emitting the
 * new one.
 */
@Composable
fun PromptsFlowApproach(modifier: Modifier = Modifier, viewModel: PromptsViewModel) {
    val promptsData by viewModel.promptsFlow.collectAsStateWithLifecycle(
        viewModel.getFirstPrompt()
    )

    val animatedTermState = remember(promptsData) {
        MutableTransitionState(promptsData.currentPrompt).apply {
            targetState = promptsData.nextPrompt
        }
    }
    val animatedTermTransition = rememberTransition(animatedTermState)

    var input by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(text = "Flow Approach", modifier = Modifier.padding(8.dp))

        Row {
            TextField(
                value = input,
                onValueChange = { input = it },
                placeholder = {
                    animatedTermTransition.AnimatedContent(
                        transitionSpec = {
                            slideInVertically(animationSpec = tween(800)) { height -> height } +
                                    fadeIn(animationSpec = tween(800)) togetherWith
                                    slideOutVertically(animationSpec = tween(800)) { height -> -height } +
                                    fadeOut(animationSpec = tween(800)) using SizeTransform(
                                clip = false
                            )
                        },
                    ) {
                        Text(text = it)
                    }
                }
            )
        }
    }
}
