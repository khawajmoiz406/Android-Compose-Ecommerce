package com.ecommerce.shoppy.ui.profile.presentation.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.ecommerce.shoppy.config.components.image.SvgImage
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ItemSetting(icon: String, title: String, desc: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(vertical = 8.sdp, horizontal = 10.sdp)
    ) {
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

        Spacer(Modifier.width(10.sdp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 13.ssp,
                lineHeight = 13.ssp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(2.sdp))

            Text(
                text = desc,
                fontSize = 10.ssp,
                lineHeight = 10.ssp,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}