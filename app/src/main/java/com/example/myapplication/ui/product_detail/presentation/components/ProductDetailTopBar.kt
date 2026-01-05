package com.example.myapplication.ui.product_detail.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.zIndex
import com.example.myapplication.config.components.NetworkImage
import com.example.myapplication.config.components.SvgImage
import com.example.myapplication.models.response.product.Product
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    product: Product,
    onBackPressed: () -> Unit,
    onFavToggle: (Boolean) -> Unit
) {
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
                content = { CollapsedAppBar(product, onBackPressed, onFavToggle) }
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
private fun CollapsedAppBar(product: Product, onBackPressed: () -> Unit, onFavToggle: (Boolean) -> Unit) {
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
                .clickable {
                    isFavourite.value = !isFavourite.value
                    onFavToggle.invoke(isFavourite.value)
                },
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
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { product.images?.size ?: 0 })

    Box {
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

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .wrapContentWidth()
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 10.sdp),
        ) {
            product.images?.forEachIndexed { index, _ ->
                val isSelected = pagerState.currentPage == index
                val size = animateDpAsState(targetValue = if (isSelected) 8.sdp else 8.sdp)
                val color = animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                )

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(size.value)
                        .background(color.value)
                        .clickable { scope.launch { pagerState.animateScrollToPage(index) } }
                )

                if (index != product.images.lastIndex) Spacer(modifier = Modifier.width(2.sdp))
            }
        }
    }
}