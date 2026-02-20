package com.example.myapplication.ui.cart.presentation.cart.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.config.components.image.NetworkImage
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.theme.Yellow
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
    onFavClicked: () -> Unit,
    onRemoveClicked: () -> Unit,
    onAddClicked: () -> Unit,
    onMinusClicked: () -> Unit
) {
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            NetworkImage(
                showShimmerWhenLoading = false,
                imageUrl = cartItem.product.thumbnail ?: "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(70.sdp, 70.sdp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.sdp)
                    )
            )

            Spacer(Modifier.width(10.sdp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Text(
                    text = cartItem.product.title ?: "",
                    fontSize = 13.ssp,
                    lineHeight = 13.ssp,
                    color = MaterialTheme.colorScheme.onSurface,
                )


                cartItem.product.brand?.let {
                    Spacer(Modifier.height(5.sdp))

                    Text(
                        text = it,
                        fontSize = 11.ssp,
                        lineHeight = 11.ssp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )
                }

                Spacer(Modifier.height(5.sdp))

                Text(
                    text = "$${String.format("%.2f", cartItem.product.calculateTotal(cartItem.cartItem.quantity))}",
                    fontSize = 14.ssp,
                    lineHeight = 14.ssp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Spacer(Modifier.height(10.sdp))

        HorizontalDivider(modifier = Modifier.height(2.sdp))

        Spacer(Modifier.height(10.sdp))

        if ((cartItem.product.stock ?: 0) < 10) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 0.5.dp, color = Yellow, shape = RoundedCornerShape(8.sdp))
                    .background(Yellow.copy(alpha = 0.1f), shape = RoundedCornerShape(8.sdp))
                    .padding(horizontal = 5.sdp, vertical = 8.sdp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    tint = Yellow,
                    contentDescription = null,
                    modifier = Modifier.size(15.sdp),
                )

                Spacer(Modifier.width(5.sdp))

                Text(
                    text = stringResource(R.string.stock_left_).replace(
                        "@value",
                        cartItem.product.stock?.toString() ?: ""
                    ),
                    color = Yellow,
                    fontSize = 11.ssp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(10.sdp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(width = 1.sdp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                        .size(25.sdp)
                        .clickable { onMinusClicked.invoke() },
                ) {
                    SvgImage(
                        asset = "minus",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(13.sdp)
                    )
                }

                Spacer(Modifier.width(10.sdp))

                Text(
                    text = cartItem.cartItem.quantity.toString(),
                    fontSize = 12.ssp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(Modifier.width(10.sdp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(width = 1.sdp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                        .size(25.sdp)
                        .clickable { onAddClicked.invoke() },

                    ) {
                    SvgImage(
                        asset = "plus",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(13.sdp)
                    )
                }
            }

            Spacer(Modifier.width(10.sdp))

            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(25.sdp)
                        .background(color = Color.Transparent, shape = CircleShape)
                        .clickable { onFavClicked.invoke() },
                ) {
                    SvgImage(
                        asset = if (cartItem.product.isFavourite) "fav_filled" else "fav_outline",
                        color = if (cartItem.product.isFavourite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.size(19.sdp)
                    )
                }

                Spacer(Modifier.width(10.sdp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(25.sdp)
                        .background(color = Color.Transparent, shape = CircleShape)
                        .clickable { onRemoveClicked.invoke() },
                ) {
                    SvgImage(
                        asset = "delete",
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.size(20.sdp)
                    )
                }
            }
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
        onFavClicked = {},
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