package com.example.chatexample.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.ChatExampleMessage
import com.example.chatexample.usecase.FetchMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatExampleViewModel @Inject constructor(
    private val fetchMessagesUseCase: FetchMessagesUseCase
) : ViewModel() {
    sealed class State {
        data class SuccessfullyLoadedMessages(
            val list: List<ChatExampleMessage>
        ) : State()
        object NoMessagesFetched : State()
        object NoInternetConnectivity : State()
    }

    sealed class MessageState {
        object UnknownError : MessageState()
        object NoInternetConnectivity : MessageState()
    }

    val state = MutableLiveData<State>()
    val messages = MutableLiveData<MessageState>()
    val isLoading = MutableLiveData<Boolean>()

    fun fetchMessages() {
        viewModelScope.launch {
            isLoading.value = true
            fetchMessagesUseCase().collectLatest {
                when (it) {
                    is FetchMessagesUseCase.Callback.Success -> {
                        state.value = State.SuccessfullyLoadedMessages(it.list)
                    }
                    else -> {
                        state.value = State.NoMessagesFetched
                    }
                }
                isLoading.value = false
            }
        }
    }
}