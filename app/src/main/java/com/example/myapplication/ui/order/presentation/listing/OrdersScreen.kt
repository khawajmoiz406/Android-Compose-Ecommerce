package com.example.myapplication.ui.order.presentation.listing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.myapplication.config.components.layout.SwipeRefresh
import com.example.myapplication.config.components.state.EmptyState
import com.example.myapplication.config.navigation.Destination
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.ui.order.presentation.listing.component.ItemOrder
import com.example.myapplication.ui.order.presentation.listing.component.ItemOrderStatus
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(viewModel: OrdersViewModel = koinViewModel()) {
    val parentNavController = LocalParentNavController.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val orders = viewModel.filteredOrders.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { CustomToolbar(stringResource(R.string.my_orders)) },
    ) { innerPadding ->
        when {
            uiState.value.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { CircularProgressIndicator() }
            )

            else -> SwipeRefresh(
                scrollBehavior = scrollBehavior,
                isRefreshing = uiState.value.isRefreshing,
                onRefresh = { viewModel.getUserOrders(true) },
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    item { Spacer(Modifier.height(15.sdp)) }

                    item {
                        LazyRow {
                            itemsIndexed(viewModel.orderStatus) { index, item ->
                                ItemOrderStatus(
                                    status = item,
                                    index = index,
                                    selected = uiState.value.selectedOrderStatus == item.first,
                                    onItemClick = { viewModel.onOrderStatusChanged(it) }
                                )
                            }
                        }
                    }

                    when {
                        orders.value.isEmpty() && uiState.value.error.isNotEmpty() -> item {
                            Box(Modifier.fillParentMaxHeight(0.75f)) {
                                EmptyState(message = uiState.value.error, scrollable = false)
                            }
                        }

                        orders.value.isEmpty() -> item {
                            Box(Modifier.fillParentMaxHeight(0.75f)) {
                                EmptyState(
                                    message = stringResource(R.string.no_order_found),
                                    imageAsset = "no_order_found",
                                    scrollable = false
                                )
                            }
                        }

                        else -> items(
                            count = orders.value.size,
                            key = { orders.value[it].id }) { index ->
                            val item = orders.value[index]
                            ItemOrder(
                                index = index,
                                order = item,
                                onClick = {
                                    parentNavController?.let {
                                        handleItemClicked(it, item.id)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun handleItemClicked(navController: NavController, orderId: Int) {
    navController.navigate(Destination.OrderDetail(orderId)) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}