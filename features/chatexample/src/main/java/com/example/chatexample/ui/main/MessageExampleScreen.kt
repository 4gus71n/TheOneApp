@file:OptIn(ExperimentalMaterialApi::class)

package com.example.chatexample.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.base_design.ui.TheOneAppTheme
import com.example.chatexample.ui.designsystem.ErrorLayout
import com.example.core.model.ChatExampleMessage
import com.example.chatexample.ui.main.viewmodel.MessageExampleViewModel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun MessageExampleScreen(
    uiState: MessageExampleViewModel.State,
    isLoadingState: Boolean,
    onPullToRefresh: (() -> Unit) = {},
    modifier: Modifier = Modifier
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoadingState,
        onRefresh = onPullToRefresh
    )

    ConstraintLayout(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        val (content, pullRefresh) = createRefs()

        Box(
            modifier = Modifier.constrainAs(content) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
        ) {
            when (uiState) {
                is MessageExampleViewModel.State.SuccessfullyLoadedMessages -> {
                    ChatExampleScreenSuccessfullyLoadedMessages(
                        chatexampleMessages = uiState.list,
                        modifier = modifier,
                    )
                }
                is MessageExampleViewModel.State.NoMessagesFetched -> {
                    ErrorLayout(
                        errorMessage = "No messages so far.",
                        modifier = modifier
                    )
                }
                is MessageExampleViewModel.State.NoInternetConnectivity -> {
                    ErrorLayout(
                        errorMessage = "No internet connectivity. Try again later.",
                        modifier = modifier
                    )
                }
                else -> {
                    // Agus - Do nothing???
                    Box(modifier = modifier.fillMaxSize())
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isLoadingState,
            state = pullRefreshState,
            modifier = Modifier.constrainAs(pullRefresh) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
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