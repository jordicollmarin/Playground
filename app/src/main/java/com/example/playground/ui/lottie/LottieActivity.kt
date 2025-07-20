package com.example.playground.ui.lottie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.playground.ui.theme.PlaygroundTheme

class LottieActivity : ComponentActivity() {
    private val viewModel = LottieViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val uiState by viewModel.animationUiState.observeAsState()

            PlaygroundTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination?.route

                                currentDestination?.let { Text(it) }
                            },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = LottieRoutes.Fragment1.screenRoute
                    ) {
                        composable(route = LottieRoutes.Fragment1.screenRoute) {
                            Fragment1Screen(
                                fragmentOneOnClick = {
                                    navController.navigate(LottieRoutes.Fragment2.screenRoute)
                                },
                                fragmentOneTwoOnClick = {
                                    navController.navigate(LottieRoutes.Fragment3.screenRoute)
                                },
                                initialOnClick = {
                                    viewModel.setInitial()
                                },
                                inProgressOnClick = {
                                    viewModel.setInProgress()
                                },
                                doneOnClick = {
                                    viewModel.setDone(it)
                                },
                                uiState = uiState
                            )
                        }
                        composable(route = LottieRoutes.Fragment2.screenRoute) {
                            Fragment2Screen(
                                initialOnClick = {
                                    viewModel.setInitial()
                                },
                                inProgressOnClick = {
                                    viewModel.setInProgress()
                                },
                                doneOnClick = {
                                    viewModel.setDone(it)
                                },
                                uiState = uiState
                            )
                        }
                        composable(route = LottieRoutes.Fragment3.screenRoute) {
                            Fragment3Screen(
                                initialOnClick = {
                                    viewModel.setInitial()
                                },
                                inProgressOnClick = {
                                    viewModel.setInProgress()
                                },
                                doneOnClick = {
                                    viewModel.setDone(it)
                                },
                                uiState = uiState
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class LottieRoutes(
    val screenRoute: String,
) {
    Fragment1("AnimationScreen"),
    Fragment2("Fragment2"),
    Fragment3("Fragment3"),
}

/*@Composable
private fun Tabs(
    modifier: Modifier,
    selectedTabIndex: Int,
    scrollToItem: (String) -> Unit,
    tabItems: List<String>
) {
    ScrollableTabRow(modifier = modifier, selectedTabIndex = selectedTabIndex) {
        tabItems.forEachIndexed { index, item ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { scrollToItem(item) }
            ) {
                Text(text = item)
            }
        }
    }
}*/

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlaygroundTheme {
        Tabs(
            modifier = Modifier,
            selectedTabIndex = 0,
            scrollToItem = {
                Log.d("TUS UN MAKINA", it)
            },
            tabItems = listOf("Test 1, Test 2, Test 3, Test 4, Test 5, Test 2, Test 3, Test 4, Test 5, Test 2, Test 3, Test 4, Test 5")
        )
    }
}*/
