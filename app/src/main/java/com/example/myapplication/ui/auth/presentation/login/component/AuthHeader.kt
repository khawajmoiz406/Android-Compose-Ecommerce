package com.example.myapplication.ui.auth.presentation.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.config.components.image.SvgImage
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AuthHeader(heading: String, subHeading: String? = null) {
    val roundedCorners = RoundedCornerShape(10.sdp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(150.sdp)
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.sdp)
                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f), roundedCorners)
                    .border(0.5.dp, MaterialTheme.colorScheme.onPrimary, roundedCorners)
            ) {
                SvgImage(
                    asset = "shoppy_bag",
                    modifier = Modifier.size(25.sdp)
                )
            }

            Spacer(Modifier.height(10.sdp))

            Text(
                text = heading,
                fontSize = 18.ssp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            subHeading?.let {
                Spacer(Modifier.height(5.sdp))

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    fontSize = 11.ssp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAuthHeader() {
    AuthHeader("Welcome Back", "Sign in to continue")
}