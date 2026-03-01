package com.example.myapplication.ui.profile.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.config.theme.Brown
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun PreferenceWidget(
    onNotificationsClicked: () -> Unit,
    onHelpClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.sdp)
            )
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.preference),
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            color = Brown,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .background(
                    color = Brown.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
                )
                .padding(horizontal = 10.sdp, vertical = 10.sdp)
                .fillMaxWidth()
        )

        HorizontalDivider(thickness = 1.sdp, color = MaterialTheme.colorScheme.outlineVariant)

        ItemSetting(
            icon = "notification",
            title = stringResource(R.string.notifications),
            desc = stringResource(R.string.notifications_msg),
            onClick = onNotificationsClicked
        )

        HorizontalDivider(thickness = 1.sdp, color = MaterialTheme.colorScheme.outlineVariant)

        ItemSetting(
            icon = "question",
            title = stringResource(R.string.help_and_support),
            desc = stringResource(R.string.help_and_support_msg),
            onClick = onHelpClicked
        )
    }
}

@Preview
@Composable
private fun PreviewPreferenceWidget() {
    PreferenceWidget({}, {})
}