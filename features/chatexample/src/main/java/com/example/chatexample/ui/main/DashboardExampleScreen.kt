@file:OptIn(ExperimentalMaterialApi::class)

package com.example.chatexample.ui.main

import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatexample.R
import com.example.chatexample.ui.designsystem.ErrorLayout
import com.example.chatexample.ui.main.viewmodel.DashboardExampleViewModel
import com.example.core.model.UserProfile
import java.util.*

// Note: Using JP kinda limits the amount of third-party libraries we are able to use, what would
// happen if we want to use a photo-picker library here?

@Composable
fun DashboardExampleScreen(
    state: DashboardExampleViewModel.State,
    onChangePicture: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Crossfade(
        targetState = state,
        animationSpec = tween(durationMillis = 1000)
    ) { state ->
        when (state) {
            is DashboardExampleViewModel.State.Loading -> {
                LoadingLayout(
                    modifier = modifier
                )
            }
            is DashboardExampleViewModel.State.UnknownError -> {
                ErrorLayout(
                    errorMessage = "Something went wrong. Try again later.",
                    modifier = modifier
                )
            }
            is DashboardExampleViewModel.State.SuccessfullyFetchedProfile -> {
                DashboardProfileScreen(
                    state = state,
                    onChangePicture = onChangePicture
                )
            }
        }
    }
}

@Composable
fun LoadingLayout(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = colorResource(id = R.color.black_overlay))
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}

@Composable
fun DashboardProfileScreen(
    state: DashboardExampleViewModel.State.SuccessfullyFetchedProfile,
    onChangePicture: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            UserImageThumbnail(
                imgUrl = state.profileUri,
                onChangePicture = onChangePicture
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxSize()
            ) {
                TitleLabel(
                    title = "Full Name",
                    content = state.userProfile.fullName
                )
                Spacer(modifier = Modifier.size(8.dp))
                TitleLabel(
                    title = "Email",
                    content = state.userProfile.emailAddress
                )
                Spacer(modifier = Modifier.size(8.dp))
                TitleLabel(
                    title = "Address",
                    content = state.userProfile.address
                )
                Spacer(modifier = Modifier.size(8.dp))
                TitleLabel(
                    title = "State",
                    content = state.userProfile.state
                )
                Spacer(modifier = Modifier.size(8.dp))
                TitleLabel(
                    title = "City",
                    content = state.userProfile.city
                )
                Spacer(modifier = Modifier.size(8.dp))
                TitleLabel(
                    title = "Zip Code",
                    content = state.userProfile.zipCode
                )
            }
        }
    }
}

@Composable
fun TitleLabel(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentSize()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6
        )
        Text(
            text = content,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun UserImageThumbnail(
    imgUrl: Uri? = null,
    modifier: Modifier = Modifier,
    onChangePicture: (() -> Unit) = {}
) {
    Box(
        modifier = modifier
            .wrapContentSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_default_user),
            error = painterResource(id = R.drawable.ic_default_user),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(156.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Take Picture",
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    onChangePicture()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingLayout_Preview() {
    LoadingLayout()
}

@Preview(showBackground = true)
@Composable
fun TitleLabel_Preview() {
    TitleLabel(
        title = "Some Title",
        content = "Yada Yada Yada"
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardExampleScreen_Preview() {
    DashboardExampleScreen(
        DashboardExampleViewModel.State.SuccessfullyFetchedProfile(
            userProfile = UserProfile(
                id = "123",
                fullName = "Peter Jackson",
                emailAddress = "peter@jackson.com",
                address = "123 Liberty Av.",
                state = "CA",
                city = "Los Angeles",
                zipCode = "12345"
            )
        )
    )
}

@Preview
@Composable
fun UserImageThumbnail_Preview() {
    UserImageThumbnail()
}
