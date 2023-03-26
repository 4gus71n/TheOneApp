package com.example.chatexample.usecase

import com.example.core.model.ChatExampleMessage
import com.example.core.model.UserProfile
import com.example.core.repositories.MockRepository
import com.example.core.repositories.ResultResponse
import com.example.core.repositories.isSuccessful
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FetchUserProfileUseCase(
    private val mockRepository: MockRepository
) {
    sealed class Callback {
        data class Success(
            val profile: UserProfile,
            val profileImageUrl: String?
        ) : Callback()
        object Error : Callback()
    }

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val userProfileResponse = mockRepository.fetchUserProfile()
        if (!userProfileResponse.isSuccessful()) {
            return@withContext Callback.Error
        }
        val userProfile = userProfileResponse.data
        val imageUrlResponse = mockRepository.fetchUserProfileImage(userProfile.id)
        val imageUrl = if (imageUrlResponse.isSuccessful()) {
            imageUrlResponse.data
        } else {
            null
        }
        Callback.Success(userProfile, imageUrl)
    }
}