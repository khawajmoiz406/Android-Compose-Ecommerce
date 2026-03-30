package com.ecommerce.shoppy.ui.auth.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.image.SvgImage
import com.ecommerce.shoppy.config.navigation.Destination
import com.ecommerce.shoppy.config.utils.AppCompositionLocals.LocalParentNavController
import com.ecommerce.shoppy.config.utils.SnackbarUtils
import com.ecommerce.shoppy.ui.auth.presentation.login.component.AuthHeader
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val navController = LocalParentNavController.current
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(lifecycleOwner) { navController?.let { handleEvents(it, viewModel) } }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        AuthHeader(
            heading = stringResource(R.string.welcome_back),
            subHeading = stringResource(R.string.login_caption)
        )

        Spacer(Modifier.height(15.sdp))

        Column(Modifier.padding(horizontal = 15.sdp)) {
            OutlinedTextField(
                singleLine = true,
                value = uiState.value.email,
                shape = RoundedCornerShape(10.dp),
                label = { Text(text = stringResource(R.string.username)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onValueChange = { viewModel.updateUiState(uiState.value.copy(email = it)) },
                placeholder = { Text(text = stringResource(R.string.username)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                singleLine = true,
                value = uiState.value.password,
                shape = RoundedCornerShape(10.dp),
                label = { Text(text = stringResource(R.string.password)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                onValueChange = { viewModel.updateUiState(uiState.value.copy(password = it)) },
                placeholder = { Text(text = stringResource(R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        SvgImage(
                            asset = if (passwordVisible) "eye_closed" else "eye_open",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = { if (!uiState.value.isLoading) viewModel.login() },
                shape = RoundedCornerShape(8.sdp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.sdp),
            ) {
                when (uiState.value.isLoading) {
                    false -> Text(
                        stringResource(R.string.login),
                        fontSize = 13.ssp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    true -> CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }

    if (uiState.value.error.isNotEmpty()) AlertDialog(
        onDismissRequest = { viewModel.updateUiState(uiState.value.copy(error = "")) },
        title = { Text("Error") },
        text = { Text(uiState.value.error) },
        confirmButton = {
            Button(onClick = { viewModel.updateUiState(uiState.value.copy(error = "")) }) {
                Text(stringResource(R.string.ok))
            }
        }
    )
}

private suspend fun handleEvents(navController: NavController, viewModel: LoginViewModel) {
    viewModel.events.collect { event ->
        when (event) {
            is LoginEvents.OnLoginFailed -> {
                SnackbarUtils.show("Login has been failed with error: ${event.error}")
            }

            is LoginEvents.OnLoginSuccess -> {
                navController.navigate(Destination.DrawerGraph) {
                    popUpTo(Destination.Login) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}
