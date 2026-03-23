package com.example.myapplication.ui.profile.presentation

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.config.components.layout.CustomToolbar
import com.example.myapplication.config.components.state.EmptyState
import com.example.myapplication.config.navigation.Destination
import com.example.myapplication.config.theme.ThemeMode
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.config.utils.PermissionUtils
import com.example.myapplication.config.utils.SnackbarUtils
import com.example.myapplication.ui.profile.presentation.component.AccountSettingWidget
import com.example.myapplication.ui.profile.presentation.component.LogoutButton
import com.example.myapplication.ui.profile.presentation.component.PreferenceWidget
import com.example.myapplication.ui.profile.presentation.component.TogglePreferenceWidget
import com.example.myapplication.ui.profile.presentation.component.UserInfoWidget
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val navController = LocalParentNavController.current
    val uiState = viewModel.uiState.collectAsState()
    val userProfile = viewModel.userProfile.collectAsState()
    val totalOrders = viewModel.totalOrders.collectAsState()
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { viewModel.changeNotificationEnabled(it) }
    )
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            handleEvents(navController, viewModel)
        }
    }

    Scaffold(topBar = { CustomToolbar(stringResource(R.string.profile), false) }) { innerPadding ->
        when {
            uiState.value.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { CircularProgressIndicator() })

            userProfile.value == null && uiState.value.error.isNotEmpty() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { EmptyState(message = uiState.value.error) })

            else -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.sdp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        start = 10.sdp,
                        end = 10.sdp
                    )
            ) {
                item { Spacer(Modifier) }

                item {
                    UserInfoWidget(
                        user = userProfile.value!!,
                        orders = totalOrders.value,
                        onViewOrdersClicked = {
                            navController?.let {
                                handleItemClicked(it, Destination.OrderListing)
                            }
                        }
                    )
                }

                item {
                    TogglePreferenceWidget(
                        icon = "brightness",
                        title = stringResource(R.string.dark_theme),
                        desc = stringResource(R.string.dark_theme_msg),
                        value = userProfile.value?.themeMode == ThemeMode.Dark.value,
                        onCheckChanged = { viewModel.changeThemeMode(if (it) ThemeMode.Dark else ThemeMode.Light) }
                    )
                }

                item {
                    TogglePreferenceWidget(
                        icon = "notification",
                        title = stringResource(R.string.notification),
                        desc = stringResource(R.string.notification_msg),
                        value = isPermitted(userProfile.value?.notificationEnabled, context),
                        onCheckChanged = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                onNotificationCheckChanged(context, permissionLauncher, viewModel, it)
                            } else {
                                viewModel.changeNotificationEnabled(it)
                            }
                        }
                    )
                }

                item {
                    AccountSettingWidget(
                        onAddressClicked = {
                            navController?.let {
                                handleItemClicked(
                                    navController = it,
                                    route = Destination.AddressListing()
                                )
                            }
                        },
                        onPromoCodeClicked = { },
                        onPaymentMethodClicked = { }
                    )
                }

                item {
                    PreferenceWidget(
                        onHelpClicked = { },
                        onNotificationsClicked = { },
                    )
                }

                item {
                    LogoutButton { viewModel.logout() }
                }

                item { Spacer(Modifier) }
            }
        }
    }
}

private fun isPermitted(permitted: Boolean?, context: Context): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val i = PermissionUtils.isGranted(
            context = context,
            permission = Manifest.permission.POST_NOTIFICATIONS
        )
        return i && permitted == true
    }
    return permitted ?: false
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun onNotificationCheckChanged(
    context: Context,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    viewModel: ProfileViewModel,
    it: Boolean
) {
    val permissionGranted = PermissionUtils.isGranted(
        context = context,
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    if (it && !permissionGranted) {
        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        viewModel.changeNotificationEnabled(it)
    }
}

private fun handleItemClicked(navController: NavController, route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

private suspend fun handleEvents(navController: NavController?, viewModel: ProfileViewModel) {
    viewModel.events.collect { event ->
        when (event) {
            is ProfileEvents.OnLogoutSuccess -> {
                navController?.navigate(Destination.Splash) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }

            is ProfileEvents.OnThemeChangeFailed -> {
                SnackbarUtils.show("Theme change failed with error: ${event.error}")
            }

            is ProfileEvents.OnNotificationChangeFailed -> {
                SnackbarUtils.show("Notification toggle failed with error: ${event.error}")
            }

            is ProfileEvents.OnLogoutFailed -> {
                SnackbarUtils.show("Logout failed with error: ${event.error}")
            }
        }
    }
}
