package com.ecommerce.shoppy.ui.cart.presentation.cart.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.image.SvgImage
import com.ecommerce.shoppy.config.theme.Blue
import com.ecommerce.shoppy.core.model.Cart
import com.ecommerce.shoppy.core.model.Dimensions
import com.ecommerce.shoppy.core.model.Meta
import com.ecommerce.shoppy.core.model.Product
import com.ecommerce.shoppy.core.model.Review
import com.ecommerce.shoppy.ui.cart.data.local.entity.CartItem
import com.ecommerce.shoppy.ui.cart.data.local.relation.CartItemWithProduct
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("DefaultLocale")
@Composable
fun InvoiceWidget(cart: Cart) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.sdp),
        modifier = Modifier
            .background(color = Blue.copy(alpha = 0.1f), shape = RoundedCornerShape(12.sdp))
            .padding(10.sdp)
            .fillMaxWidth()
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            SvgImage(
                asset = "receipt",
                color = Blue,
                modifier = Modifier.size(18.sdp, 18.sdp)
            )

            Spacer(Modifier.width(8.sdp))

            Text(
                text = stringResource(R.string.receipt),
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(Modifier)

        HeadingValue(
            heading = stringResource(R.string.sub_total),
            value = "$${String.format("%.2f", cart.getSubTotalPrice())}",
        )

        HorizontalDivider()

        HeadingValue(
            heading = stringResource(R.string.delivery_fee),
            value = "$10",
        )

        HorizontalDivider()

        HeadingValue(
            heading = stringResource(R.string.platform_fee),
            value = "$2",
        )

        HorizontalDivider()

        HeadingValue(
            heading = stringResource(R.string.vat),
            value = "$1",
        )
    }
}

@Composable
private fun HeadingValue(heading: String, value: String, subValue: String? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = heading,
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.3f)
        )

        Text(
            text = value,
            fontSize = 13.ssp,
            lineHeight = 13.ssp,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.7f)
        )

        Spacer(Modifier.width(3.sdp))

        if (subValue != null)
            Text(
                text = subValue,
                fontSize = 9.ssp,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
    }
}

@Preview
@Composable
private fun PreviewInvoiceWidget() {
    InvoiceWidget(
        Cart(
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