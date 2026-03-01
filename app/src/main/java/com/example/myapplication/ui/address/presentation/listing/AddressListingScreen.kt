package com.example.myapplication.ui.address.presentation.listing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.config.components.layout.CustomToolbar
import com.example.myapplication.config.components.layout.SwipeRefresh
import com.example.myapplication.config.components.state.EmptyState
import com.example.myapplication.config.navigation.Destination
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.config.utils.ComposableUtils.dottedBorder
import com.example.myapplication.config.utils.SnackbarUtils
import com.example.myapplication.ui.address.presentation.listing.component.ItemAddress
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressListingScreen(
    selectionMode: Boolean = false,
    viewModel: AddressListingViewModel = koinViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val parentNavController = LocalParentNavController.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val addresses = viewModel.addresses.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            handleEvents(parentNavController, viewModel)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { CustomToolbar(stringResource(R.string.saved_address), true) },
    ) { innerPadding ->
        SwipeRefresh(
            scrollBehavior = scrollBehavior,
            isRefreshing = uiState.value.isRefreshing,
            onRefresh = { viewModel.getUserAddresses(true) },
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 10.sdp,
                    end = 10.sdp
                )
                .fillMaxSize(),
        ) {
            Box(modifier = Modifier.fillMaxHeight()) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {

                    item { Spacer(Modifier.height(10.sdp)) }

                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .dottedBorder(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(10.sdp)
                                )
                                .fillMaxWidth()
                                .height(40.sdp)
                                .clip(RoundedCornerShape(10.sdp))
                                .clickable {
                                    parentNavController?.let {
                                        handleItemClicked(
                                            it,
                                            Destination.NewAddress(),
                                        )
                                    }
                                },
                        ) {
                            Text(
                                text = stringResource(R.string.add_new_address),
                                fontSize = 13.ssp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }

                    item { Spacer(Modifier.height(10.sdp)) }

                    when {
                        uiState.value.isLoading -> item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center,
                                content = { CircularProgressIndicator() }
                            )
                        }

                        addresses.value.isEmpty() && uiState.value.error.isNotEmpty() -> item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center,
                                content = { EmptyState(message = uiState.value.error) }
                            )
                        }

                        addresses.value.isEmpty() -> item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center,
                                content = {
                                    EmptyState(
                                        message = stringResource(R.string.no_address_found),
                                        imageAsset = "no_address_found"
                                    )
                                }
                            )
                        }

                        else -> items(
                            count = addresses.value.size,
                            key = { addresses.value[it].id },
                            itemContent = { index ->
                                val address = addresses.value[index]
                                ItemAddress(
                                    address = address,
                                    selectionMode = selectionMode,
                                    selected = uiState.value.selectedAddress == index,
                                    onDeleteClicked = { viewModel.deleteAddress(address.id) },
                                    onAddressClicked = { viewModel.onSelectionChanged(index) },
                                    onEditClicked = {
                                        parentNavController?.let {
                                            handleItemClicked(
                                                it,
                                                Destination.NewAddress(address.id),
                                            )
                                        }
                                    },
                                )

                                Spacer(Modifier.height(10.sdp))
                            }
                        )
                    }
                }

                if (selectionMode) UseThisAddressButton {}
            }
        }
    }
}


@Composable
private fun BoxScope.UseThisAddressButton(onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.sdp)
            .align(Alignment.BottomCenter)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.sdp))
            .clickable(onClick = { onClick.invoke() })
            .padding(horizontal = 10.sdp)
    ) {

        Text(
            text = stringResource(R.string.use_this_address),
            fontSize = 13.ssp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.surface,
        )
    }
}

private fun handleItemClicked(navController: NavController, route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

private suspend fun handleEvents(navController: NavController?, viewModel: AddressListingViewModel) {
    viewModel.events.collect { event ->
        when (event) {
            is AddressListingEvents.OnAddressDeleted -> {
                SnackbarUtils.show(navController?.context?.getString(R.string.address_delete_msg) ?: "")
            }

            is AddressListingEvents.OnError -> {
                SnackbarUtils.show("Address deletion failed with error: ${event.error}")
            }
        }
    }
}
