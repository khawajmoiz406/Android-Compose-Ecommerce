package com.example.myapplication.config.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.ButtonDefaults.outlinedShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.models.request.ProductsRequest
import com.example.myapplication.models.request.Order
import com.example.myapplication.models.request.SortArrangement
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun HomeFilters(defaultValue: ProductsRequest? = null, onFiltersApplied: (ProductsRequest) -> Unit) {
    val sortOrder = remember { mutableStateOf(defaultValue?.order ?: Order.Ascending) }
    val arrangeBy = remember { mutableStateOf(defaultValue?.sortBy ?: SortArrangement.Title) }

    Column(
        verticalArrangement = Arrangement.spacedBy(15.sdp),
        modifier = Modifier
            .padding(horizontal = 10.sdp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Column {
            Text(
                text = stringResource(R.string.apply_filters),
                fontSize = 16.ssp,
                lineHeight = 16.ssp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(Modifier.height(5.sdp))

            Text(
                text = stringResource(R.string.filters_msg),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        HorizontalDivider(thickness = 2.sdp)

        Text(
            text = stringResource(R.string.sort_order),
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Row {
            Box(modifier = Modifier.weight(0.5f)) {
                AppRadioButton(
                    selected = sortOrder.value == Order.Ascending,
                    heading = Order.Ascending.javaClass.simpleName,
                    onCheckChange = { if (it) sortOrder.value = Order.Ascending }
                )
            }

            Box(modifier = Modifier.weight(0.5f)) {
                AppRadioButton(
                    selected = sortOrder.value == Order.Descending,
                    heading = Order.Descending.javaClass.simpleName,
                    onCheckChange = { if (it) sortOrder.value = Order.Descending }
                )
            }
        }

        Text(
            text = stringResource(R.string.arrange_by),
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Row {
            Box(modifier = Modifier.weight(0.5f)) {
                AppRadioButton(
                    selected = arrangeBy.value == SortArrangement.Title,
                    heading = SortArrangement.Title.javaClass.simpleName,
                    onCheckChange = { if (it) arrangeBy.value = SortArrangement.Title }
                )
            }

            Box(modifier = Modifier.weight(0.5f)) {
                AppRadioButton(
                    selected = arrangeBy.value == SortArrangement.Price,
                    heading = SortArrangement.Price.javaClass.simpleName,
                    onCheckChange = { if (it) arrangeBy.value = SortArrangement.Price }
                )
            }
        }

        Row {
            Box(modifier = Modifier.weight(0.5f)) {
                AppRadioButton(
                    selected = arrangeBy.value == SortArrangement.Brand,
                    heading = SortArrangement.Brand.javaClass.simpleName,
                    onCheckChange = { if (it) arrangeBy.value = SortArrangement.Brand }
                )
            }

            Box(modifier = Modifier.weight(0.5f)) {
                AppRadioButton(
                    selected = arrangeBy.value == SortArrangement.Rating,
                    heading = SortArrangement.Rating.javaClass.simpleName,
                    onCheckChange = { if (it) arrangeBy.value = SortArrangement.Rating }
                )
            }
        }

        Row {
            Button(
                shape = outlinedShape,
                colors = outlinedButtonColors(),
                modifier = Modifier.weight(0.5f),
                border = BorderStroke(1.sdp, MaterialTheme.colorScheme.primary),
                onClick = {
                    sortOrder.value = Order.Ascending
                    arrangeBy.value = SortArrangement.Title
                },
            ) {
                Text(
                    stringResource(R.string.default_filters),
                    fontSize = 10.ssp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(Modifier.width(10.sdp))

            Button(
                modifier = Modifier.weight(0.5f),
                onClick = {
                    val request = ProductsRequest(sortBy = arrangeBy.value, order = sortOrder.value)
                    onFiltersApplied.invoke(request)
                }
            ) {
                Text(
                    stringResource(R.string.apply_filters),
                    fontSize = 10.ssp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Spacer(Modifier.height(20.sdp))
    }
}

@Composable
private fun AppRadioButton(selected: Boolean, heading: String, onCheckChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(30.sdp)
            .clip(RoundedCornerShape(15.sdp))
            .clickable { onCheckChange.invoke(true) }
    ) {
        Spacer(Modifier.width(5.sdp))

        RadioButton(
            selected = selected,
            modifier = Modifier.size(20.sdp),
            onClick = { onCheckChange.invoke(true) },
        )

        Spacer(Modifier.width(5.sdp))

        Text(
            text = heading,
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeFilters() {
    HomeFilters {}
}