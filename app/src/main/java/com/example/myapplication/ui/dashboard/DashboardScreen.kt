package com.example.myapplication.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.Destinations
import com.example.myapplication.navigation.bottomNavGraph
import com.example.myapplication.utils.components.BottomNav
import com.example.myapplication.utils.components.Drawer

@Composable
fun DashboardScreen() {
    val navController = rememberNavController()

    Scaffold(topBar = { Drawer(navController) }, bottomBar = { BottomNav(navController) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.BottomGraph.route,
            modifier = Modifier.padding(innerPadding),
            builder = { bottomNavGraph(navController) }
        )
    }
}
