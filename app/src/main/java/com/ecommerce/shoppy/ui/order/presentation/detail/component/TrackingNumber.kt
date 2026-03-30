package com.ecommerce.shoppy.ui.order.presentation.detail.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.image.SvgImage
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("DefaultLocale")
@Composable
fun TrackingNumber(trackingNumber: String, onCopyClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .border(1.sdp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.sdp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.sdp))
            .fillMaxWidth()
            .padding(start = 10.sdp, end = 10.sdp, top = 14.sdp, bottom = 10.sdp)
    ) {

        Text(
            text = stringResource(R.string.tracking_number),
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            color = MaterialTheme.colorScheme.outline,
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = trackingNumber,
                fontSize = 13.ssp,
                lineHeight = 13.ssp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(30.sdp)
                    .clip(RoundedCornerShape(20.sdp))
                    .clickable { onCopyClicked.invoke() }
            ) {
                SvgImage(
                    asset = "copy",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(15.sdp, 15.sdp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTrackingNumber() {
    TrackingNumber("12345679876543") {}
}