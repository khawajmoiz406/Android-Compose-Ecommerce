package com.ecommerce.shoppy.ui.profile.presentation.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ecommerce.shoppy.config.components.image.SvgImage
import com.ecommerce.shoppy.config.components.input.AppSwitch
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun TogglePreferenceWidget(
    value: Boolean,
    icon: String,
    title: String,
    desc: String,
    onCheckChanged: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.sdp)
            )
            .fillMaxWidth()
            .padding(vertical = 12.sdp, horizontal = 10.sdp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.sdp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(10.sdp)
                )
        ) {
            SvgImage(
                asset = icon,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.sdp, 20.sdp)
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

            Spacer(Modifier.height(1.sdp))

            Text(
                text = desc,
                fontSize = 10.ssp,
                lineHeight = 10.ssp,
                color = MaterialTheme.colorScheme.outline,
            )
        }

        AppSwitch(isChecked = value, onCheckedChange = onCheckChanged)
    }
}

@Preview
@Composable
private fun PreviewTogglePreferenceWidget() {
    TogglePreferenceWidget(true, "", "Toggle Heading", "This is the description for toggle pref") {}
}