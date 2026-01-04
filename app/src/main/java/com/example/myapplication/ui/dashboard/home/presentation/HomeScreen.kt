package com.example.myapplication.ui.dashboard.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.config.components.DashboardToolbar
import com.example.myapplication.config.components.HomeFilters
import com.example.myapplication.config.components.ItemProduct
import com.example.myapplication.config.components.NoData
import com.example.myapplication.config.components.ProgressBar
import com.example.myapplication.config.components.SwipeRefresh
import com.example.myapplication.config.utils.AppCompositionLocals.LocalDrawerStateController
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.di.createApiService
import com.example.myapplication.di.createOkHttpClient
import com.example.myapplication.di.createRetrofit
import com.example.myapplication.di.createRoomDatabase
import com.example.myapplication.models.response.HomeResponse
import com.example.myapplication.models.response.category.Category
import com.example.myapplication.models.response.product.Dimensions
import com.example.myapplication.models.response.product.Meta
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.Review
import com.example.myapplication.navigation.Destinations
import com.example.myapplication.ui.dashboard.home.data.HomeRepositoryImpl
import com.example.myapplication.ui.dashboard.home.data.local.HomeLocalRepoImpl
import com.example.myapplication.ui.dashboard.home.data.remote.HomeRemoteRepoImpl
import com.example.myapplication.ui.dashboard.home.domain.GetHomeUseCase
import com.example.myapplication.ui.dashboard.home.domain.GetProductsByCategoryUseCase
import com.example.myapplication.ui.dashboard.home.presentation.components.HeadingRow
import com.example.myapplication.ui.dashboard.home.presentation.components.ItemCategory
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val scope = rememberCoroutineScope()
    val drawerState = LocalDrawerStateController.current
    val selectedCategory = remember { mutableIntStateOf(0) }
    val parentNavController = LocalParentNavController.current
    val homeData = viewModel.homeResponse.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (homeData.value == null && uiState.value.isLoading) return Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = { CircularProgressIndicator() }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DashboardToolbar(
                drawerState = drawerState!!,
                scrollBehavior = scrollBehavior,
                onFilterClicked = { scope.launch { bottomSheetState.show() } },
                onSearchTextChanged = {
                    selectedCategory.intValue = 0
                    if (it.isBlank()) {
                        viewModel.getProductsByCategory("all")
                    } else {
                        viewModel.searchProduct(it)
                    }
                }
            )
        },
    ) { innerPadding ->
        SwipeRefresh(
            isRefreshing = uiState.value.isRefreshing,
            scrollBehavior = scrollBehavior,
            onRefresh = { onRefresh(viewModel, homeData.value, selectedCategory.intValue) },
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
        ) {
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                if (!homeData.value?.categories.isNullOrEmpty())
                    item {
                        CategoryRow(homeData.value, selectedCategory.intValue) { index, category ->
                            viewModel.getProductsByCategory(category.slug)
                            selectedCategory.intValue = index
                        }
                    }

                item {
                    Spacer(Modifier.height(10.sdp))

                    Text(
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.SemiBold,
                        text = stringResource(R.string.products),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 10.sdp)
                    )
                }

                when {
                    uiState.value.isLoading -> item {
                        Box(Modifier.fillParentMaxHeight(0.65f)) {
                            ProgressBar()
                        }
                    }

                    uiState.value.error.isNotEmpty() -> item {
                        Box(Modifier.fillParentMaxHeight(0.65f)) {
                            NoData(message = uiState.value.error, scrollable = false)
                        }
                    }

                    homeData.value?.products.isNullOrEmpty() -> item {
                        Box(Modifier.fillParentMaxHeight(0.65f)) {
                            NoData(message = stringResource(R.string.no_products), scrollable = false)
                        }
                    }

                    else -> itemsIndexed(homeData.value!!.products!!.chunked(2)) { index, items ->
                        ProductsRow(index, items) { item ->
                            parentNavController?.let { handleItemClicked(it, item.id) }
                        }
                    }
                }
            }
        }

        if (bottomSheetState.isVisible || bottomSheetState.currentValue != SheetValue.Hidden) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                windowInsets = WindowInsets(0),
                onDismissRequest = { scope.launch { bottomSheetState.hide() } },
                content = {
                    HomeFilters(defaultValue = viewModel.filters) {
                        viewModel.filterProduct(it)
                        scope.launch { bottomSheetState.hide() }
                    }
                }
            )
        }
    }
}

@Composable
private fun ProductsRow(rowIndex: Int, rowItems: List<Product>, onProductsClick: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = if (rowIndex == 0) 10.sdp else 0.sdp,
                bottom = 10.sdp,
                start = 10.sdp,
                end = 10.sdp
            )
    ) {
        repeat(2) { i ->
            val item = if (i < rowItems.size) rowItems[i] else null
            Box(modifier = Modifier.weight(1f)) {
                if (item != null)
                    ItemProduct(
                        item = item,
                        index = i,
                        onClick = { onProductsClick.invoke(item) }
                    )
            }
        }
    }
}

@Composable
private fun CategoryRow(homeData: HomeResponse?, selectedCategory: Int, onCategoryClicked: (Int, Category) -> Unit) {
    Column {
        Spacer(Modifier.height(10.sdp))

        HeadingRow(heading = stringResource(R.string.categories))

        Spacer(Modifier.height(10.sdp))

        LazyRow {
            itemsIndexed(homeData!!.categories!!) { index, item ->
                ItemCategory(
                    category = item,
                    index = index,
                    selectedIndex = selectedCategory,
                    onItemClick = { onCategoryClicked.invoke(index, item) }
                )
            }
        }
    }
}

private fun onRefresh(viewModel: HomeViewModel, homeData: HomeResponse?, selectedCategory: Int) =
    viewModel.getProductsByCategory(
        category = homeData?.categories?.get(selectedCategory)?.slug ?: "all",
        fromSwipe = true
    )

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
            GetHomeUseCase(
                HomeRepositoryImpl(
                    HomeRemoteRepoImpl(
                        LocalContext.current,
                        createApiService(createRetrofit(createOkHttpClient()))
                    ),
                    HomeLocalRepoImpl(
                        createRoomDatabase(LocalContext.current)
                    )
                )
            ),
            GetProductsByCategoryUseCase(
                HomeRepositoryImpl(
                    HomeRemoteRepoImpl(
                        LocalContext.current,
                        createApiService(createRetrofit(createOkHttpClient()))
                    ),
                    HomeLocalRepoImpl(
                        createRoomDatabase(LocalContext.current)
                    )
                )
            ),
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
                        isFavourite = false,
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
                        isFavourite = false,
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
                        isFavourite = false,
                        thumbnail = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp"
                    ),
                ),
                listOf(),
            )
        }
    )
}