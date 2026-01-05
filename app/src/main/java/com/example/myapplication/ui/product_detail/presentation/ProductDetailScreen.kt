package com.example.myapplication.ui.product_detail.presentation

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myapplication.R
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
import com.example.myapplication.ui.product_detail.presentation.components.ProductDetailTopBar
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: Int, viewModel: ProductDetailViewModel = koinViewModel()) {
    val scope = rememberCoroutineScope()
    val product = viewModel.product.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val navController = LocalParentNavController.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val tabs = listOf(R.string.info, R.string.warranty, R.string.reviews, R.string.dimension)
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
                    .drawBehind { topShadowScope(topStartCorner = 16.dp, topEndCorner = 16.dp) }
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 8.sdp, vertical = 10.sdp)) {
                        Text(
                            text = product.value?.title ?: "",
                            fontSize = 14.ssp,
                            lineHeight = 14.ssp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Spacer(modifier = Modifier.height(10.sdp))

                        Text(
                            text = product.value?.description ?: "",
                            fontSize = 11.ssp,
                            lineHeight = 11.ssp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                stickyHeader {
                    TabRow(containerColor = Color.Transparent, selectedTabIndex = pagerState.currentPage) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                                text = {
                                    Text(
                                        fontSize = 11.ssp,
                                        lineHeight = 11.ssp,
                                        text = stringResource(title),
                                        fontWeight = FontWeight.Medium,
                                    )
                                }
                            )
                        }
                    }
                }

                item {
                    HorizontalPager(state = pagerState, modifier = Modifier.fillParentMaxHeight()) { page ->
                        when (page) {
                            0 -> Box { }
                            1 -> Box { }
                            2 -> Box { }
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
