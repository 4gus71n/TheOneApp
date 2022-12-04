@file:OptIn(ExperimentalPagerApi::class)

package com.example.chatexample.ui.main

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.base_design.ui.TheOneAppTheme
import com.example.chatexample.ui.main.viewmodel.MessageExampleViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun ChatExampleScreen(
    onLaunchMediaPicker: (() -> Unit),
    onPullToRefresh: (() -> Unit),
    dashboardExampleScreenImageUrl: State<Uri>,
    messageExampleScreenIsLoading: State<Boolean>,
    messageExampleScreenUiState: State<MessageExampleViewModel.State>
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