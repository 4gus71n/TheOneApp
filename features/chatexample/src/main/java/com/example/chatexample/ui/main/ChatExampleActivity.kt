@file:OptIn(ExperimentalPagerApi::class)

package com.example.chatexample.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.example.base_design.ui.TheOneAppTheme
import com.example.chatexample.ui.main.viewmodel.ChatExampleViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint

// Agus
// https://google.github.io/accompanist/pager/

@AndroidEntryPoint
class ChatExampleActivity : ComponentActivity() {

    private val viewModel: ChatExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheOneAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { TopAppBarSample() }
                    ) {

                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(it)) {
                            val pagerState = rememberPagerState()

                            ScrollableTabRow(
                                selectedTabIndex = pagerState.currentPage,
                                indicator = { tabPositions ->
                                    TabRowDefaults.Indicator(
                                        modifier = Modifier.tabIndicatorOffset(
                                            currentTabPosition = tabPositions[pagerState.currentPage],
                                        )
                                    )
                                }
                            ) {
                                Tab(
                                    selected = pagerState.currentPage == 0,
                                    onClick = {  },
                                    text = {
                                        Text(
                                            text = "Messages"
                                        )
                                    }
                                )
                                Tab(
                                    selected = pagerState.currentPage == 1,
                                    onClick = {  },
                                    text = {
                                        Text(
                                            text = "Dashboard"
                                        )
                                    }
                                )
                            }

                            HorizontalPager(
                                count = 2,
                                state = pagerState,
                                modifier = Modifier.fillMaxWidth(),
                            ) { page ->
                                when (page) {
                                    0 -> {
                                        ChatExampleScreen(
                                            chatexampleViewModel = viewModel,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    1 -> {
                                        ChatExampleScreen(
                                            chatexampleViewModel = viewModel,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        viewModel.fetchMessages()
    }

    @Composable
    fun TopAppBarSample(){
        Column {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text("ChatExampleActivity")
                },
                backgroundColor =  MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }, actions = {
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.Share, null)
                    }
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.Settings, null)
                    }
                })

        }
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ChatExampleActivity::class.java)
        }
    }
}