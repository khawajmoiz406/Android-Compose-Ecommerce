package com.example.myapplication.ui.cart.presentation.cart

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.config.components.layout.CustomToolbar
import com.example.myapplication.config.components.state.EmptyState
import com.example.myapplication.config.navigation.Destinations
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.config.utils.ComposableUtils.topShadowScope
import com.example.myapplication.core.model.Cart
import com.example.myapplication.core.model.PromoCode
import com.example.myapplication.ui.cart.presentation.cart.component.DeliveryEstimateWidget
import com.example.myapplication.ui.cart.presentation.cart.component.ItemCart
import com.example.myapplication.ui.cart.presentation.cart.component.OrderSummaryWidget
import com.example.myapplication.ui.cart.presentation.cart.component.PromoCodeWidget
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(viewModel: CartViewModel = koinViewModel()) {
    val cartData = viewModel.cart.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val promoCode = viewModel.promo.collectAsState()
    val parentNavController = LocalParentNavController.current
    val checkoutVisibility = remember { mutableStateOf(true) }

    if (cartData.value == null && uiState.value.isLoading) return Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = { CircularProgressIndicator() }
    )

    Scaffold(topBar = { CustomToolbar(stringResource(R.string.cart)) }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            when {
                cartData.value?.items.isNullOrEmpty() -> EmptyState(
                    message = stringResource(R.string.cart_is_empty),
                    imageAsset = "empty_cart",
                )

                else -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(15.sdp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 8.sdp)
                        .nestedScroll(
                            connection = remember {
                                object : NestedScrollConnection {
                                    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                                        if (available.y < 0) checkoutVisibility.value = false
                                        else if (available.y > 0) checkoutVisibility.value = true
                                        return Offset.Zero
                                    }
                                }
                            }
                        )
                ) {
                    item { Spacer(Modifier) }

                    items(key = { "cart_item_$it" }, count = cartData.value!!.items.size) { index ->
                        val item = cartData.value!!.items[index]
                        ItemCart(
                            cartItem = item,
                            index = index,
                            itemCount = cartData.value!!.items.size,
                            onRemoveClicked = { viewModel.removeCartItem(item.product.id!!) },
                            onFavClicked = { viewModel.toggleFavourite(item.product.id!!) },
                            onAddClicked = {
                                val newValue = item.cartItem.quantity + 1
                                if (newValue < (item.product.stock ?: 0))
                                    viewModel.updateQuantity(item.product.id!!, newValue)
                            },
                            onMinusClicked = {
                                val newValue = item.cartItem.quantity - 1
                                if (newValue > (item.product.minimumOrderQuantity ?: 0))
                                    viewModel.updateQuantity(item.product.id!!, newValue)
                            },
                        )
                    }

                    item(key = "key_delivery_estimate") {
                        DeliveryEstimateWidget()
                    }

                    item(key = "key_promo_code") {
                        PromoCodeWidget(
                            isLoading = uiState.value.promoLoading,
                            initialValue = promoCode.value,
                            onApplyClicked = { viewModel.checkPromoCode(it) },
                            onCancelClicked = { viewModel.removePromo() }
                        )
                    }

                    item(key = "key_order_summary") {
                        OrderSummaryWidget(cart = cartData.value!!, promoCode = promoCode.value)
                    }

                    item {
                        Spacer(Modifier.height(10.sdp))
                    }
                }
            }

            if (!cartData.value?.items.isNullOrEmpty())
                AnimatedVisibility(
                    visible = checkoutVisibility.value,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    content = {
                        TotalContent(cartData.value!!, promoCode.value, uiState.value) {
                            parentNavController?.apply {
                                currentBackStackEntry?.savedStateHandle?.let {
                                    it["cart"] = cartData.value!!
                                    it["promoCode"] = promoCode.value
                                }
                                navigate(Destinations.Checkout.route) {
                                    popUpTo(parentNavController.graph.startDestinationId) { saveState = true }
                                    restoreState = true
                                }
                            }
                        }
                    }
                )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun BoxScope.TotalContent(
    cart: Cart,
    promoCode: PromoCode?,
    uiState: CartUiState,
    onCheckoutClicked: () -> Unit
) {
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
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = stringResource(R.string.total_heading),
                    fontSize = 17.ssp,
                    softWrap = false,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.height(18.sdp)
                )

                Spacer(Modifier.width(2.sdp))

                Text(
                    text = stringResource(R.string.total_msg),
                    fontSize = 11.ssp,
                    softWrap = false,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "$${String.format("%.2f", cart.getTotal(promoCode?.discountPrice))}",
                    fontSize = 15.ssp,
                    softWrap = false,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(Modifier.height(1.sdp))

                Text(
                    text = "$${String.format("%.2f", cart.getSubTotalWithoutDiscount())}",
                    fontSize = 11.ssp,
                    textAlign = TextAlign.End,
                    textDecoration = TextDecoration.LineThrough,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
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
                .clickable(onClick = { if (!uiState.isLoading) onCheckoutClicked.invoke() })
                .background(
                    color = if (uiState.isLoading) MaterialTheme.colorScheme.surfaceDim else MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.sdp)
                )
        ) {

            when (uiState.isLoading) {
                true -> CircularProgressIndicator(
                    modifier = Modifier.size(20.sdp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                false -> Text(
                    text = stringResource(R.string.proceed_to_checkout),
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