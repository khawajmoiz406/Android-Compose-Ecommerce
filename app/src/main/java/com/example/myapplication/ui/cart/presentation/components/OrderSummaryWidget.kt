package com.example.myapplication.ui.cart.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.theme.Blue
import com.example.myapplication.config.theme.Green
import com.example.myapplication.config.theme.Purple80
import com.example.myapplication.config.utils.Constants
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.Dimensions
import com.example.myapplication.core.model.Meta
import com.example.myapplication.core.model.Product
import com.example.myapplication.core.model.Review
import com.example.myapplication.ui.cart.data.local.entity.CartItem
import com.example.myapplication.ui.cart.data.local.relation.CartItemWithProduct
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun OrderSummaryWidget(cart: Cart, shipping: Pair<String, Double>? = null, promoCode: Pair<String, Double>? = null) {

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.sdp)
            )
            .padding(10.sdp)
            .fillMaxWidth()
    ) {

        Spacer(Modifier.height(5.sdp))

        Text(
            text = stringResource(R.string.order_summary),
            fontSize = 14.ssp,
            lineHeight = 14.ssp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(15.sdp))

        cart.items.mapIndexed { index, it ->
            HeadingValue(
                heading = "${it.product.title}${if (it.cartItem.quantity > 0) " X ${it.cartItem.quantity}" else ""}",
                value = "$${String.format("%.2f", it.product.getPriceBeforeDiscount())}",
            )

            if (index < (cart.items.size - 1)) Spacer(Modifier.height(5.sdp))
        }

        Spacer(Modifier.height(10.sdp))

        DottedHorizontalDivider()

        Spacer(Modifier.height(10.sdp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.sub_total),
                fontSize = 12.ssp,
                lineHeight = 12.ssp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(0.5f)
            )

            Text(
                text = "$${String.format("%.2f", cart.getSubTotalWithoutDiscount())}",
                fontSize = 12.ssp,
                lineHeight = 12.ssp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(0.5f)
            )
        }

        Spacer(Modifier.height(10.sdp))

        DottedHorizontalDivider()

        Spacer(Modifier.height(10.sdp))

        HeadingValue(
            heading = stringResource(R.string.product_discount),
            value = "-$${String.format("%.2f", cart.getSubTotalDiscount())}",
            valueTint = Green
        )

        promoCode?.let {
            Spacer(Modifier.height(5.sdp))

            HeadingValue(
                heading = stringResource(R.string.promo_code).replace("@value", it.first),
                value = "-$${it.second}",
                valueTint = Green
            )
        }

        shipping?.let {
            Spacer(Modifier.height(10.sdp))

            DottedHorizontalDivider()

            Spacer(Modifier.height(10.sdp))

            HeadingValue(
                heading = stringResource(R.string.shipping),
                value = if (shipping.second == 0.0) stringResource(R.string.free) else shipping.second.toString(),
                valueTint = if (shipping.second == 0.0) Green else null
            )
        }

        Spacer(Modifier.height(10.sdp))

        DottedHorizontalDivider()

        Spacer(Modifier.height(10.sdp))

        HeadingValue(
            heading = stringResource(R.string.vat),
            value = "$${Constants.VAT}",
        )

        Spacer(Modifier.height(5.sdp))

        HeadingValue(
            heading = stringResource(R.string.platform_fee),
            value = "$${Constants.PLATFORM_FEES}",
        )

        Spacer(Modifier.height(10.sdp))

        HorizontalDivider()

        Spacer(Modifier.height(15.sdp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.total),
                    fontSize = 14.ssp,
                    lineHeight = 14.ssp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(0.3f)
                )

                Text(
                    text = "$${String.format("%.2f", cart.getTotal(promoCode?.second))}",
                    fontSize = 18.ssp,
                    lineHeight = 18.ssp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(0.7f)
                )
            }

            Spacer(Modifier.height(3.sdp))

            Text(
                text = stringResource(R.string.inclusive_of_tax),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(15.sdp))

        HorizontalDivider()

        Spacer(Modifier.height(15.sdp))

        Row {
            IconValue("shield", stringResource(R.string.secure_payment), Green)
            IconValue("rotate", stringResource(R.string.easy_return), Purple80)
            IconValue("truck", stringResource(R.string.fast_shipping), Blue)
        }

        Spacer(Modifier.height(10.sdp))
    }
}

@Composable
private fun HeadingValue(heading: String, value: String, valueTint: Color? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = heading,
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.6f)
        )

        Text(
            text = value,
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Medium,
            color = valueTint ?: MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.4f)
        )
    }
}

@Composable
private fun RowScope.IconValue(icon: String, value: String, tint: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.sdp)
                .background(color = tint.copy(alpha = 0.2f), shape = CircleShape)
        ) {
            SvgImage(
                asset = icon,
                color = tint,
                modifier = Modifier.size(17.sdp, 17.sdp)
            )
        }

        Spacer(Modifier.height(5.sdp))

        Text(
            text = value,
            fontSize = 10.ssp,
            lineHeight = 10.ssp,
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Composable
private fun DottedHorizontalDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.sdp)
    ) {
        drawLine(
            color = Color.Gray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
    }
}

@Preview
@Composable
private fun PreviewOrderSummaryWidget() {
    OrderSummaryWidget(
        shipping = Pair("standard", 0.0),
        promoCode = Pair("SAVE20", 20.0),
        cart = Cart(
            items = listOf(
                CartItemWithProduct(
                    cartItem = CartItem(productId = 1, quantity = 48),
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
                            width = 15.14,
                            height = 13.08,
                            depth = 22.99
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
                            ),
                            Review(
                                rating = 4,
                                comment = "Very satisfied!",
                                date = "2025-04-30T09:41:02.053Z",
                                reviewerName = "Lucas Gordon",
                                reviewerEmail = "lucas.gordon@x.dummyjson.com"
                            ),
                            Review(
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
                ),
                CartItemWithProduct(
                    cartItem = CartItem(productId = 1, quantity = 48),
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
                            width = 15.14,
                            height = 13.08,
                            depth = 22.99
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
                            ),
                            Review(
                                rating = 4,
                                comment = "Very satisfied!",
                                date = "2025-04-30T09:41:02.053Z",
                                reviewerName = "Lucas Gordon",
                                reviewerEmail = "lucas.gordon@x.dummyjson.com"
                            ),
                            Review(
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
                ),
                CartItemWithProduct(
                    cartItem = CartItem(productId = 1, quantity = 48),
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
                            width = 15.14,
                            height = 13.08,
                            depth = 22.99
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
                            ),
                            Review(
                                rating = 4,
                                comment = "Very satisfied!",
                                date = "2025-04-30T09:41:02.053Z",
                                reviewerName = "Lucas Gordon",
                                reviewerEmail = "lucas.gordon@x.dummyjson.com"
                            ),
                            Review(
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
                ),
                CartItemWithProduct(
                    cartItem = CartItem(productId = 1, quantity = 48),
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
                            width = 15.14,
                            height = 13.08,
                            depth = 22.99
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
                            ),
                            Review(
                                rating = 4,
                                comment = "Very satisfied!",
                                date = "2025-04-30T09:41:02.053Z",
                                reviewerName = "Lucas Gordon",
                                reviewerEmail = "lucas.gordon@x.dummyjson.com"
                            ),
                            Review(
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
                ),
            )
        )
    )
}