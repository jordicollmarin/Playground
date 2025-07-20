package com.example.playground.ui.prompts

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.playground.extensions.loop
import com.example.playground.ui.theme.PlaygroundTheme

class PromptsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaygroundTheme {
                Scaffold { innerPadding ->
                    var input by remember { mutableStateOf("") }
                    val transitionDetails = produceState("First prompt") {
                        listOf(
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
                        ).loop {
                            value = it
                        }
                    }

                    val animatedState = remember { MutableTransitionState(transitionDetails) }
                    val animatedTransition = rememberTransition(animatedState)

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        animatedTransition.AnimatedContent(
                        ) {
                            TextField(
                                value = input,
                                onValueChange = { text ->
                                    input = text
                                    Log.d("Jordi", "Clicked!")
                                },
                                placeholder = {
                                    Text(text = it.value)
                                },
                            )
                        }

                    }
                }
            }
        }
    }
}