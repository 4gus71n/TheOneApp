package com.example.chatexample.ui.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.base_design.ui.TheOneAppTheme

@Composable
fun ErrorLayout(
    errorMessage: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.h6,
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ErrorLayout_Preview() {
    TheOneAppTheme {
        ErrorLayout("No internet connectivity. Try again later.")
    }
}