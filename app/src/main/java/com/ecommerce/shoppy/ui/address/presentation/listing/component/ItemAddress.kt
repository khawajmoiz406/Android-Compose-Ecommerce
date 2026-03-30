package com.ecommerce.shoppy.ui.address.presentation.listing.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.image.SvgImage
import com.ecommerce.shoppy.core.model.Address
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ItemAddress(
    address: Address,
    selectionMode: Boolean,
    onAddressClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    selected: Boolean = false,
) {
    val isDefault = address.defaultAddress
    val tintColor = when {
        selectionMode && !selected -> MaterialTheme.colorScheme.outline
        !selectionMode && !isDefault -> MaterialTheme.colorScheme.outline
        else -> MaterialTheme.colorScheme.primary
    }

    Column(
        modifier = Modifier
            .border(width = 1.sdp, color = tintColor, shape = RoundedCornerShape(10.sdp))
            .background(tintColor.copy(alpha = 0.1f), shape = RoundedCornerShape(10.sdp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.sdp))
            .clickable { onAddressClicked.invoke() }
            .padding(10.sdp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.sdp)
                        .background(
                            color = tintColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.sdp)
                        )
                ) {
                    SvgImage(
                        asset = address.getAddressType().asset,
                        color = tintColor,
                        modifier = Modifier.size(18.sdp, 18.sdp)
                    )
                }

                Spacer(modifier = Modifier.width(10.sdp))

                Text(
                    text = address.fullName,
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
                    text = address.getAddressType().toString(),
                    fontSize = 9.ssp,
                    lineHeight = 9.ssp,
                    color = tintColor,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(
                            tintColor.copy(alpha = 0.1f),
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
                        color = tintColor,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .background(
                                tintColor.copy(alpha = 0.1f),
                                RoundedCornerShape(10.sdp)
                            )
                            .padding(vertical = 2.sdp, horizontal = 5.sdp)
                    )
                }
            }

            if (selectionMode) {
                RadioButton(
                    selected = selected,
                    onClick = { onAddressClicked.invoke() },
                )
            }
        }

        Spacer(Modifier.height(10.sdp))

        Text(
            text = "${address.houseNo ?: ""} ${address.address}",
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(Modifier.height(2.sdp))

        Text(
            text = "${address.city}, ${address.state} ${address.zipCode} ${address.country}",
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(Modifier.height(10.sdp))

        Row {
            IconButton(
                title = stringResource(R.string.edit),
                icon = "edit",
                tintColor = MaterialTheme.colorScheme.primary,
                onClick = { onEditClicked.invoke() }
            )

            Spacer(Modifier.width(5.sdp))

            IconButton(
                title = stringResource(R.string.delete),
                icon = "delete",
                tintColor = MaterialTheme.colorScheme.error,
                onClick = { onDeleteClicked.invoke() }
            )
        }
    }
}

@Composable
private fun IconButton(icon: String, title: String, onClick: () -> Unit, tintColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .background(
                tintColor.copy(alpha = 0.1f),
                RoundedCornerShape(20.sdp)
            )
            .clip(RoundedCornerShape(20.sdp))
            .clickable { onClick.invoke() }
            .padding(vertical = 7.sdp, horizontal = 12.sdp)
    ) {
        SvgImage(
            asset = icon,
            color = tintColor,
            modifier = Modifier.size(12.sdp, 12.sdp)
        )

        Spacer(Modifier.width(5.sdp))

        Text(
            text = title,
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            color = tintColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun PreviewItemAddress() {
    ItemAddress(
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
        true, {},
        {}, {},
    )
}