package com.example.myapplication.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardToolbar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.sdp)
                    .align(Alignment.CenterVertically)
                    .clickable { },
            ) {
                SvgImage(
                    asset = "notification",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(18.sdp)
                )
            }
        },
        navigationIcon = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(30.sdp)
                    .clickable { scope.launch { drawerState.open() } },
            ) {
                SvgImage(
                    asset = "menu",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.sdp)
                )
            }
        }
    )
}