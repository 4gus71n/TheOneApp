package com.example.whatsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.RecipientContactState
import com.example.whatsapp.models.LetterRecipient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LetterComposeViewModel @Inject constructor() : ViewModel() {
    val recentState = MutableLiveData<RecipientContactState>()
    val addressBookState = MutableLiveData<RecipientContactState>()
    val searchState = MutableLiveData<RecipientContactState>()

    fun performSearch(it: String) {
        searchState.value = RecipientContactState.RecipientsSuccessfullyFetched(
            list = generateRandomList()
        )
    }

    private fun generateRandomList(): List<LetterRecipient> {
        return mutableListOf<LetterRecipient>().apply {
            repeat(50) {
                add(
                    LetterRecipient(
                        fullName = "Long long long long name",
                        fullAddress = "Long long long address",
                        initials = "QA"
                    )
                )
            }
        }
    }

    fun fetchContacts() {
        addressBookState.value = RecipientContactState.RecipientsSuccessfullyFetched(
            list = generateRandomList()
        )
        recentState.value = RecipientContactState.RecipientsSuccessfullyFetched(
            list = generateRandomList()
        )
    }
}