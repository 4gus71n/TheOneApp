package com.example.chatexample.ui.main.viewmodel

import android.net.Uri
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
class DashboardExampleViewModel @Inject constructor(
    private val fetchMessagesUseCase: FetchMessagesUseCase
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val profileImageUri = MutableLiveData<Uri>()

    fun fetchUserDetails() {
        viewModelScope.launch {
            isLoading.value = true
            profileImageUri.value = Uri.parse("https://img.freepik.com/free-vector/cute-astronaut-flying-space-cartoon-icon-illustration_138676-2702.jpg?w=2000")
            isLoading.value = false
        }
    }

    fun updateProfileImageUrl(imageUri: Uri?) {
        imageUri?.let { profileImageUri.value = it }
    }
}