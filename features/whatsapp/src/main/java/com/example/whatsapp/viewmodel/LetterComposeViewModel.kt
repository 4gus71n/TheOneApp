package com.example.whatsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.RecipientContactState
import com.example.whatsapp.models.LetterRecipient
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class LetterComposeViewModel @Inject constructor() : ViewModel() {
    val recentState = MutableLiveData<RecipientContactState>()
    val addressBookState = MutableLiveData<RecipientContactState>()
    val searchState = MutableLiveData<RecipientContactState>()

    // TODO Remove
    private val _addressBookList = generateRandomList("[AB]")
    private val _searchResults = generateRandomList("[SR]")
    private val _recentList = mutableListOf<LetterRecipient>()

    fun performSearch(query: String) {
        searchState.value = RecipientContactState.RecipientsSuccessfullyFetched(
            list = _searchResults
        )
    }

    private fun generateRandomList(prefix: String): MutableList<LetterRecipient> {
        return mutableListOf<LetterRecipient>().apply {
            repeat(50) {
                add(
                    LetterRecipient(
                        fullName = "$prefix Long long long long name",
                        fullAddress = "$prefix Long long long address",
                        initials = "QA"
                    )
                )
            }
        }
    }

    fun fetchContacts() {
        addressBookState.value = RecipientContactState.RecipientsSuccessfullyFetched(
            list = _addressBookList
        )
        recentState.value = RecipientContactState.RecipientsSuccessfullyFetched(
            list = _recentList
        )
    }

    fun addNewContact() {
        _addressBookList.add(
            LetterRecipient(
                fullName = "${Date()} NEW Long long long long name",
                fullAddress = "${Date()} NEW Long long long address",
                initials = "QA"
            )
        )
        addressBookState.value = RecipientContactState.RecipientsSuccessfullyFetched(
            list = _addressBookList
        )
    }

    fun addContactAsRecent(it: LetterRecipient) {
        _recentList.add(it)
        recentState.value = RecipientContactState.RecipientsSuccessfullyFetched(
            list = _recentList
        )
    }
}