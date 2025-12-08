package com.example.myapplication.ui.product_detail.presentation.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    val expandedAppBarHeight = 200.dp
    val scrollState = scrollBehavior.state
    val headerTranslation = (expandedAppBarHeight / 2)
    val appBarExpanded = remember { derivedStateOf { scrollState.collapsedFraction < 0.9f } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(0f)
    ) {
        CollapsedAppBar(onBackPressed)
        ExpandedAppBar(scrollBehavior, appBarExpanded.value, headerTranslation, product)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollapsedAppBar(onBackPressed: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
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
        },
        actions = {
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
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = MaterialTheme.colorScheme.onTertiary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    visible: Boolean,
    headerTranslation: Dp,
    product: Product
) {
    val pagerState = rememberPagerState { product.images?.size ?: 0 }

    TopAppBar(
        scrollBehavior = scrollBehavior,
        windowInsets = WindowInsets(0),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        title = {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween()),
                exit = fadeOut(animationSpec = tween()),
                modifier = Modifier
                    .height(250.dp)
                    .graphicsLayer {
                        translationY = scrollBehavior.state.collapsedFraction * headerTranslation.toPx()
                    },
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) { page ->
                    NetworkImage(
                        showShimmerWhenLoading = false,
                        imageUrl = product.images?.get(page) ?: "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        },
    )
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