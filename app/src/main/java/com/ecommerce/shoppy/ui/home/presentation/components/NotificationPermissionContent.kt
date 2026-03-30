package com.ecommerce.shoppy.ui.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.image.SvgImage
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun NotificationPermissionContent(onAllowClicked: () -> Unit, onCancelClicked: () -> Unit) {
    return Column(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.surface,
            RoundedCornerShape(12.sdp)
        )
    ) {
        Header(onCancelClicked)

        Spacer(Modifier.height(15.sdp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 15.sdp),
        ) {
            Text(
                text = stringResource(R.string.stay_updated),
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
            )

            Spacer(Modifier.height(8.sdp))

            Text(
                text = stringResource(R.string.stay_updated_msg),
                fontSize = 10.ssp,
                lineHeight = 10.ssp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline,
            )

            Spacer(Modifier.height(20.sdp))

            HeadingValue(
                stringResource(R.string.order_updates),
                stringResource(R.string.order_updates_msg),
                "box"
            )

            Spacer(Modifier.height(10.sdp))

            HeadingValue(
                stringResource(R.string.exclusive_deals),
                stringResource(R.string.exclusive_deals_msg),
                "tag"
            )

            Spacer(Modifier.height(10.sdp))

            HeadingValue(
                stringResource(R.string.account_security),
                stringResource(R.string.account_security_msg),
                "shield"
            )

            Spacer(Modifier.height(20.sdp))

            Button(
                onClick = { onAllowClicked.invoke() },
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.sdp),
            ) {
                Text(
                    stringResource(R.string.allow_notifications),
                    fontSize = 12.ssp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(Modifier.height(10.sdp))

            Text(
                stringResource(R.string.maybe_later),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.clickable { onCancelClicked.invoke() }
            )

            Spacer(Modifier.height(15.sdp))

            Text(
                stringResource(R.string.change_notificaiton_msg),
                fontSize = 9.ssp,
                lineHeight = 9.ssp,
                color = MaterialTheme.colorScheme.outline,
            )

            Spacer(Modifier.height(15.sdp))
        }
    }
}

@Composable
private fun Header(onCancelClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
            )
            .fillMaxWidth()
            .height(100.sdp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(30.sdp)
                .clip(CircleShape)
                .clickable { onCancelClicked.invoke() }
        ) {
            SvgImage(
                asset = "close",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(15.sdp)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .size(55.sdp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    shape = CircleShape
                )
        ) {
            SvgImage(
                asset = "notification",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.sdp)
            )
        }
    }
}

@Composable
private fun HeadingValue(heading: String, value: String, icon: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.sdp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.sdp)
                )
        ) {
            SvgImage(
                asset = icon,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.sdp, 18.sdp)
            )
        }

        Spacer(modifier = Modifier.width(10.sdp))

        Column {
            Text(
                text = heading,
                fontSize = 12.ssp,
                lineHeight = 12.ssp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
            )

            Text(
                text = value,
                fontSize = 9.ssp,
                lineHeight = 9.ssp,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewNotificationPermissionContent() {
    NotificationPermissionContent({}, {})
}