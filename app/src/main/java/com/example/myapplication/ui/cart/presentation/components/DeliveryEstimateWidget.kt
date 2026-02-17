package com.example.myapplication.ui.cart.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.theme.Blue
import com.example.myapplication.config.theme.Orange
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun DeliveryEstimateWidget() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(width = 0.5.dp, color = Blue, shape = RoundedCornerShape(10.sdp))
            .background(color = Blue.copy(alpha = 0.1f), shape = RoundedCornerShape(10.sdp))
            .fillMaxWidth()
            .padding(vertical = 12.sdp, horizontal = 10.sdp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.estimated_delivery),
                fontSize = 12.ssp,
                lineHeight = 12.ssp,
                fontWeight = FontWeight.Medium,
                color = Blue,
            )

            Spacer(Modifier.height(3.sdp))

            Text(
                text = stringResource(R.string.estimated_delivery_msg),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                color = MaterialTheme.colorScheme.outline,
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(28.sdp)
                .background(color = Blue.copy(alpha = 0.2f), shape = CircleShape)
        ) {
            SvgImage(
                asset = "box",
                color = Blue,
                modifier = Modifier.size(18.sdp, 18.sdp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDeliveryEstimateWidget() {
    DeliveryEstimateWidget()
}