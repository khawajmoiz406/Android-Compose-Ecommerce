package com.example.myapplication.ui.auth.login.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.di.createApiService
import com.example.myapplication.di.createOkHttpClient
import com.example.myapplication.di.createRetrofit
import com.example.myapplication.navigation.Destinations
import com.example.myapplication.ui.auth.login.data.LoginRemoteRepoImpl
import com.example.myapplication.ui.auth.login.domain.LoginUseCase
import com.example.myapplication.utils.SnackbarUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(lifecycleOwner) { handleEvents(navController, viewModel) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {

        OutlinedTextField(
            singleLine = true,
            value = uiState.value.email,
            shape = RoundedCornerShape(10.dp),
            label = { Text(text = stringResource(R.string.username)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = { viewModel.updateUiState(uiState.value.copy(email = it)) },
            placeholder = { Text(text = stringResource(R.string.username)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            singleLine = true,
            value = uiState.value.password,
            shape = RoundedCornerShape(10.dp),
            label = { Text(text = stringResource(R.string.password)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onValueChange = { viewModel.updateUiState(uiState.value.copy(email = it)) },
            placeholder = { Text(text = stringResource(R.string.password)) },
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
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { viewModel.login() }, modifier = Modifier.defaultMinSize(minWidth = 150.dp)) {
            when (uiState.value.isLoading) {
                false -> Text(
                    stringResource(R.string.login),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                true -> CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
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
                SnackbarUtils.show("Login has been successful. Welcome ${event.user.username}")
                navController.navigate(Destinations.DrawerGraph.route) {
                    popUpTo(Destinations.Login.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val context = LocalContext.current
    val navController = rememberNavController()
    LoginScreen(
        navController,
        LoginViewModel(
            LoginUseCase(
                LoginRemoteRepoImpl(
                    context,
                    createApiService(createRetrofit(createOkHttpClient()))
                )
            )
        )
    )
}
