package com.example.chatexample.usecase

import com.example.core.model.ChatExampleMessage
import com.example.core.repositories.MockRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchMessagesUseCase(
    private val mockRepository: MockRepository
) {
    sealed class Callback {
        data class Success(
            val list: List<ChatExampleMessage>
        ) : Callback()
        object Error : Callback()
    }

    operator fun invoke(): Flow<Callback> {
        return flow {
            delay(4000L)
            try {
                val result = mockRepository.fetchChatExampleMessages()
                emit(Callback.Success(result))
            } catch (e: Throwable) {
                emit(Callback.Error)
            }
        }.flowOn(Dispatchers.IO)
    }
}