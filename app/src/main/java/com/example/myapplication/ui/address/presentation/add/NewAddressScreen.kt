package com.example.myapplication.ui.address.presentation.add

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.config.components.input.AppRadioButton
import com.example.myapplication.config.components.layout.CustomToolbar
import com.example.myapplication.config.components.state.EmptyState
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.config.utils.SnackbarUtils
import com.example.myapplication.ui.address.presentation.add.component.AddressForm
import com.example.myapplication.ui.address.presentation.add.component.AddressTypeWidget
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAddressScreen(addressId: Int = 0, viewModel: NewAddressViewModel = koinViewModel()) {
    val isUpdate: Boolean = addressId != 0
    val lifecycleOwner = LocalLifecycleOwner.current
    val navController = LocalParentNavController.current
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            handleEvents(navController, viewModel)
        }
    }

    LaunchedEffect(addressId) {
        if (addressId != 0 && viewModel.address == null) viewModel.getUserAddress(addressId)
    }

    Scaffold(topBar = {
        CustomToolbar(
            stringResource(if (isUpdate) R.string.update_address else R.string.new_address),
            true
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = 10.sdp,
                end = 10.sdp
            )
        ) {
            when {
                uiState.value.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = { CircularProgressIndicator() }
                    )
                }

                addressId != 0 && viewModel.address == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = { EmptyState(message = uiState.value.error) }
                    )
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(15.sdp),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        item { Spacer(Modifier) }

                        item {
                            AddressTypeWidget(uiState.value.addressType) {
                                viewModel.onAddressTypeChange(it)
                            }
                        }

                        item {
                            AddressForm(uiState.value) { str, fieldUpdater ->
                                viewModel.onFieldChange(value = str, fieldUpdater = fieldUpdater)
                            }
                        }

                        item {
                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.surfaceDim,
                                        RoundedCornerShape(15.sdp)
                                    )
                                    .padding(horizontal = 10.sdp, vertical = 5.sdp)
                            ) {
                                AppRadioButton(
                                    selected = uiState.value.defaultAddress,
                                    heading = stringResource(R.string.default_filters),
                                    onClick = { viewModel.toggleDefaultAddress() },
                                )
                            }
                        }

                        item { Spacer(Modifier) }
                    }

                    SaveButton(uiState.value) {
                        if (!viewModel.validateForm()) return@SaveButton
                        if (isUpdate) viewModel.updateAddress() else viewModel.addNewAddress()
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxScope.SaveButton(uiState: NewAddressUiState, onSaveClicked: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.sdp)
            .align(Alignment.BottomCenter)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.sdp))
            .clickable(onClick = { onSaveClicked.invoke() })
            .padding(horizontal = 10.sdp)
    ) {

        when (uiState.isSaving) {
            true -> CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )

            false -> {
                Text(
                    text = stringResource(R.string.save_address),
                    fontSize = 13.ssp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.surface,
                )
            }
        }
    }
}

private suspend fun handleEvents(navController: NavController?, viewModel: NewAddressViewModel) {
    viewModel.events.collect { event ->
        when (event) {
            is NewAddressEvents.OnAddressAdded -> {
                navController?.popBackStack()
                SnackbarUtils.show(navController?.context?.getString(R.string.address_added_msg) ?: "")
            }

            is NewAddressEvents.OnAddressUpdated -> {
                navController?.popBackStack()
                SnackbarUtils.show(navController?.context?.getString(R.string.address_updated_msg) ?: "")
            }

            is NewAddressEvents.OnError -> {
                SnackbarUtils.show("Notification toggle failed with error: ${event.error}")
            }
        }
    }
}
