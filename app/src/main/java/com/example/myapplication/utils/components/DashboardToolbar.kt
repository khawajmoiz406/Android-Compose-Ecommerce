package com.example.myapplication.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardToolbar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            Icon(
                Icons.Outlined.Notifications,
                contentDescription = null,
                modifier = Modifier.clickable { },
            )
        },
        navigationIcon = {
            Icon(
                Icons.Outlined.Menu,
                contentDescription = null,
                modifier = Modifier.clickable { scope.launch { drawerState.open() } },
            )
        }
    )
}