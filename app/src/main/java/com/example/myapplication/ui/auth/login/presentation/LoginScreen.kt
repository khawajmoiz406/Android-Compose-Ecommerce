package com.example.myapplication.ui.auth.login.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.myapplication.R
import com.example.myapplication.di.createApiService
import com.example.myapplication.di.createOkHttpClient
import com.example.myapplication.di.createRetrofit
import com.example.myapplication.ui.auth.login.data.LoginRemoteRepoImpl
import com.example.myapplication.ui.auth.login.domain.LoginUseCase
import com.example.myapplication.utils.SnackbarUtils
import com.example.myapplication.utils.theme.MyApplicationTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) { handleEvents(viewModel) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { viewModel.login() },
            modifier = Modifier.defaultMinSize(minWidth = 150.dp)
        ) {
            when (uiState.value.isLoading) {
                false -> Text(stringResource(R.string.login), fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimary)
                true -> CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }

    if (uiState.value.error.isNotEmpty()) AlertDialog(
        onDismissRequest = { viewModel.updateUiState(uiState.value.copy(error = "")) },
        title = { Text("Error") },
        text = { Text(uiState.value.error) },
        confirmButton = {
            Button(onClick = { viewModel.updateUiState(uiState.value.copy(error = "")) }) { Text(stringResource(R.string.ok)) }
        }
    )
}

private suspend fun handleEvents(viewModel: LoginViewModel) {
    viewModel.events.collect { event ->
        when (event) {
            is LoginEvents.OnLoginFailed -> {
                SnackbarUtils.show("Login has been failed with error: ${event.error}")
            }

            is LoginEvents.OnLoginSuccess -> {
                SnackbarUtils.show("Login has been successful. Welcome ${event.user.name}")
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            innerPadding.calculateTopPadding()
            LoginScreen(LoginViewModel(LoginUseCase(LoginRemoteRepoImpl(createApiService(createRetrofit(createOkHttpClient()))))))
        }
    }
}
