package com.example.myapplication.ui.dashboard.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.components.SvgImage
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ItemCategory(category: Category, index: Int, selectedIndex: Int) {
    val isSelected = index == selectedIndex
    category.icon = Constants.CATEGORIES_ICON_MAP[category.slug] ?: ""

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(30.sdp)
            .padding(start = if (index == 0) 10.sdp else 0.sdp, end = 8.sdp)
            .clip(RoundedCornerShape(15.sdp))
            .background(color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceDim)
    ) {
        Spacer(Modifier.width(10.sdp))

        SvgImage(
            asset = category.icon,
            modifier = Modifier.size(20.sdp),
            color = if(isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.width(5.sdp))

        Text(
            text = category.name,
            fontSize = 10.ssp,
            lineHeight = 10.ssp,
            fontWeight = FontWeight.Medium,
            color = if(isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.width(10.sdp))
    }
}