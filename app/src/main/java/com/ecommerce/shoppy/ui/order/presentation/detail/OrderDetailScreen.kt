package com.ecommerce.shoppy.ui.order.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.layout.CustomToolbar
import com.ecommerce.shoppy.config.components.layout.SwipeRefresh
import com.ecommerce.shoppy.config.components.state.EmptyState
import com.ecommerce.shoppy.config.utils.AppUtils
import com.ecommerce.shoppy.config.utils.SnackbarUtils
import com.ecommerce.shoppy.ui.order.presentation.detail.component.DeliveryAddress
import com.ecommerce.shoppy.ui.order.presentation.detail.component.OrderTimeline
import com.ecommerce.shoppy.ui.order.presentation.detail.component.PaymentMethodWidget
import com.ecommerce.shoppy.ui.order.presentation.detail.component.PaymentSummary
import com.ecommerce.shoppy.ui.order.presentation.detail.component.TrackingNumber
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(orderId: Int, viewModel: OrderDetailViewModel = koinViewModel()) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val orders = viewModel.order.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(orderId) {
        viewModel.getOrderDetail(orderId)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { CustomToolbar(stringResource(R.string.order_detail)) },
    ) { innerPadding ->
        when {
            uiState.value.isLoading && orders.value == null -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { CircularProgressIndicator() }
            )

            else -> SwipeRefresh(
                scrollBehavior = scrollBehavior,
                isRefreshing = uiState.value.isRefreshing,
                onRefresh = { viewModel.getOrderDetail(orderId, true) },
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 10.sdp,
                        end = 10.sdp
                    )
                    .fillMaxSize(),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(15.sdp)
                ) {
                    when {
                        orders.value == null || uiState.value.error.isNotEmpty() -> item {
                            Box(Modifier.fillParentMaxHeight()) {
                                EmptyState(message = uiState.value.error, scrollable = false)
                            }
                        }

                        else -> orders.value!!.let {
                            item { Spacer(Modifier) }

                            item { OrderTimeline(it) }

                            item {
                                TrackingNumber(it.trackingNumber) {
                                    AppUtils.copyToClipboard(context, it.trackingNumber)
                                    SnackbarUtils.show(context.getString(R.string.tacking_number_copied))
                                }
                            }

                            item { PaymentMethodWidget(it.paymentMethod) }

                            item { DeliveryAddress(it.shippingAddress, it.phoneNumber) }

                            item { PaymentSummary(it) }

                            item { Spacer(Modifier) }
                        }
                    }
                }
            }
        }
    }
}