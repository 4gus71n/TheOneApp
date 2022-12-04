@file:OptIn(ExperimentalMaterialApi::class)

package com.example.chatexample.ui.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.base_design.R
import com.example.base_design.ui.TheOneAppTheme
import com.example.chatexample.ui.main.viewmodel.DashboardExampleViewModel
import java.util.*

// Note: Using JP kinda limits the amount of third-party libraries we are able to use, what would
// happen if we want to use a photo-picker library here?

@Composable
fun DashboardExampleScreen(
    imageUrl: State<Uri?>,
    onChangePicture: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            UserImageThumbnail(
                imgUrl = imageUrl.value,
                onChangePicture = onChangePicture
            )
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Full Nae",
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = "Admin",
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = "Admin",
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
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
            placeholder = ColorPainter(Color(0x1F888888)),
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

@Preview
@Composable
fun UserImageThumbnail_Preview() {
    UserImageThumbnail()
}
