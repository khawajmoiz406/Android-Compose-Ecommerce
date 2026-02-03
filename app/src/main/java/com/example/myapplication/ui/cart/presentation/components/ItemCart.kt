package com.example.myapplication.ui.cart.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.config.components.image.NetworkImage
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.core.model.Dimensions
import com.example.myapplication.core.model.Meta
import com.example.myapplication.core.model.Product
import com.example.myapplication.core.model.Review
import com.example.myapplication.ui.cart.data.local.entity.CartItem
import com.example.myapplication.ui.cart.data.local.relation.CartItemWithProduct
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("DefaultLocale")
@Composable
fun ItemCart(
    cartItem: CartItemWithProduct,
    index: Int,
    itemCount: Int,
    onRemoveClicked: () -> Unit,
    onAddClicked: () -> Unit,
    onMinusClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceDim,
                shape = RoundedCornerShape(
                    topStart = if (index == 0) 12.sdp else 0.sdp,
                    topEnd = if (index == 0) 12.sdp else 0.sdp,
                    bottomStart = if (index == (itemCount - 1)) 12.sdp else 0.sdp,
                    bottomEnd = if (index == (itemCount - 1)) 12.sdp else 0.sdp,
                )
            )
            .padding(
                start = 10.sdp,
                end = 10.sdp,
                top = 10.sdp,
                bottom = if (index == (itemCount - 1)) 10.sdp else 0.sdp
            )
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            NetworkImage(
                showShimmerWhenLoading = false,
                imageUrl = cartItem.product.thumbnail ?: "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(50.sdp, 50.sdp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.sdp)
                    )
            )

            Spacer(Modifier.width(10.sdp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = cartItem.product.title ?: "",
                        fontSize = 13.ssp,
                        lineHeight = 13.ssp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(color = Color.Transparent, shape = CircleShape)
                            .size(20.sdp)
                            .clickable { onRemoveClicked.invoke() },
                    ) {
                        SvgImage(
                            asset = "delete",
                            color = Red,
                            modifier = Modifier.size(15.sdp)
                        )
                    }
                }

                Spacer(Modifier.height(5.sdp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$${String.format("%.2f", cartItem.product.calculateTotal(cartItem.cartItem.quantity))}",
                        fontSize = 13.ssp,
                        lineHeight = 13.ssp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.outlineVariant, shape = CircleShape)
                            .size(20.sdp)
                            .clickable { onMinusClicked.invoke() },
                    ) {
                        SvgImage(
                            asset = "minus",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(10.sdp)
                        )
                    }

                    Spacer(Modifier.width(5.sdp))

                    Text(
                        text = cartItem.cartItem.quantity.toString(),
                        fontSize = 10.ssp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(Modifier.width(5.sdp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.outlineVariant, shape = CircleShape)
                            .size(20.sdp)
                            .clickable { onAddClicked.invoke() },

                        ) {
                        SvgImage(
                            asset = "plus",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(10.sdp)
                        )
                    }
                }
            }
        }

        if (index != (itemCount - 1)) {
            Spacer(Modifier.height(10.sdp))

            HorizontalDivider(
                modifier = Modifier
                    .height(2.sdp)
                    .padding(start = 60.sdp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewItemCart() {
    ItemCart(
        index = 0,
        itemCount = 1,
        onAddClicked = {},
        onMinusClicked = {},
        onRemoveClicked = {},
        cartItem = CartItemWithProduct(
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
        )
    )
}