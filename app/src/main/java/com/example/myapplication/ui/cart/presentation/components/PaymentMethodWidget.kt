package com.example.myapplication.ui.cart.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.config.components.input.AppRadioButton
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.theme.Blue
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("DefaultLocale")
@Composable
fun PaymentMethodWidget(onMethodChange: (Int) -> Unit) {
    val selectedMethod = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .background(color = Blue.copy(alpha = 0.1f), shape = RoundedCornerShape(12.sdp))
            .padding(start = 10.sdp, end = 10.sdp, top = 10.sdp)
            .fillMaxWidth()
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            SvgImage(
                asset = "truck",
                color = Blue,
                modifier = Modifier.size(18.sdp, 18.sdp)
            )

            Spacer(Modifier.width(8.sdp))

            Text(
                text = stringResource(R.string.payment_method),
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(Modifier.height(7.sdp))

        AppRadioButton(
            leadingIcon = "money",
            selected = selectedMethod.intValue == 0,
            heading = stringResource(R.string.cash_on_delivery),
            onClick = { onRadioChanged(0, selectedMethod, onMethodChange) },
        )

        HorizontalDivider()

        AppRadioButton(
            leadingIcon = "card",
            selected = selectedMethod.intValue == 1,
            heading = stringResource(R.string.credit_card),
            onClick = { onRadioChanged(1, selectedMethod, onMethodChange) },
        )

        HorizontalDivider()

        AppRadioButton(
            leadingIcon = "wallet",
            selected = selectedMethod.intValue == 2,
            heading = stringResource(R.string.wallet),
            onClick = { onRadioChanged(2, selectedMethod, onMethodChange) },
        )

        Spacer(Modifier.height(5.sdp))
    }
}

private fun onRadioChanged(method: Int, mutable: MutableIntState, onMethodChange: (Int) -> Unit) {
    mutable.intValue = method
    onMethodChange.invoke(method)
}

@Preview
@Composable
private fun PreviewPaymentMethodWidget() {
    PaymentMethodWidget {}
}