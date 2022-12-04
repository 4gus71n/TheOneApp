@file:OptIn(ExperimentalPagerApi::class)

package com.example.chatexample.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.base_design.ui.TheOneAppTheme
import com.example.chatexample.ui.main.viewmodel.MessageExampleViewModel
import com.example.chatexample.ui.main.viewmodel.DashboardExampleViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint

// Agus
// https://google.github.io/accompanist/pager/

@AndroidEntryPoint
class ChatExampleActivity : ComponentActivity() {

    private lateinit var pickVisualMedialLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    private val viewModel: MessageExampleViewModel by viewModels()
    private val dashboardViewModel: DashboardExampleViewModel by viewModels()

    private fun onLaunchMediaPicker() {
        pickVisualMedialLauncher.launch(
            PickVisualMediaRequest(
                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickVisualMedialLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            dashboardViewModel.updateProfileImageUrl(uri)
        }

        setContent {
            ChatExampleScreen(
                onPullToRefresh = { viewModel.fetchMessages() },
                onLaunchMediaPicker = { onLaunchMediaPicker() },
                messageExampleScreenIsLoading = viewModel.isLoading.observeAsState(initial = true),
                messageExampleScreenUiState = viewModel.state.observeAsState(initial = MessageExampleViewModel.State.NoMessagesFetched),
                dashboardExampleScreenImageUrl = dashboardViewModel.profileImageUri.observeAsState(
                    initial = Uri.parse("")
                )
            )
        }

        viewModel.fetchMessages()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ChatExampleActivity::class.java)
        }
    }
}