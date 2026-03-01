package com.example.myapplication.ui.address.presentation.add.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.config.components.input.AppTextField
import com.example.myapplication.config.components.state.FieldState
import com.example.myapplication.ui.address.presentation.add.NewAddressUiState
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AddressForm(
    uiState: NewAddressUiState,
    onFieldChange: (value: String, fieldUpdater: NewAddressUiState.(FieldState) -> NewAddressUiState) -> Unit
) {
    val focusManager = LocalFocusManager.current

    DisposableEffect(Unit) {
        onDispose { focusManager.clearFocus() }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.sdp),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceDim, RoundedCornerShape(15.sdp))
            .padding(10.sdp)
    ) {
        AppTextField(
            value = uiState.name.value,
            height = 40.sdp,
            borderWidth = 1.sdp,
            imeAction = ImeAction.Next,
            onValueChange = { onFieldChange.invoke(it) { state -> copy(name = state) } },
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth(),
            borderColor = MaterialTheme.colorScheme.outline,
            placeholder = stringResource(R.string.full_name),
            containerColor = MaterialTheme.colorScheme.surface,
            onImeActionPerformed = { focusManager.moveFocus(FocusDirection.Next) },
            error = uiState.name.error?.let { stringResource(it) },
        )

        AppTextField(
            value = uiState.streetAddress.value,
            height = 40.sdp,
            borderWidth = 1.sdp,
            imeAction = ImeAction.Next,
            onValueChange = { onFieldChange.invoke(it) { state -> copy(streetAddress = state) } },
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth(),
            borderColor = MaterialTheme.colorScheme.outline,
            placeholder = stringResource(R.string.street_address),
            containerColor = MaterialTheme.colorScheme.surface,
            onImeActionPerformed = { focusManager.moveFocus(FocusDirection.Next) },
            error = uiState.streetAddress.error?.let { stringResource(it) },
        )

        AppTextField(
            value = uiState.houseNo.value,
            height = 40.sdp,
            borderWidth = 1.sdp,
            imeAction = ImeAction.Next,
            onValueChange = { onFieldChange.invoke(it) { state -> copy(houseNo = state) } },
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth(),
            borderColor = MaterialTheme.colorScheme.outline,
            placeholder = stringResource(R.string.house_no_suit_apt),
            containerColor = MaterialTheme.colorScheme.surface,
            onImeActionPerformed = { focusManager.moveFocus(FocusDirection.Next) },
        )

        Row {
            AppTextField(
                value = uiState.city.value,
                height = 40.sdp,
                borderWidth = 1.sdp,
                imeAction = ImeAction.Next,
                onValueChange = { onFieldChange.invoke(it) { state -> copy(city = state) } },
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f),
                borderColor = MaterialTheme.colorScheme.outline,
                placeholder = stringResource(R.string.city),
                containerColor = MaterialTheme.colorScheme.surface,
                onImeActionPerformed = { focusManager.moveFocus(FocusDirection.Next) },
                error = uiState.city.error?.let { stringResource(it) },
            )

            Spacer(Modifier.width(10.sdp))

            AppTextField(
                value = uiState.state.value,
                height = 40.sdp,
                borderWidth = 1.sdp,
                imeAction = ImeAction.Next,
                onValueChange = { onFieldChange.invoke(it) { state -> copy(state = state) } },
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f),
                borderColor = MaterialTheme.colorScheme.outline,
                placeholder = stringResource(R.string.state),
                containerColor = MaterialTheme.colorScheme.surface,
                onImeActionPerformed = { focusManager.moveFocus(FocusDirection.Next) },
                error = uiState.state.error?.let { stringResource(it) },
            )
        }

        Row {
            AppTextField(
                value = uiState.zipcode.value,
                height = 40.sdp,
                borderWidth = 1.sdp,
                imeAction = ImeAction.Next,
                onValueChange = { onFieldChange.invoke(it) { state -> copy(zipcode = state) } },
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f),
                borderColor = MaterialTheme.colorScheme.outline,
                placeholder = stringResource(R.string.postal_code),
                containerColor = MaterialTheme.colorScheme.surface,
                onImeActionPerformed = { focusManager.moveFocus(FocusDirection.Next) },
                error = uiState.zipcode.error?.let { stringResource(it) },
            )

            Spacer(Modifier.width(10.sdp))

            AppTextField(
                value = uiState.country.value,
                height = 40.sdp,
                borderWidth = 1.sdp,
                onValueChange = { onFieldChange.invoke(it) { state -> copy(country = state) } },
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f),
                borderColor = MaterialTheme.colorScheme.outline,
                placeholder = stringResource(R.string.country),
                containerColor = MaterialTheme.colorScheme.surface,
                onImeActionPerformed = { focusManager.clearFocus() },
                error = uiState.country.error?.let { stringResource(it) },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAddressForm() {
    AddressForm(NewAddressUiState()) { _, _ -> }
}