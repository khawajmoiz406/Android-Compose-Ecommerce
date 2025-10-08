package com.example.myapplication.ui.dashboard.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.di.createApiService
import com.example.myapplication.di.createOkHttpClient
import com.example.myapplication.di.createRetrofit
import com.example.myapplication.models.response.product.Dimensions
import com.example.myapplication.models.response.product.Meta
import com.example.myapplication.models.response.product.Product
import com.example.myapplication.models.response.product.Review
import com.example.myapplication.navigation.Destinations
import com.example.myapplication.ui.dashboard.home.data.HomeRemoteRepoImpl
import com.example.myapplication.ui.dashboard.home.domain.GetProductsUseCase
import com.example.myapplication.utils.components.ItemProduct
import com.example.myapplication.utils.components.SwipeRefresh
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val products = viewModel.products.collectAsState()

    if (products.value.isEmpty() && uiState.value.isLoading) return Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = { CircularProgressIndicator() }
    )

    SwipeRefresh(
        isRefreshing = uiState.value.isRefreshing,
        onRefresh = { viewModel.getProducts(fromSwipe = true) }
    ) {
        if (uiState.value.error.isNotEmpty()) Box(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center,
            content = { Text(text = uiState.value.error) }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(products.value) { item ->
                ItemProduct(item = item, onClick = { handleItemClicked(navController = navController, item.id) })
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
    val navController = rememberNavController()
    HomeScreen(
        navController,
        HomeViewModel(
            GetProductsUseCase(
                HomeRemoteRepoImpl(
                    LocalContext.current,
                    createApiService(createRetrofit(createOkHttpClient()))
                )
            )
        ).also {
            it.products.value = listOf(
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
            )
        }
    )
}