@file:OptIn(ExperimentalPagerApi::class)

package com.example.chatexample.ui.main

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.example.base_design.ui.TheOneAppTheme
import com.example.chatexample.ui.main.viewmodel.MessageExampleViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun ChatExampleScreen(
    onLaunchMediaPicker: (() -> Unit),
    onPullToRefresh: (() -> Unit),
    dashboardExampleScreenImageUrl: Uri,
    messageExampleScreenIsLoading: Boolean,
    messageExampleScreenUiState: MessageExampleViewModel.State
) {
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
                        .padding(it)
                ) {
                    val pagerState = rememberPagerState()
                    val scope = rememberCoroutineScope()

                    TabRow(
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
                            onClick = {
                                scope.launch {
                                    pagerState.scrollToPage(0, 0f)
                                }
                            },
                            text = {
                                Text(
                                    text = "Messages"
                                )
                            }
                        )
                        Tab(
                            selected = pagerState.currentPage == 1,
                            onClick = {
                                scope.launch {
                                    pagerState.scrollToPage(1, 0f)
                                }
                            },
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clipToBounds(),
                    ) { page ->
                        when (page) {
                            0 -> {
                                MessageExampleScreen(
                                    uiState = messageExampleScreenUiState,
                                    isLoadingState = messageExampleScreenIsLoading,
                                    onPullToRefresh = onPullToRefresh,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            1 -> {
                                DashboardExampleScreen(
                                    imageUrl = dashboardExampleScreenImageUrl,
                                    onChangePicture = onLaunchMediaPicker,
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

@Composable
fun TopAppBarSample(){
    Column {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Chat")
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