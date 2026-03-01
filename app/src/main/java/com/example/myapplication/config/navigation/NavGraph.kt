package com.example.myapplication.config.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Destination.LandingGraph,
        builder = {
            landingGraph()
            authGraph()
            drawerGraph()
        }
    )
}