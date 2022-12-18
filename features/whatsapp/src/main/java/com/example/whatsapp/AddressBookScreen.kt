@file:OptIn(ExperimentalPagerApi::class)

package com.example.whatsapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.whatsapp.models.LetterRecipient
import com.example.whatsapp.utils.toHslColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun RecipientTabPager(
    padding: PaddingValues = PaddingValues(0.dp),
    recentRecipientState: RecipientContactState = RecipientContactState.NoRecentRecipients,
    addressBookRecipientState: RecipientContactState = RecipientContactState.NoRecentRecipients,
    onAddNewContactClicked: (() -> Unit) = {}
) {
    Column(modifier = Modifier.padding(padding)) {

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
                        text = "Recent"
                    )
                }
            )
            Tab(
                selected = pagerState.currentPage == 1,
                onClick = {  },
                text = {
                    Text(
                        text = "Address Book"
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
                    RecipientContactPage(
                        state = recentRecipientState,
                        onAddNewContactClicked = onAddNewContactClicked
                    )
                }
                1 -> {
                    RecipientContactPage(
                        state = addressBookRecipientState,
                        onAddNewContactClicked = onAddNewContactClicked
                    )
                }
            }
        }
    }
}

@Composable
fun RecipientContactPage(
    state: RecipientContactState = RecipientContactState.NoRecentRecipients,
    onAddNewContactClicked: (() -> Unit) = {},
    onContactClicked: ((LetterRecipient) -> Unit) = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is RecipientContactState.NoRecentRecipients -> {
                RecipientEmptyPage(
                    onAddNewContactClicked = onAddNewContactClicked
                )
            }
            is RecipientContactState.RecipientsSuccessfullyFetched -> {
                RecentRecipientFullPage(
                    contactList = state.list,
                    onContactClicked = onContactClicked,
                )
            }
            is RecipientContactState.Error -> {
                RecipientErrorPage()
            }
        }
    }
}

@Composable
fun RecipientEmptyPage(
    onAddNewContactClicked: (() -> Unit) = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = Icons.Rounded.Info, contentDescription = "Error icon")
        Text(
            text = stringResource(id = R.string.no_recipients_title),
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1
        )
        Row(
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    onAddNewContactClicked()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add Contact",
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = stringResource(id = R.string.add_new_contact),
                color = MaterialTheme.colors.primary,
                modifier = Modifier.wrapContentSize(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}



@Composable
fun RecentRecipientFullPage(
    contactList: List<LetterRecipient> = emptyList(),
    onContactClicked: ((LetterRecipient) -> Unit) = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        contactList.forEach { recipientItem -> item {
            RecipientItem(
                onContactClicked = {
                    onContactClicked(recipientItem)
                },
                recipient = recipientItem
            )
        } }
    }
}

@Composable
fun AddressBookRecipientPage() {
    Box(modifier = Modifier)
}

@Composable
fun RecipientItem(
    recipient: LetterRecipient,
    onContactClicked: ((LetterRecipient) -> Unit) = {}
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onContactClicked(recipient)
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserHead(initials = recipient.initials)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = recipient.fullName,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = recipient.fullAddress,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun UserHead(
    initials: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
) {
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        val initials = initials.let { if (it.length > 2 ) it.take(2) else it }
        val color = remember(initials) {
            val init = initials.uppercase()
            Color("$init".toHslColor())
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(color))
        }
        Text(text = initials, style = textStyle, color = Color.White)
    }
}

@Composable
fun RecipientErrorPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = Icons.Rounded.Warning, contentDescription = "Error icon")
        Text(
            text = stringResource(id = R.string.error_title),
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = stringResource(id = R.string.error_subtitle),
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RecipientTabPager_Error() {
    RecipientTabPager(
        recentRecipientState = RecipientContactState.NoRecentRecipients,
        addressBookRecipientState = RecipientContactState.Error
    )
}

@Composable
@Preview(showBackground = true)
fun RecipientTabPager_EmptyFull() {
    RecipientTabPager(
        recentRecipientState = RecipientContactState.NoRecentRecipients,
        addressBookRecipientState = RecipientContactState.RecipientsSuccessfullyFetched(
            list = mutableListOf<LetterRecipient>().apply {
                repeat(10) {
                    add(
                        LetterRecipient(
                            initials = "ASDASDSA",
                            fullName = "Long Name Long Name Long Name Long Name Long Name Long Name Long Name ",
                            fullAddress = "Long Address Long Address Long Address Long Address Long Address "
                        )
                    )
                }
            }
        )
    )
}


sealed class RecipientContactState {
    object NoRecentRecipients : RecipientContactState()
    data class RecipientsSuccessfullyFetched(
        val list: List<LetterRecipient>
    ) : RecipientContactState()
    object Error : RecipientContactState()
}