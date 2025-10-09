package com.example.myapplication.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.Destinations
import com.example.myapplication.navigation.bottomNavGraph
import com.example.myapplication.utils.components.BottomNav
import com.example.myapplication.utils.components.DashboardToolbar
import com.example.myapplication.utils.components.Drawer

@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(drawerContent = { Drawer(navController) }, drawerState = drawerState) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            topBar = { DashboardToolbar(drawerState) },
            bottomBar = { BottomNav(navController) },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Destinations.BottomGraph.route,
                modifier = Modifier.padding(innerPadding),
                builder = { bottomNavGraph() }
            )
        }
    }
}
