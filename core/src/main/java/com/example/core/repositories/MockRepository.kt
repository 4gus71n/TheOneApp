package com.example.core.repositories

import com.example.core.model.ChatExampleMessage
import com.example.core.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class ResultResponse<out Z : Any> {
    data class Failure (
        val errorMessage: String
    ) : ResultResponse<Nothing>()

    data class Success<out P : Any>(
        val data: P
    ) : ResultResponse<P>()
}

@OptIn(ExperimentalContracts::class)
fun <T : Any> ResultResponse<T>.isSuccessful(): Boolean {
    contract {
        returns(false) implies (this@isSuccessful is ResultResponse.Failure)
        returns(true) implies (this@isSuccessful is ResultResponse.Success)
    }
    return this@isSuccessful is ResultResponse.Success
}

class MockRepository {

    suspend fun fetchUserProfile(): ResultResponse<UserProfile> = withContext(Dispatchers.IO) {
        delay(2000)
        return@withContext ResultResponse.Success(
            data = UserProfile(
                id = "123222321",
                fullName = "Peter Jackson",
                emailAddress = "peter@jackson.com",
                address = "123 Liberty Av.",
                state = "CA",
                city = "Los Angeles",
                zipCode = "12345"
            )
        )
    }

    suspend fun fetchUserProfileImage(id: String): ResultResponse<String> = withContext(Dispatchers.IO) {
        delay(2000)
        return@withContext ResultResponse.Success(
            data = "someUrl.com/someImage.png"
        )
    }

    suspend fun fetchChatExampleMessages() = withContext(Dispatchers.IO) {
        return@withContext listOf(
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date()),
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date()),
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date()),
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date()),
            ChatExampleMessage(text = "Test1", author = "Joe", date = Date())
        )
    }

}