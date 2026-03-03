package com.example.myapplication.ui.order.presentation.listing.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.core.model.OrderStatus
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ItemOrderStatus(
    selected: Boolean,
    index: Int,
    status: Pair<OrderStatus, Int>,
    onItemClick: ((OrderStatus) -> Unit)?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(28.sdp)
            .padding(start = if (index == 0) 10.sdp else 0.sdp, end = 10.sdp)
            .clip(RoundedCornerShape(15.sdp))
            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = if (selected) 1f else 0.3f))
            .clickable { onItemClick?.invoke(status.first) }
    ) {
        Spacer(Modifier.width(12.sdp))

        Text(
            text = status.first.toString(),
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            fontWeight = FontWeight.Medium,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline
        )

        Spacer(Modifier.width(5.sdp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(White, CircleShape)
                .size(15.sdp)
        ) {
            Text(
                text = status.second.toString(),
                fontSize = 9.ssp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                softWrap = true,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
            )
        }

        Spacer(Modifier.width(12.sdp))
    }
}

@Preview
@Composable
private fun PreviewOrderStatusesWidget() {
    ItemOrderStatus(
        selected = false,
        index = 0,
        status = OrderStatus.Pending to 12,
    ) { }
}