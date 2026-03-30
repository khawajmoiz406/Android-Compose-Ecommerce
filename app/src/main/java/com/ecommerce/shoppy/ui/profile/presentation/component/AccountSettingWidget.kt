package com.ecommerce.shoppy.ui.profile.presentation.component

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
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.theme.Green
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun AccountSettingWidget(
    onAddressClicked: () -> Unit,
    onPromoCodeClicked: () -> Unit,
    onPaymentMethodClicked: () -> Unit,
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
            text = stringResource(R.string.account_settings),
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            color = Green,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .background(
                    color = Green.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
                )
                .padding(horizontal = 10.sdp, vertical = 10.sdp)
                .fillMaxWidth()
        )

        HorizontalDivider(thickness = 1.sdp, color = MaterialTheme.colorScheme.outlineVariant)

        ItemSetting(
            icon = "location",
            title = stringResource(R.string.saved_address),
            desc = stringResource(R.string.saved_address_msg),
            onClick = onAddressClicked
        )

        HorizontalDivider(thickness = 1.sdp, color = MaterialTheme.colorScheme.outlineVariant)

        ItemSetting(
            icon = "card",
            title = stringResource(R.string.payment_method),
            desc = stringResource(R.string.payment_method_msg),
            onClick = onPaymentMethodClicked
        )

        HorizontalDivider(thickness = 1.sdp, color = MaterialTheme.colorScheme.outlineVariant)

        ItemSetting(
            icon = "tag",
            title = stringResource(R.string.promo_code_and_coupon),
            desc = stringResource(R.string.check_your_promo_code),
            onClick = onPromoCodeClicked
        )
    }
}

@Preview
@Composable
private fun PreviewAccountSettingWidget() {
    AccountSettingWidget({}, {}, {})
}