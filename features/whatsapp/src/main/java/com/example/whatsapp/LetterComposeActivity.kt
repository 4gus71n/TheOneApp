@file:OptIn(ExperimentalPagerApi::class)

package com.example.whatsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import com.example.whatsapp.viewmodel.LetterComposeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LetterComposeActivity : ComponentActivity() {

    private val viewModel by viewModels<LetterComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val recentState by viewModel.recentState.observeAsState(
                initial = RecipientContactState.NoRecentRecipients
            )
            val addressBookState by viewModel.addressBookState.observeAsState(
                initial = RecipientContactState.NoRecentRecipients
            )
            val searchState by viewModel.searchState.observeAsState(
                initial = RecipientContactState.NoRecentRecipients
            )
            LetterComposeScreen(
                recentRecipientState = recentState,
                addressBookRecipientState = addressBookState,
                recipientSearchState = searchState,
                onAddNewContactClicked = {
                    // Start an Activity to somewhere else.
                },
                onSearchQueryChanged = {
                    viewModel.performSearch(it)
                },
                onContactSelected = {
                    // Navigate to the next ste[
                }
            )
        }
        viewModel.fetchContacts()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LetterComposeActivity::class.java)
        }
    }
}