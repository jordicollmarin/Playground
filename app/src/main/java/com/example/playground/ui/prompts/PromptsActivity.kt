package com.example.playground.ui.prompts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.playground.ui.theme.PlaygroundTheme

class PromptsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaygroundTheme {
                val promptsViewModel = PromptsViewModel()
                PromptsScreen(promptsViewModel)
            }
        }
    }
}