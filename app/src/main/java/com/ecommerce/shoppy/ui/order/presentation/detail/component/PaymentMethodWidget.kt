package com.ecommerce.shoppy.ui.order.presentation.detail.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.image.SvgImage
import com.ecommerce.shoppy.core.model.PaymentMethod
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun PaymentMethodWidget(selected: PaymentMethod) {
    Column(
        modifier = Modifier
            .border(1.sdp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.sdp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.sdp))
            .fillMaxWidth()
            .padding(start = 10.sdp, end = 10.sdp, top = 14.sdp, bottom = 10.sdp)
    ) {

        Text(
            text = stringResource(R.string.payment_method),
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(Modifier.height(10.sdp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            SvgImage(
                asset = selected.image,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(15.sdp, 15.sdp)
            )

            Spacer(Modifier.width(8.sdp))

            Text(
                text = selected.name,
                fontSize = 13.ssp,
                lineHeight = 13.ssp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )

        }
    }
}

@Preview
@Composable
private fun PreviewPaymentMethodWidget() {
    PaymentMethodWidget(PaymentMethod.CashOnDelivery)
}