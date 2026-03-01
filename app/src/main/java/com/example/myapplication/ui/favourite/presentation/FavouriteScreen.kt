package com.example.myapplication.ui.favourite.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.config.components.layout.CustomToolbar
import com.example.myapplication.config.components.layout.ItemProduct
import com.example.myapplication.config.components.layout.SwipeRefresh
import com.example.myapplication.config.components.state.EmptyState
import com.example.myapplication.config.navigation.Destination
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.core.model.Product
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(viewModel: FavouriteViewModel = koinViewModel()) {
    val parentNavController = LocalParentNavController.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val products = viewModel.products.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { CustomToolbar(stringResource(R.string.favourites), false) },
    ) { innerPadding ->
        when {
            uiState.value.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { CircularProgressIndicator() }
            )

            products.value.isEmpty() && uiState.value.error.isNotEmpty() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { EmptyState(message = uiState.value.error) })

            products.value.isEmpty() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = {
                    EmptyState(
                        message = stringResource(R.string.no_favourites_found),
                        imageAsset = "no_favourite"
                    )
                }
            )

            else -> SwipeRefresh(
                scrollBehavior = scrollBehavior,
                isRefreshing = uiState.value.isRefreshing,
                onRefresh = { viewModel.getAllFavourites(true) },
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .fillMaxSize(),
            ) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    itemsIndexed(
                        items = products.value.chunked(2),
                        key = { _, items -> items.joinToString(separator = "-") { it.id.toString() } }
                    ) { index, items ->
                        ProductsRow(
                            rowIndex = index,
                            rowItems = items,
                            onFavClicked = { viewModel.toggleFavourite(it) },
                            onProductsClick = { item ->
                                parentNavController?.let { handleItemClicked(it, item.id) }
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductsRow(
    rowIndex: Int,
    rowItems: List<Product>,
    onProductsClick: (Product) -> Unit,
    onFavClicked: (Product) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = if (rowIndex == 0) 10.sdp else 0.sdp, bottom = 10.sdp, start = 10.sdp, end = 10.sdp
            )
    ) {
        repeat(2) { i ->
            val item = if (i < rowItems.size) rowItems[i] else null
            Box(modifier = Modifier.weight(1f)) {
                if (item != null) ItemProduct(
                    item = item,
                    index = i,
                    onClick = { onProductsClick.invoke(item) },
                    onFavClicked = { onFavClicked.invoke(item) },
                )
            }
        }
    }
}

private fun handleItemClicked(navController: NavController, productId: Int?) {
    if (productId == null) return
    navController.navigate(Destination.ProductDetail(productId)) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}