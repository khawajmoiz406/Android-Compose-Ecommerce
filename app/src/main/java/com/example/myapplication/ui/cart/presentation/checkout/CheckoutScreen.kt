package com.example.myapplication.ui.cart.presentation.checkout

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.components.layout.CustomToolbar
import com.example.myapplication.config.navigation.Destination
import com.example.myapplication.config.theme.Green
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.config.utils.ComposableUtils.topShadowScope
import com.example.myapplication.config.utils.SnackbarUtils
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.PromoCode
import com.example.myapplication.ui.cart.presentation.cart.component.OrderSummaryWidget
import com.example.myapplication.ui.cart.presentation.checkout.component.AddressWidget
import com.example.myapplication.ui.cart.presentation.checkout.component.PaymentMethodWidget
import com.example.myapplication.ui.cart.presentation.checkout.component.ShippingMethodWidget
import com.example.myapplication.ui.cart.presentation.checkout.component.TermsAndConditionWidget
import com.example.myapplication.ui.cart.presentation.checkout.component.UserInfoWidget
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(cart: Cart, promoCode: PromoCode?, viewModel: CheckoutViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val parentNavController = LocalParentNavController.current
    val checkoutRequest = viewModel.checkoutRequest.collectAsState()
    val confirmVisibility = remember { mutableStateOf(true) }
    val address = parentNavController?.currentBackStackEntry?.savedStateHandle
        ?.getStateFlow<String?>("selected_address_json", null)
        ?.collectAsState()

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            handleEvents(parentNavController, viewModel)
        }
    }

    LaunchedEffect(address?.value) {
        address?.value?.let {
            viewModel.onShippingAddressChanged(Json.decodeFromString(it))
            parentNavController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<String?>("selected_address_json")
        }
    }

    LaunchedEffect(checkoutRequest.value) {
        if (checkoutRequest.value == null) viewModel.initCheckout(cart, promoCode)
    }

    if (checkoutRequest.value == null) return Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = { CircularProgressIndicator() }
    )

    Scaffold(topBar = { CustomToolbar(stringResource(R.string.checkout)) }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.sdp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.sdp)
                    .nestedScroll(
                        connection = remember {
                            object : NestedScrollConnection {
                                override fun onPreScroll(
                                    available: Offset,
                                    source: NestedScrollSource
                                ): Offset {
                                    if (available.y < 0) confirmVisibility.value = false
                                    else if (available.y > 0) confirmVisibility.value = true
                                    return Offset.Zero
                                }
                            }
                        }
                    )
            ) {
                item { Spacer(Modifier) }

                item {
                    UserInfoWidget(uiState.value.phoneNumberField) {
                        viewModel.onPhoneNumberChanged(it)
                    }
                }

                item {
                    AddressWidget(
                        selected = checkoutRequest.value!!.shippingAddress,
                        onChangeAddress = {
                            parentNavController?.let {
                                handleItemClicked(it, Destination.AddressListing(true))
                            }
                        },
                    )
                }

                item {
                    ShippingMethodWidget(
                        selected = checkoutRequest.value!!.shippingMethod,
                        shippingMethods = viewModel.shippingMethods,
                        onShippingChanged = { viewModel.shippingChanged(it) }
                    )
                }

                item {
                    PaymentMethodWidget(
                        selected = checkoutRequest.value!!.paymentMethod,
                        onPaymentMethodChanged = { viewModel.paymentMethodChanged(it) }
                    )
                }

                item {
                    OrderSummaryWidget(
                        cart = cart,
                        promoCode = promoCode,
                        shipping = checkoutRequest.value!!.shippingMethod,
                    )
                }

                item {
                    TermsAndConditionWidget(uiState.value.termsAccepted) {
                        viewModel.toggleTerms()
                    }
                }

                item { Spacer(Modifier.height(10.sdp)) }
            }

            AnimatedVisibility(
                visible = confirmVisibility.value,
                modifier = Modifier.align(Alignment.BottomCenter),
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                content = {
                    checkoutRequest.value?.let {
                        TotalContent(
                            uiState = uiState.value,
                            onPlaceOrderClicked = { viewModel.checkout() },
                            total = it.cart.getTotal(
                                it.promoCode?.discountPrice,
                                it.shippingMethod.price
                            ),
                        )
                    }
                }
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun BoxScope.TotalContent(
    total: Double,
    uiState: CheckoutUiState,
    onPlaceOrderClicked: () -> Unit
) {
    val enabled =
        !uiState.isLoading && uiState.termsAccepted && uiState.phoneNumberField.value.isNotEmpty()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.sdp),
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .drawBehind { topShadowScope(topStartCorner = 10.dp, topEndCorner = 10.dp) }
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            )
            .padding(vertical = 8.sdp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.sdp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.total_to_pay),
                    fontSize = 11.ssp,
                    color = MaterialTheme.colorScheme.outline,
                )

                Spacer(Modifier.height(1.sdp))

                Text(
                    text = "$${String.format("%.2f", total)}",
                    fontSize = 17.ssp,
                    softWrap = false,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = Green.copy(alpha = 0.1f), shape = RoundedCornerShape(20.sdp))
                    .padding(horizontal = 10.sdp, vertical = 5.sdp)
            ) {
                SvgImage(
                    asset = "lock",
                    color = Green,
                    modifier = Modifier.size(15.sdp, 15.sdp)
                )

                Spacer(Modifier.width(5.sdp))

                Text(
                    text = stringResource(R.string.secure),
                    fontSize = 11.ssp,
                    fontWeight = FontWeight.Medium,
                    color = Green
                )
            }
        }

        HorizontalDivider()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(40.sdp)
                .padding(horizontal = 10.sdp)
                .fillMaxWidth()
                .clickable(onClick = { if (enabled) onPlaceOrderClicked.invoke() })
                .background(
                    color = if (!enabled) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.sdp)
                )
        ) {

            when (uiState.isLoading) {
                true -> CircularProgressIndicator(
                    modifier = Modifier.size(20.sdp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                false -> Text(
                    text = stringResource(R.string.place_order),
                    fontSize = 14.ssp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

private fun handleItemClicked(navController: NavController, route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

private suspend fun handleEvents(navController: NavController?, viewModel: CheckoutViewModel) {
    viewModel.events.collect { event ->
        when (event) {
            is CheckoutEvents.OnOrderPlaced -> {
                navController?.navigate(Destination.DrawerGraph) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
                SnackbarUtils.show(navController?.context?.getString(R.string.order_placed_msg) ?: "")
            }

            is CheckoutEvents.OnError -> {
                SnackbarUtils.show("Order placing error: ${event.error}")
            }
        }
    }
}