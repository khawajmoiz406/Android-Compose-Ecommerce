package com.example.myapplication.ui.order.presentation.detail.component

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.core.model.Address
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun DeliveryAddress(address: Address, phoneNumber: String) {
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
            modifier = Modifier.padding(horizontal = 10.sdp, vertical = 12.sdp)
        ) {
            SvgImage(
                asset = "location",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.sdp, 18.sdp)
            )

            Spacer(Modifier.width(8.sdp))

            Text(
                text = stringResource(R.string.shipping_address),
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        HorizontalDivider(thickness = 1.sdp)

        Spacer(Modifier.height(10.sdp))

        Text(
            text = address.fullName,
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 10.sdp)
        )

        Spacer(Modifier.height(5.sdp))

        Text(
            text = "${address.houseNo ?: ""} ${address.address}",
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 10.sdp)
        )

        Spacer(Modifier.height(2.sdp))

        Text(
            text = "${address.city}, ${address.state} ${address.zipCode} ${address.country}",
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 10.sdp)
        )

        Spacer(Modifier.height(5.sdp))

        Text(
            text = phoneNumber,
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 10.sdp)
        )

        Spacer(Modifier.height(10.sdp))
    }
}

@Preview
@Composable
private fun PreviewDeliveryAddress() {
    DeliveryAddress(
        phoneNumber = "+1234567890",
        address = Address(
            id = 1,
            type = 1,
            fullName = "Gulberg Home",
            address = "Canal Park Street# 5 Gulberg 2",
            houseNo = "23-A",
            city = "Lahore",
            state = "Punjab",
            country = "Pakistan",
            zipCode = "54000",
            defaultAddress = true
        )
    )
}