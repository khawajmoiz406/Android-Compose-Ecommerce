package com.example.myapplication.ui.product_detail.presentation.components

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.config.components.SvgImage
import com.example.myapplication.config.theme.Orange
import com.example.myapplication.config.theme.Pink
import com.example.myapplication.config.utils.cmToInches
import com.example.myapplication.config.utils.ozToGrams
import com.example.myapplication.models.response.product.Dimensions
import com.example.myapplication.models.response.product.Meta
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.Review
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DimensionView(product: Product) {
    Column(Modifier.padding(10.sdp)) {

        DimensionsComposable(product)

        Spacer(Modifier.height(10.sdp))

        VolumeComposable(product)

        Spacer(Modifier.height(10.sdp))

        InfoComposable()
    }
}

@Composable
private fun DimensionsComposable(product: Product) {
    Column(
        modifier = Modifier
            .background(Pink.copy(alpha = 0.1f), shape = RoundedCornerShape(10.sdp))
            .padding(10.sdp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SvgImage(
                asset = "dimensions", color = Pink, modifier = Modifier.size(18.sdp, 18.sdp)
            )

            Spacer(Modifier.width(8.sdp))

            Text(
                text = stringResource(R.string.dimension),
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(Modifier.height(15.sdp))

        HeadingValue(
            heading = stringResource(R.string.width),
            subValue = "(${product.dimensions?.width?.cmToInches(decimalPlaces = 1)?.toString() ?: "0"}\")",
            value = stringResource(R.string.centimeter_inches).replace(
                "@value", product.dimensions?.width?.toString() ?: ""
            )
        )

        Spacer(Modifier.height(10.sdp))

        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceDim)

        Spacer(Modifier.height(10.sdp))

        HeadingValue(
            heading = stringResource(R.string.height),
            subValue = "(${product.dimensions?.height?.cmToInches(decimalPlaces = 1)?.toString() ?: "0"}\")",
            value = stringResource(R.string.centimeter_inches).replace(
                "@value", product.dimensions?.height?.toString() ?: ""
            )
        )

        Spacer(Modifier.height(10.sdp))

        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceDim)

        Spacer(Modifier.height(10.sdp))

        HeadingValue(
            heading = stringResource(R.string.depth),
            subValue = "(${product.dimensions?.depth?.cmToInches(decimalPlaces = 1)?.toString() ?: "0"}\")",
            value = stringResource(R.string.centimeter_inches).replace(
                "@value", product.dimensions?.depth?.toString() ?: ""
            )
        )

        Spacer(Modifier.height(10.sdp))

        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceDim)

        Spacer(Modifier.height(10.sdp))

        HeadingValue(
            heading = stringResource(R.string.weight),
            subValue = "(${product.weight?.ozToGrams()?.toString() ?: "0"}g)",
            value = stringResource(R.string.oz_gram).replace(
                "@value", product.weight?.toString() ?: ""
            )
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun VolumeComposable(product: Product) {
    Column(
        modifier = Modifier
            .background(Orange.copy(alpha = 0.1f), shape = RoundedCornerShape(10.sdp))
            .padding(10.sdp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SvgImage(
                asset = "box",
                color = Orange,
                modifier = Modifier.size(18.sdp, 18.sdp)
            )

            Spacer(Modifier.width(8.sdp))

            Text(
                text = stringResource(R.string.total_volume),
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(Modifier.height(10.sdp))

        Text(
            text = stringResource(R.string.centimeter_inches).replace(
                "@value",
                String.format("%.2f", product.calculateVolume())
            ),
            fontSize = 18.ssp,
            lineHeight = 18.ssp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun InfoComposable() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.2f), shape = RoundedCornerShape(10.sdp))
            .padding(10.sdp)
            .fillMaxWidth()
    ) {
        SvgImage(
            asset = "info",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(11.sdp, 11.sdp)
        )

        Spacer(Modifier.width(5.sdp))

        Text(
            text = stringResource(R.string.dimension_info_msg),
            fontSize = 9.ssp,
            lineHeight = 9.ssp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun HeadingValue(heading: String, value: String, subValue: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = heading,
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.3f)
        )

        Text(
            text = value,
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.7f)
        )

        Spacer(Modifier.width(3.sdp))

        Text(
            text = subValue,
            fontSize = 9.ssp,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDimensionView() {
    DimensionView(
        product = Product(
            id = 1,
            title = "Essence Mascara Lash Princess",
            description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.",
            category = "beauty",
            price = 9.99,
            discountPercentage = 10.48,
            rating = 2.56,
            stock = 99,
            tags = listOf("beauty", "mascara"),
            brand = "Essence",
            sku = "BEA-ESS-ESS-001",
            weight = 4,
            dimensions = Dimensions(
                width = 15.14, height = 13.08, depth = 22.99
            ),
            warrantyInformation = "1 week warranty",
            shippingInformation = "Ships in 3-5 business days",
            availabilityStatus = "In Stock",
            reviews = listOf(
                Review(
                    rating = 3,
                    comment = "Would not recommend!",
                    date = "2025-04-30T09:41:02.053Z",
                    reviewerName = "Eleanor Collins",
                    reviewerEmail = "eleanor.collins@x.dummyjson.com"
                ), Review(
                    rating = 4,
                    comment = "Very satisfied!",
                    date = "2025-04-30T09:41:02.053Z",
                    reviewerName = "Lucas Gordon",
                    reviewerEmail = "lucas.gordon@x.dummyjson.com"
                ), Review(
                    rating = 5,
                    comment = "Highly impressed!",
                    date = "2025-04-30T09:41:02.053Z",
                    reviewerName = "Eleanor Collins",
                    reviewerEmail = "eleanor.collins@x.dummyjson.com"
                )
            ),
            returnPolicy = "No return policy",
            minimumOrderQuantity = 48,
            meta = Meta(
                createdAt = "2025-04-30T09:41:02.053Z",
                updatedAt = "2025-04-30T09:41:02.053Z",
                barcode = "5784719087687",
                qrCode = "https://cdn.dummyjson.com/public/qr-code.png"
            ),
            images = listOf(
                "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp",
                "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp",
                "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp",
                "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp"
            ),
            isFavourite = false,
            thumbnail = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp"
        )
    )
}