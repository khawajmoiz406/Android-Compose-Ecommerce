package com.example.myapplication.ui.address.presentation.add.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.core.model.AddressType
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AddressTypeWidget(selectedType: AddressType, onAddressChanged: (AddressType) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.sdp)) {
        ItemAddressType(selectedType, AddressType.Home, onAddressChanged)
        ItemAddressType(selectedType, AddressType.Office, onAddressChanged)
        ItemAddressType(selectedType, AddressType.Other, onAddressChanged)
    }
}

@Composable
private fun RowScope.ItemAddressType(
    selectedType: AddressType,
    type: AddressType,
    onAddressChanged: (AddressType) -> Unit
) {
    val isSelected = selectedType === type
    val color =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(width = 1.sdp, color = color, shape = RoundedCornerShape(10.sdp))
            .background(color.copy(alpha = 0.1f), shape = RoundedCornerShape(10.sdp))
            .clip(RoundedCornerShape(10.sdp))
            .clickable { onAddressChanged.invoke(type) }
            .padding(10.sdp)
            .weight(1f)
    ) {
        SvgImage(
            asset = type.asset,
            color = color,
            modifier = Modifier.size(20.sdp)
        )

        Spacer(Modifier.height(10.sdp))

        Text(
            text = type.toString(),
            color = color,
            fontSize = 10.ssp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Preview
@Composable
private fun PreviewAddressTypeWidget() {
    AddressTypeWidget(AddressType.Home) {}
}