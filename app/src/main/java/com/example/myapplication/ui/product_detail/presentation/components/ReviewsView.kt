package com.example.myapplication.ui.product_detail.presentation.components

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.config.components.SvgImage
import com.example.myapplication.config.theme.Yellow
import com.example.myapplication.models.response.product.Dimensions
import com.example.myapplication.models.response.product.Meta
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.Review
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ReviewsView(product: Product) {
    Column(Modifier.padding(10.sdp)) {

        RatingComposable(product)

        Spacer(Modifier.height(10.sdp))

        ButtonComposable()

        Spacer(Modifier.height(10.sdp))

        ReviewsComposable(product)
    }
}

@Composable
private fun RatingComposable(product: Product) {
    Column(
        modifier = Modifier
            .background(Yellow.copy(alpha = 0.1f), shape = RoundedCornerShape(10.sdp))
            .padding(10.sdp)
    ) {
        Row {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = product.rating?.toString() ?: "",
                    fontSize = 26.ssp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                ReviewBar(
                    rating = product.rating?.toFloat() ?: 0f,
                    starSize = 12.sdp,
                    spacing = 2.sdp
                )

                Spacer(Modifier.height(5.sdp))

                Text(
                    text = stringResource(R.string.out_of_five),
                    fontSize = 9.ssp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(Modifier.width(10.sdp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SvgImage(
                        asset = "users",
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(10.sdp, 10.sdp)
                    )

                    Spacer(Modifier.width(5.sdp))

                    Text(
                        text = "${product.reviews?.size} ${stringResource(R.string.reviews).lowercase()}",
                        fontSize = 10.ssp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }

                Text(
                    text = stringResource(R.string.reviews_msg),
                    fontSize = 9.ssp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Spacer(Modifier.height(10.sdp))

        repeat(5) { index ->
            val reverseIndex = 4 - index

            ItemRating(
                index = reverseIndex,
                count = product.reviews?.size,
                value = product.reviews?.count { it.rating == (reverseIndex + 1) },
            )

            if (index < 4) Spacer(Modifier.height(5.sdp))
        }
    }
}

@Composable
private fun ButtonComposable() {
    Button(
        onClick = { },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.sdp)
    ) {
        SvgImage(
            asset = "star_hollow",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(11.sdp, 11.sdp)
        )

        Spacer(Modifier.width(5.sdp))

        Text(
            text = stringResource(R.string.write_a_review),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.height(14.sdp)
        )
    }
}

@Composable
private fun ReviewsComposable(product: Product) {
    product.reviews?.mapIndexed { index, review ->
        ItemReview(review)

        if (index < (product.reviews.size - 1)) Spacer(Modifier.height(10.sdp))
    }
}

@Composable
private fun ItemRating(index: Int, value: Int?, count: Int?) {
    val notNullValue = if (value == null || value <= 0) 0f else value.toFloat()
    val progress = (notNullValue * 100) / (count ?: 0)

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "${index + 1}",
            fontSize = 11.ssp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.width(5.sdp))

        SvgImage(
            asset = "star_filled",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(9.sdp, 9.sdp)
        )

        Spacer(Modifier.width(10.sdp))

        LinearProgressIndicator(
            progress = { progress / 100 },
            trackColor = MaterialTheme.colorScheme.surfaceDim,
            color = MaterialTheme.colorScheme.primary,
            strokeCap = StrokeCap.Round,
            modifier = Modifier
                .weight(1f)
                .height(7.sdp),
        )

        Spacer(Modifier.width(10.sdp))

        Text(
            text = "${notNullValue.toInt()}",
            fontSize = 11.ssp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun ItemReview(review: Review) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.sdp, MaterialTheme.colorScheme.surfaceDim, shape = RoundedCornerShape(10.sdp))
            .padding(10.sdp)
    ) {
        Row {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(25.sdp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
            ) {
                Text(
                    text = review.reviewerName?.firstOrNull()?.toString() ?: "",
                    fontSize = 10.ssp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.background
                )
            }

            Spacer(Modifier.width(8.sdp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = review.reviewerName ?: "",
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.SemiBold,
                    softWrap = false,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(Modifier.height(1.sdp))

                Text(
                    text = review.date?.split("T")?.firstOrNull() ?: "",
                    fontSize = 10.ssp,
                    fontWeight = FontWeight.Light,
                    softWrap = false,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Box(
                modifier = Modifier
                    .background(Yellow.copy(alpha = 0.1f), shape = RoundedCornerShape(10.sdp))
                    .padding(horizontal = 7.sdp, vertical = 3.sdp)
            ) {
                ReviewBar(
                    rating = review.rating?.toFloat() ?: 0f,
                    starSize = 12.sdp,
                    spacing = 2.sdp
                )
            }
        }

        Spacer(Modifier.height(10.sdp))

        Text(
            text = review.comment ?: "",
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceDim,
                    shape = RoundedCornerShape(10.sdp)
                )
                .padding(10.sdp)
        )
    }

}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewReviewsView() {
    ReviewsView(
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