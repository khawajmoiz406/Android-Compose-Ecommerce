package com.example.myapplication.ui.dashboard.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.config.navigation.Destination
import com.example.myapplication.config.navigation.bottomNavGraph
import com.example.myapplication.config.utils.AppCompositionLocals.LocalDrawerStateController
import com.example.myapplication.ui.dashboard.presentation.component.BottomNav
import com.example.myapplication.ui.dashboard.presentation.component.Drawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(drawerContent = { Drawer(navController) }, drawerState = drawerState) {
        Box {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.surface,
                bottomBar = { BottomNav(navController) },
                contentWindowInsets = WindowInsets(0, 0, 0),
            ) { innerPadding ->
                CompositionLocalProvider(LocalDrawerStateController provides drawerState) {
                    NavHost(
                        navController = navController,
                        startDestination = Destination.BottomGraph,
                        modifier = Modifier.padding(innerPadding),
                        builder = { bottomNavGraph() }
                    )
                }
            }
        }
    }
}
