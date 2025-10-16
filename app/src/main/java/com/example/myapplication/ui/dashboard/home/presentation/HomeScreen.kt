package com.example.myapplication.ui.dashboard.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.di.createApiService
import com.example.myapplication.di.createOkHttpClient
import com.example.myapplication.di.createRetrofit
import com.example.myapplication.models.response.HomeResponse
import com.example.myapplication.models.response.product.Dimensions
import com.example.myapplication.models.response.product.Meta
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.Review
import com.example.myapplication.navigation.Destinations
import com.example.myapplication.ui.dashboard.home.data.HomeRemoteRepoImpl
import com.example.myapplication.ui.dashboard.home.domain.GetHomeUseCase
import com.example.myapplication.ui.dashboard.home.domain.GetProductsUseCase
import com.example.myapplication.ui.dashboard.home.presentation.components.HeadingRow
import com.example.myapplication.ui.dashboard.home.presentation.components.ItemCategory
import com.example.myapplication.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.utils.AppCompositionLocals.LocalTopAppBarScrollBehavior
import com.example.myapplication.utils.components.ItemProduct
import com.example.myapplication.utils.components.NoData
import com.example.myapplication.utils.components.SwipeRefresh
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val selectedCategory = remember { mutableIntStateOf(0) }
    val parentNavController = LocalParentNavController.current
    val scrollBehavior = LocalTopAppBarScrollBehavior.current
    val homeData = viewModel.homeResponse.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    if (homeData.value == null && uiState.value.isLoading) return Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = { CircularProgressIndicator() }
    )

    SwipeRefresh(
        isRefreshing = uiState.value.isRefreshing,
        onRefresh = { viewModel.getProducts(fromSwipe = true) },
        parentNestedScrollConnection = scrollBehavior?.nestedScrollConnection
    ) {
        when {
            uiState.value.error.isNotEmpty() -> NoData(message = uiState.value.error)
            homeData.value?.products.isNullOrEmpty() -> NoData(message = stringResource(R.string.no_products))
            else -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.sdp),
                horizontalArrangement = Arrangement.spacedBy(10.sdp),
                modifier = Modifier.fillMaxHeight()
            ) {
                if (!homeData.value?.categories.isNullOrEmpty())
                    item(span = { GridItemSpan(2) }) {
                        Column {
                            Spacer(Modifier.height(10.sdp))

                            HeadingRow(heading = stringResource(R.string.categories))

                            Spacer(Modifier.height(10.sdp))

                            LazyRow {
                                itemsIndexed(homeData.value!!.categories!!) { index, item ->
                                    ItemCategory(
                                        category = item,
                                        index = index,
                                        selectedIndex = selectedCategory.intValue,
                                        onItemClick = { selectedCategory.intValue = index }
                                    )
                                }
                            }
                        }
                    }

                item(span = { GridItemSpan(2) }) {
                    Spacer(Modifier.height(10.sdp))

                    Text(
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.SemiBold,
                        text = stringResource(R.string.products),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 10.sdp)
                    )
                }

                itemsIndexed(homeData.value!!.products!!) { index, item ->
                    ItemProduct(
                        item = item,
                        index = index,
                        onClick = { parentNavController?.let { handleItemClicked(it, item.id) } },
                    )
                }

                item(span = { GridItemSpan(2) }) {
                    Spacer(Modifier.height(10.sdp))
                }
            }
        }
    }
}

private fun handleItemClicked(navController: NavController, productId: Int?) {
    if (productId == null) return
    navController.navigate(Destinations.ProductDetail.createRoute(productId)) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        restoreState = true
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        HomeViewModel(
            GetProductsUseCase(
                HomeRemoteRepoImpl(
                    LocalContext.current,
                    createApiService(createRetrofit(createOkHttpClient()))
                )
            ),
            GetHomeUseCase(
                HomeRemoteRepoImpl(
                    LocalContext.current,
                    createApiService(createRetrofit(createOkHttpClient()))
                )
            )
        ).apply {
            homeResponse.value = HomeResponse(
                mutableListOf(
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
                ),
                listOf(),
            )
        }
    )
}