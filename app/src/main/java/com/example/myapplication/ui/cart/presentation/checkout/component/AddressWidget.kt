package com.example.myapplication.ui.cart.presentation.checkout.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.theme.Brown
import com.example.myapplication.core.model.Address
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun AddressWidget(selected: Address?, onChangeAddress: () -> Unit) {
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
                    color = Brown.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
                )
                .padding(horizontal = 10.sdp, vertical = 8.sdp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(28.sdp)
                    .background(color = Brown, shape = CircleShape)
            ) {
                SvgImage(
                    asset = "location",
                    color = Color.White,
                    modifier = Modifier.size(18.sdp, 18.sdp)
                )
            }

            Spacer(Modifier.width(10.sdp))

            Text(
                text = stringResource(R.string.shipping_address),
                fontSize = 13.ssp,
                lineHeight = 13.ssp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        HorizontalDivider(thickness = 1.sdp, color = MaterialTheme.colorScheme.outlineVariant)

        if (selected == null)
            Box(modifier = Modifier.padding(horizontal = 10.sdp, vertical = 8.sdp)) {
                IconButton(stringResource(R.string.select_address), "edit") {
                    onChangeAddress.invoke()
                }
            }
        else
            AddressItem(selected) { onChangeAddress.invoke() }
    }
}

@Composable
private fun AddressItem(selected: Address, onAddressChanged: () -> Unit) {
    val isDefault = selected.defaultAddress

    Column(
        modifier = Modifier
            .padding(horizontal = 10.sdp, vertical = 8.sdp)
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(10.sdp)
            )
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(10.sdp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.sdp))
            .padding(10.sdp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
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
                    asset = selected.getAddressType().asset,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.sdp, 18.sdp)
                )
            }

            Spacer(modifier = Modifier.width(10.sdp))

            Text(
                text = selected.fullName,
                fontSize = 12.ssp,
                lineHeight = 12.ssp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f, fill = false)
            )

            Spacer(modifier = Modifier.width(5.sdp))

            Text(
                text = selected.getAddressType().toString(),
                fontSize = 9.ssp,
                lineHeight = 9.ssp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(10.sdp)
                    )
                    .padding(vertical = 2.sdp, horizontal = 5.sdp)
            )

            if (isDefault) {
                Spacer(modifier = Modifier.width(5.sdp))

                Text(
                    text = stringResource(R.string.default_),
                    fontSize = 9.ssp,
                    lineHeight = 9.ssp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(10.sdp)
                        )
                        .padding(vertical = 2.sdp, horizontal = 5.sdp)
                )
            }
        }

        Spacer(Modifier.height(10.sdp))

        Text(
            text = "${selected.houseNo ?: ""} ${selected.address}",
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(Modifier.height(2.sdp))

        Text(
            text = "${selected.city}, ${selected.state} ${selected.zipCode} ${selected.country}",
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(Modifier.height(10.sdp))

        IconButton(stringResource(R.string.change_address), "edit") {
            onAddressChanged.invoke()
        }
    }
}

@Composable
private fun IconButton(title: String, icon: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(10.sdp)
            )
            .clip(RoundedCornerShape(20.sdp))
            .clickable { onClick.invoke() }
            .fillMaxWidth()
            .height(35.sdp)
    ) {
        SvgImage(
            asset = icon,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(15.sdp, 15.sdp)
        )

        Spacer(Modifier.width(5.sdp))

        Text(
            text = title,
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun PreviewAddressWidget() {
    AddressWidget(
        Address(
            id = 1,
            type = 1,
            fullName = "Gulberg Home",
            address = "23-A Canal Park Street# 5 Gulberg 2",
            houseNo = "23-A",
            city = "Lahore",
            state = "Punjab",
            country = "Pakistan",
            zipCode = "54000",
            defaultAddress = true
        ),
        {})
}