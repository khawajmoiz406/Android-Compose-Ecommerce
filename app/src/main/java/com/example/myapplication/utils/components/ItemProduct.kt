package com.example.myapplication.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.models.response.product.Dimensions
import com.example.myapplication.models.response.product.Meta
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.Review

@Composable
fun ItemProduct(item: Product, onClick: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .height(300.dp)
    ) {
        AsyncImage(
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.thumbnail)
                .crossfade(true)
                .crossfade(500)
                .build(),
        )

        Box(modifier = Modifier.weight(0.4f).background(Color.White)) {
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                Spacer(Modifier.height(10.dp))

                Text(text = item.title ?: "", fontSize = 15.sp, fontWeight = FontWeight.Medium)

                Spacer(Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "$${item.price}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f, fill = true)
                    )

                    Spacer(Modifier.width(5.dp))

                    Column {
                        Text(text = "${stringResource(R.string.brand)}: ${item.brand?.trim()}", fontSize = 12.sp)

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                        ) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                            Spacer(Modifier.width(5.dp))
                            Text(text = "${stringResource(R.string.rating)}: ${item.rating}", fontSize = 12.sp)
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Text(text = "${stringResource(R.string.in_stock)}: ${item.stock}", fontSize = 10.sp)

                Text(text = item.returnPolicy ?: "", fontSize = 10.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemProduct() {
    ItemProduct(
        Product(
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
                "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp"
            ),
            thumbnail = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp"
        ),
    )
}