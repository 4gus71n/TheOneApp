@file:OptIn(ExperimentalPagerApi::class)

package com.example.whatsapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.base_design.ui.TheOneAppTheme
import com.example.whatsapp.models.LetterRecipient
import com.google.accompanist.pager.ExperimentalPagerApi

@Composable
fun LetterComposeScreen(
    recentRecipientState: RecipientContactState = RecipientContactState.NoRecentRecipients,
    addressBookRecipientState: RecipientContactState = RecipientContactState.NoRecentRecipients,
    onAddNewContactClicked: (() -> Unit) = {},
    recipientSearchState: RecipientContactState = RecipientContactState.NoRecentRecipients,
    onContactSelected: ((LetterRecipient) -> Unit) = {},
    onSearchQueryChanged: ((String) -> Unit) = {}
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
                Column(modifier = Modifier.fillMaxHeight()) {
                    val rememberQuery = rememberSaveable { mutableStateOf("") }
                    TextField(
                        value = rememberQuery.value,
                        onValueChange = {
                            rememberQuery.value = it
                            onSearchQueryChanged(it)
                        }
                    )
                    if (rememberQuery.value.isNotBlank()) {
                        ContactSearchResults(
                            state = recipientSearchState,
                            onContactClicked = onContactSelected
                        )
                    } else {
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp))
                        ClickToActionItem(
                            imageVector = Icons.Default.Add,
                            text = stringResource(id = R.string.new_contact_cta)
                        )
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp))
                        ClickToActionItem(
                            imageVector = Icons.Default.AccountCircle,
                            text = stringResource(id = R.string.import_contacts_cta)
                        )
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp))
                        RecipientTabPager(
                            padding = it,
                            recentRecipientState = recentRecipientState,
                            addressBookRecipientState = addressBookRecipientState,
                            onAddNewContactClicked = onAddNewContactClicked
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContactSearchResults(
    state: RecipientContactState = RecipientContactState.NoRecentRecipients,
    onContactClicked: ((LetterRecipient) -> Unit) = {},
    onAddNewContactClicked: (() -> Unit) = {},
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (state) {
            is RecipientContactState.NoRecentRecipients -> {
                RecipientEmptyPage(
                    onAddNewContactClicked = onAddNewContactClicked
                )
            }
            is RecipientContactState.Error -> {
                RecipientErrorPage()
            }
            is RecipientContactState.RecipientsSuccessfullyFetched -> {
                RecipientContactPage(
                    state = state,
                    onAddNewContactClicked = onAddNewContactClicked,
                    onContactClicked = onContactClicked
                )
            }
        }
    }
}

@Composable
fun ClickToActionItem(
    imageVector: ImageVector = Icons.Default.Add,
    text: String = "Click Me!",
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(16.dp)
    ) {
        Icon(imageVector = imageVector, contentDescription = "CTA Icon")
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
@Preview
fun ClickToActionItem_Preview() {
    ClickToActionItem()
}

@Composable
fun TopAppBarSample() {
    Column {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Recipient")
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

sealed class RecipientSearchState {
    data class RecipientsSuccessfullyFetched(
        val data: List<LetterRecipient>
    ) : RecipientSearchState()
    object NoResults : RecipientSearchState()
    object Error : RecipientSearchState()
}