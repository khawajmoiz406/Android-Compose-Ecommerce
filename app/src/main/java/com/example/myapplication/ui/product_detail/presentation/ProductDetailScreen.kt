package com.example.myapplication.ui.product_detail.presentation

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myapplication.R
import com.example.myapplication.config.theme.Blue
import com.example.myapplication.config.theme.Green
import com.example.myapplication.config.theme.Orange
import com.example.myapplication.config.theme.Pink
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.config.utils.ComposableUtils.topShadowScope
import com.example.myapplication.di.createApiService
import com.example.myapplication.di.createOkHttpClient
import com.example.myapplication.di.createRetrofit
import com.example.myapplication.di.createRoomDatabase
import com.example.myapplication.models.response.product.Dimensions
import com.example.myapplication.models.response.product.Meta
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.Review
import com.example.myapplication.ui.product_detail.data.ProductDetailRepositoryImpl
import com.example.myapplication.ui.product_detail.data.local.ProductDetailLocalRepoImpl
import com.example.myapplication.ui.product_detail.data.remote.ProductDetailRemoteRepoImpl
import com.example.myapplication.ui.product_detail.domain.GetProductDetailUseCase
import com.example.myapplication.ui.product_detail.presentation.components.DimensionView
import com.example.myapplication.ui.product_detail.presentation.components.InfoView
import com.example.myapplication.ui.product_detail.presentation.components.ProductDetailTopBar
import com.example.myapplication.ui.product_detail.presentation.components.ReviewBar
import com.example.myapplication.ui.product_detail.presentation.components.ReviewsView
import com.example.myapplication.ui.product_detail.presentation.components.RoundedCard
import com.example.myapplication.ui.product_detail.presentation.components.RoundedText
import com.example.myapplication.ui.product_detail.presentation.components.WarrantyView
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: Int, viewModel: ProductDetailViewModel = koinViewModel()) {
    val scope = rememberCoroutineScope()
    val product = viewModel.product.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val navController = LocalParentNavController.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val tabs = listOf(R.string.info, R.string.warranty, R.string.dimension, R.string.reviews)
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })

    LaunchedEffect(productId) {
        if (viewModel.product.value == null) viewModel.getProductDetail(productId.toString())
    }

    // TODO: Add shimmer layout here
    if (product.value == null || uiState.value.isLoading) return Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = { CircularProgressIndicator() }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProductDetailTopBar(
                scrollBehavior = scrollBehavior,
                product = product.value!!,
                onBackPressed = { navController?.popBackStack() },
                onFavToggle = { product.value?.isFavourite = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .zIndex(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .drawBehind { topShadowScope(topStartCorner = 25.dp, topEndCorner = 25.dp) }
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                    )
            ) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 12.sdp)) {

                        Spacer(modifier = Modifier.height(15.sdp))

                        Row {
                            RoundedText(
                                bold = true,
                                text = product.value?.brand ?: "",
                                textColor = MaterialTheme.colorScheme.primary,
                                backgroundColor = MaterialTheme.colorScheme.surfaceDim
                            )

                            if (product.value?.tags?.isNotEmpty() == true) {
                                Spacer(modifier = Modifier.width(5.sdp))

                                product.value?.tags?.map { tag ->
                                    RoundedText(
                                        text = "#${tag}",
                                        textColor = MaterialTheme.colorScheme.onSurface,
                                        backgroundColor = MaterialTheme.colorScheme.surfaceDim
                                    )

                                    Spacer(modifier = Modifier.width(5.sdp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.sdp))

                        Text(
                            text = product.value?.title ?: "",
                            fontSize = 18.ssp,
                            lineHeight = 18.ssp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                        )

                        Spacer(modifier = Modifier.height(10.sdp))

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            ReviewBar(rating = product.value?.rating?.toFloat() ?: 0f)

                            Spacer(modifier = Modifier.width(5.sdp))

                            Text(
                                text = product.value?.rating.toString(),
                                fontSize = 14.ssp,
                                lineHeight = 14.ssp,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold,
                            )

                            Spacer(modifier = Modifier.width(5.sdp))

                            Text(
                                text = "(${product.value?.reviews?.size ?: 0} ${stringResource(R.string.reviews).lowercase()})",
                                fontSize = 10.ssp,
                                lineHeight = 10.ssp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            )
                        }

                        Spacer(modifier = Modifier.height(15.sdp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "$${product.value?.price.toString()}",
                                fontSize = 23.ssp,
                                lineHeight = 23.ssp,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Black,
                            )

                            Spacer(modifier = Modifier.width(5.sdp))

                            Text(
                                text = "$${String.format("%.2f", product.value?.getPriceBeforeDiscount())}",
                                fontSize = 14.ssp,
                                lineHeight = 14.ssp,
                                fontWeight = FontWeight.Medium,
                                textDecoration = TextDecoration.LineThrough,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            )
                        }

                        Spacer(modifier = Modifier.height(15.sdp))

                        Column {
                            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                                RoundedCard(
                                    icon = "truck",
                                    foregroundColor = Blue,
                                    heading = stringResource(R.string.shipping),
                                    value = product.value?.shippingInformation?.replace("Ships in", "") ?: "",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                )

                                Spacer(Modifier.width(8.sdp))

                                RoundedCard(
                                    icon = "rotate",
                                    foregroundColor = Green,
                                    heading = stringResource(R.string.returns),
                                    value = product.value?.returnPolicy ?: "",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                )
                            }

                            Spacer(Modifier.height(8.sdp))

                            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                                RoundedCard(
                                    icon = "shield",
                                    foregroundColor = Pink,
                                    heading = stringResource(R.string.warranty),
                                    value = product.value?.warrantyInformation ?: "",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                )

                                Spacer(Modifier.width(8.sdp))

                                RoundedCard(
                                    icon = "box",
                                    foregroundColor = Orange,
                                    heading = stringResource(R.string.stock),
                                    value = stringResource(R.string.stock_left).replace(
                                        "@value",
                                        product.value?.stock?.toString() ?: ""
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(15.sdp))
                    }
                }

                stickyHeader {
                    ScrollableTabRow(
                        containerColor = Color.Transparent,
                        selectedTabIndex = pagerState.currentPage,
                        edgePadding = 0.dp,
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                        )
                    ) {
                        tabs.forEachIndexed { index, title ->
                            val selected = pagerState.currentPage == index

                            Tab(
                                selected = selected,
                                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                text = {
                                    Text(
                                        fontSize = 12.ssp,
                                        lineHeight = 12.ssp,
                                        text = stringResource(title),
                                    )
                                }
                            )
                        }
                    }
                }

                item {
                    HorizontalPager(state = pagerState) { page ->
                        when (page) {
                            0 -> InfoView(product.value!!)
                            1 -> WarrantyView(product.value!!)
                            2 -> DimensionView(product.value!!)
                            3 -> ReviewsView(product.value!!)
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ProductDetailPreview() {
    val context = LocalContext.current

    ProductDetailScreen(
        -1, ProductDetailViewModel(
            GetProductDetailUseCase(
                ProductDetailRepositoryImpl(
                    ProductDetailRemoteRepoImpl(
                        context,
                        createApiService(createRetrofit(createOkHttpClient()))
                    ),
                    ProductDetailLocalRepoImpl(
                        createRoomDatabase(context)
                    )
                )
            )
        ).apply {
            product.value = Product(
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
        }
    )
}
