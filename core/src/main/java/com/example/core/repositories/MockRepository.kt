package com.example.core.repositories

import com.example.core.model.ChatExampleMessage
import java.util.Date

class MockRepository {

    suspend fun fetchChatExampleMessages(): List<ChatExampleMessage> {
        return listOf(
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date()),
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date()),
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date()),
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date()),
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date())
        )
    }

}