package com.example.playground.ui.tabs

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playground.ui.theme.PlaygroundTheme

class TabsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaygroundTheme {
                Scaffold { innerPadding ->
                    Tabs(
                        modifier = Modifier.padding(innerPadding),
                        selectedTabIndex = 1,
                        scrollToItem = {
                            Log.d("TUS UN MAKINA", it)
                            // RecyclerView, scroll to the Category with the name/id 'it'
                        },
                        tabItems = listOf(
                            "Test 1",
                            "Test 2",
                            "Test 3",
                            "Test 4",
                            "Test 5",
                            "Test 2",
                            "Test 3",
                            "Test 4",
                            "Test 5"
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun Tabs(
    modifier: Modifier,
    selectedTabIndex: Int,
    scrollToItem: (String) -> Unit,
    tabItems: List<String>
) {
    var selectedTab by remember { mutableIntStateOf(selectedTabIndex) }

    ScrollableTabRow(modifier = modifier, selectedTabIndex = selectedTab) {
        tabItems.forEachIndexed { index, item ->
            Tab(
                modifier = Modifier.height(48.dp),
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTab = index
                    scrollToItem(item)
                }
            ) {
                Text(text = item)
            }
        }
    }
}


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
}