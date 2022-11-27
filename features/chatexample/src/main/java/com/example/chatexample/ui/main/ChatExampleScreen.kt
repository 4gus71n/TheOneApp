@file:OptIn(ExperimentalMaterialApi::class)

package com.example.chatexample.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.base_design.ui.TheOneAppTheme
import com.example.core.model.ChatExampleMessage
import com.example.chatexample.ui.main.viewmodel.ChatExampleViewModel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ChatExampleScreen(
    chatexampleViewModel: ChatExampleViewModel,
    modifier: Modifier = Modifier
) {
    val chatexampleViewModelState by chatexampleViewModel.state.observeAsState()
    val chatexampleViewModelIsLoadingState by chatexampleViewModel.isLoading.observeAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = chatexampleViewModelIsLoadingState == true,
        onRefresh = { chatexampleViewModel.fetchMessages() }
    )

    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        when (val result = chatexampleViewModelState) {
            is ChatExampleViewModel.State.SuccessfullyLoadedMessages -> {
                ChatExampleScreenSuccessfullyLoadedMessages(
                    chatexampleMessages = result.list,
                    modifier = modifier,
                )
            }
            is ChatExampleViewModel.State.NoMessagesFetched -> {
                ChatExampleScreenEmptyState(
                    modifier = modifier
                )
            }
            is ChatExampleViewModel.State.NoInternetConnectivity -> {
                NoInternetConnectivityScreen(
                    modifier = modifier
                )
            }
            else -> {
                // Agus - Do nothing???
                Box(modifier = modifier.fillMaxSize())
            }
        }
        PullRefreshIndicator(chatexampleViewModelIsLoadingState == true, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun NoInternetConnectivityScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "No internet. Try again later.",
            style = MaterialTheme.typography.h6,
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ChatExampleScreenEmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "No messages so far.",
            style = MaterialTheme.typography.h6,
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ChatExampleScreenSuccessfullyLoadedMessages(
    modifier: Modifier = Modifier,
    chatexampleMessages: List<ChatExampleMessage>,
    onMessageClicked: ((ChatExampleMessage) -> Unit) = {}
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            chatexampleMessages.forEach {
                item {
                    ChatExampleMessageItem(
                        chatexampleMessage = it
                    )
                }
            }
        }
    }
}

fun formatDate(date: Date): String {
    return SimpleDateFormat("hh:mm:SS dd/MM/YYY").format(date)
}

@Composable
fun ChatExampleMessageItem(
    modifier: Modifier = Modifier,
    chatexampleMessage: ChatExampleMessage
) {
    Card(modifier = modifier.wrapContentHeight()) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                text = "From: ${chatexampleMessage.author} - at ${formatDate(chatexampleMessage.date)}",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = chatexampleMessage.text,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatExampleMessageItem_Preview() {
    TheOneAppTheme {
        ChatExampleMessageItem(
            chatexampleMessage = ChatExampleMessage(
                text = "Lorem ipsum",
                date = Date(),
                author = "Joe"
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatExampleScreenEmptyState_Preview() {
    TheOneAppTheme {
        ChatExampleScreenSuccessfullyLoadedMessages(
            chatexampleMessages = listOf(
                ChatExampleMessage(
                    text = "Lorem ipsum #1",
                    date = Date(),
                    author = "Joe"
                ),
                ChatExampleMessage(
                    text = "Lorem ipsum #2",
                    date = Date(),
                    author = "Joe"
                ),
                ChatExampleMessage(
                    text = "Lorem ipsum #3",
                    date = Date(),
                    author = "Joe"
                ),
                ChatExampleMessage(
                    text = "Lorem ipsum #4",
                    date = Date(),
                    author = "Joe"
                )
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NoInternetConnectivityScreen_Preview() {
    TheOneAppTheme {
        NoInternetConnectivityScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatExampleScreenSuccessfullyLoadedMessages_Preview() {
    TheOneAppTheme {
        ChatExampleScreenEmptyState()
    }
}