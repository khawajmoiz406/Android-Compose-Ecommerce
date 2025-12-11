package com.example.myapplication.ui.product_detail.presentation.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.example.myapplication.models.response.product.Dimensions
import com.example.myapplication.models.response.product.Meta
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.Review
import com.example.myapplication.utils.components.NetworkImage
import com.example.myapplication.utils.components.SvgImage
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailTopBar(scrollBehavior: TopAppBarScrollBehavior, product: Product, onBackPressed: () -> Unit) {
    val collapsedAppBarHeightPx = remember { mutableIntStateOf(0) }
    val expandedAppBarHeightPx = remember { mutableIntStateOf(0) }
    var maxHeightPx = collapsedAppBarHeightPx.intValue + expandedAppBarHeightPx.intValue
    val heightOffset = scrollBehavior.state.heightOffset

    LaunchedEffect(collapsedAppBarHeightPx) {
        if (collapsedAppBarHeightPx.intValue > 0) {
            maxHeightPx = expandedAppBarHeightPx.intValue + collapsedAppBarHeightPx.intValue
        }
    }

    LaunchedEffect(expandedAppBarHeightPx) {
        if (expandedAppBarHeightPx.intValue > 0) {
            scrollBehavior.state.heightOffsetLimit = -expandedAppBarHeightPx.intValue.toFloat()
            maxHeightPx = expandedAppBarHeightPx.intValue + collapsedAppBarHeightPx.intValue
        }
    }

    Layout(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .zIndex(0f),
        content = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                content = { CollapsedAppBar(product, onBackPressed) }
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                content = { ExpandedAppBar(product) }
            )
        }
    ) { measurables, constraints ->
        val collapsedPlaceable = measurables[0].measure(constraints)
        val expandedPlaceable = measurables[1].measure(constraints)

        if (collapsedAppBarHeightPx.intValue == 0 && collapsedPlaceable.height > 0) {
            collapsedAppBarHeightPx.intValue = collapsedPlaceable.height
        }

        if (expandedAppBarHeightPx.intValue == 0 && expandedPlaceable.height > 0) {
            expandedAppBarHeightPx.intValue = expandedPlaceable.height
        }

        val finalHeight = maxOf(collapsedPlaceable.height, (maxHeightPx + heightOffset).toInt())

        layout(constraints.maxWidth, finalHeight) {
            collapsedPlaceable.place(0, 0)
            expandedPlaceable.place(0, collapsedPlaceable.height)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollapsedAppBar(product: Product, onBackPressed: () -> Unit) {
    val isFavourite = remember { mutableStateOf(product.isFavourite ?: false) }

    Row(modifier = Modifier.padding(top = 40.sdp, bottom = 12.sdp, start = 10.sdp, end = 10.sdp)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface)
                .size(35.sdp)
                .clickable { onBackPressed() },
        ) {
            SvgImage(
                asset = "back",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.sdp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface)
                .size(35.sdp)
                .clickable { onBackPressed() },
        ) {
            SvgImage(
                asset = if (isFavourite.value) "fav_filled" else "fav_outline",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.sdp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedAppBar(product: Product) {
    val pagerState = rememberPagerState { product.images?.size ?: 0 }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.sdp)
            .height(200.sdp)
    ) { page ->
        NetworkImage(
            showShimmerWhenLoading = false,
            imageUrl = product.images?.get(page) ?: "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 200)
@Composable
private fun PreviewProductDetailTopBar() {
    ProductDetailTopBar(
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
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
        )
    ) { }
}