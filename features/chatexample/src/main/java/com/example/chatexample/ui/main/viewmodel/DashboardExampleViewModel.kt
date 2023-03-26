package com.example.chatexample.ui.main.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.ChatExampleMessage
import com.example.chatexample.usecase.FetchMessagesUseCase
import com.example.chatexample.usecase.FetchUserProfileUseCase
import com.example.core.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardExampleViewModel @Inject constructor(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase
) : ViewModel() {

    sealed class State {
        object Loading : State()
        data class SuccessfullyFetchedProfile(
            val userProfile: UserProfile,
            val profileUri: Uri? = null
        ) : State()
        data class UnknownError(
            val errorMessage: String
        ) : State()
    }

    val state = MutableLiveData<State>()

    private var _userProfile: UserProfile? = null

    fun fetchUserDetails() {
        viewModelScope.launch {
            state.value = State.Loading
            when (val result = fetchUserProfileUseCase()) {
                is FetchUserProfileUseCase.Callback.Success -> {
                    _userProfile = result.profile
                    state.value = State.SuccessfullyFetchedProfile(
                        userProfile = result.profile,
                        profileUri = result.profileImageUrl?.let { Uri.parse(it) }
                    )
                }
                is FetchUserProfileUseCase.Callback.Error -> {
                    state.value =  State.UnknownError("")
                }
            }
        }
    }

    fun updateProfileImageUrl(imageUri: Uri?) {
        val userProfile = _userProfile
        if (userProfile != null) {
            state.value = State.SuccessfullyFetchedProfile(
                profileUri = imageUri,
                userProfile = userProfile
            )
        }
    }
}