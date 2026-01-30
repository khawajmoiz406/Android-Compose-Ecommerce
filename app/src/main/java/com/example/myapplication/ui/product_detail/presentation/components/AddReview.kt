package com.example.myapplication.ui.product_detail.presentation.components

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.copy
import com.example.myapplication.R
import com.example.myapplication.config.components.NetworkImage
import com.example.myapplication.config.components.SvgImage
import com.example.myapplication.models.response.product.Dimensions
import com.example.myapplication.models.response.product.Meta
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.Review
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AddReview(product: Product, onAddClicked: (name: String, review: String, rating: Int) -> Unit) {
    var name = ""
    var review = ""
    var rating = 5

    Column(
        verticalArrangement = Arrangement.spacedBy(15.sdp),
        modifier = Modifier
            .padding(horizontal = 10.sdp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        HeadingComposable()

        HorizontalDivider(thickness = 2.sdp)

        ProductInfoComposable(product)

        RatingComposable { rating = it }

        NameComposable { name = it }

        ReviewComposable { review = it }

        ButtonComposable { onAddClicked.invoke(name, review, rating) }

        InfoComposable()

        Spacer(Modifier.height(5.sdp))
    }
}

@Composable
private fun HeadingComposable() {
    Column {
        Text(
            text = stringResource(R.string.write_a_review),
            fontSize = 16.ssp,
            lineHeight = 16.ssp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(2.sdp))

        Text(
            text = stringResource(R.string.write_a_review_msg),
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun ProductInfoComposable(product: Product) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceDim,
                shape = RoundedCornerShape(12.sdp)
            )
            .padding(10.sdp)
            .fillMaxWidth()
    ) {
        NetworkImage(
            showShimmerWhenLoading = false,
            imageUrl = product.thumbnail ?: "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(50.sdp, 50.sdp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(12.sdp)
                )
        )

        Spacer(Modifier.width(10.sdp))

        Column {
            Text(
                text = product.title ?: "",
                maxLines = 2,
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(Modifier.height(1.sdp))

            Text(
                text = product.brand ?: "",
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun RatingComposable(onRatingChanged: (Int) -> Unit) {
    val rating = remember { mutableIntStateOf(5) }

    Column {
        Text(
            text = stringResource(R.string.your_rating),
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(5.sdp))

        ReviewBar(
            rating = 5f,
            spacing = 6.sdp,
            starSize = 23.sdp,
            interactive = true,
            onRatingChanged = {
                rating.intValue = it.toInt()
                onRatingChanged.invoke(rating.intValue)
            }
        )

        Spacer(Modifier.height(8.sdp))

        Text(
            text = stringResource(getRatingText(rating.intValue)),
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun NameComposable(onNameChanged: (String) -> Unit) {
    val name = remember { mutableStateOf("") }

    Column {
        Text(
            text = stringResource(R.string.your_name),
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(5.sdp))

        OutlinedTextField(
            singleLine = true,
            value = name.value,
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_you_name),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            },
            onValueChange = {
                name.value = it
                onNameChanged.invoke(it)
            },
        )
    }
}

@Composable
private fun ReviewComposable(onReviewChanged: (String) -> Unit) {
    val name = remember { mutableStateOf("") }

    Column {
        Text(
            text = stringResource(R.string.your_review),
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(5.sdp))

        OutlinedTextField(
            value = name.value,
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.sdp),
            placeholder = {
                Text(
                    text = stringResource(R.string.share_your_experience),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            },
            onValueChange = {
                name.value = it
                onReviewChanged.invoke(it)
            },
        )
    }
}

@Composable
private fun ButtonComposable(onAddClicked: () -> Unit) {
    Button(
        onClick = { onAddClicked.invoke() },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.sdp)
    ) {
        SvgImage(
            asset = "send",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(11.sdp, 11.sdp)
        )

        Spacer(Modifier.width(5.sdp))

        Text(
            text = stringResource(R.string.submit_review),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.height(14.sdp)
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun InfoComposable() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.2f), shape = RoundedCornerShape(10.sdp))
            .padding(10.sdp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SvgImage(
                asset = "info",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(11.sdp, 11.sdp)
            )

            Spacer(Modifier.width(5.sdp))

            Text(
                text = stringResource(R.string.review_guidelines),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(Modifier.height(3.sdp))

        Text(
            text = stringResource(R.string.review_guidelines_msg),
            fontSize = 10.ssp,
            lineHeight = 10.ssp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

private fun getRatingText(rating: Int): Int {
    return when (rating) {
        1 -> R.string.poor
        2 -> R.string.below_average
        3 -> R.string.average
        4 -> R.string.good
        else -> R.string.excellent
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewAddReview() {
    AddReview(
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
    ) { _, _, _ -> }
}