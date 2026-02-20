package com.example.myapplication.ui.cart.presentation.checkout.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.theme.Orange
import com.example.myapplication.ui.cart.data.remote.dto.PaymentMethod
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun PaymentMethodWidget(selected: PaymentMethod, onPaymentMethodChanged: (PaymentMethod) -> Unit) {
    val paymentMethods = listOf(PaymentMethod.CashOnDelivery, PaymentMethod.Card)

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = Orange.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
                )
                .padding(horizontal = 10.sdp, vertical = 8.sdp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(28.sdp)
                    .background(color = Orange, shape = CircleShape)
            ) {
                SvgImage(
                    asset = "card",
                    color = Color.White,
                    modifier = Modifier.size(18.sdp, 18.sdp)
                )
            }

            Spacer(Modifier.width(10.sdp))

            Text(
                text = stringResource(R.string.payment_method),
                fontSize = 13.ssp,
                lineHeight = 13.ssp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        HorizontalDivider(thickness = 1.sdp, color = MaterialTheme.colorScheme.outlineVariant)

        Spacer(Modifier.height(10.sdp))

        Column(modifier = Modifier.padding(horizontal = 10.sdp)) {
            paymentMethods.map { method ->
                ItemPaymentMethod(method, method.id == selected.id, onPaymentMethodChanged)

                Spacer(Modifier.height(10.sdp))
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun ItemPaymentMethod(method: PaymentMethod, selected: Boolean, onPaymentChanged: (PaymentMethod) -> Unit) {
    val bgColor = if (!selected) MaterialTheme.colorScheme.surface
    else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

    val borderColor = if (!selected) MaterialTheme.colorScheme.outlineVariant
    else MaterialTheme.colorScheme.primary

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = bgColor, shape = RoundedCornerShape(12.sdp))
            .border(width = 1.sdp, color = borderColor, shape = RoundedCornerShape(12.sdp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.sdp))
            .clickable { onPaymentChanged.invoke(method) }
            .padding(horizontal = 10.sdp, vertical = 15.sdp)
    ) {
        SvgImage(
            asset = method.image,
            color = Orange,
            modifier = Modifier.size(18.sdp, 18.sdp)
        )

        Spacer(Modifier.width(10.sdp))

        Text(
            text = method.name,
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun PreviewPaymentMethodWidget() {
    PaymentMethodWidget(PaymentMethod.CashOnDelivery) {}
}