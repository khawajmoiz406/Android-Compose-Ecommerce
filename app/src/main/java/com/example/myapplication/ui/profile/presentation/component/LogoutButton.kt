package com.example.myapplication.ui.profile.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.config.components.image.SvgImage
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LogoutButton(onLogoutClicked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(10.sdp)
            )
            .background(
                color = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                shape = RoundedCornerShape(10.sdp)
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.sdp))
            .clickable { onLogoutClicked.invoke() }
            .padding(vertical = 13.sdp)
    ) {
        Text(
            text = stringResource(R.string.logout),
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.error,
        )

        Spacer(Modifier.width(5.sdp))

        SvgImage(
            asset = "logout",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(15.sdp, 15.sdp).rotate(180f)
        )

    }
}

@Preview
@Composable
private fun PreviewDeliveryEstimateWidget() {
    LogoutButton {}
}