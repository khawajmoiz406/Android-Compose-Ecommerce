package com.ecommerce.shoppy.ui.dashboard.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.navigation.Destination
import com.ecommerce.shoppy.config.navigation.bottomNavGraph
import com.ecommerce.shoppy.config.utils.AppCompositionLocals.LocalDrawerStateController
import com.ecommerce.shoppy.ui.dashboard.presentation.component.BottomNav
import com.ecommerce.shoppy.ui.dashboard.presentation.component.Drawer
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    CompositionLocalProvider(LocalDrawerStateController provides drawerState) {
        ModalNavigationDrawer(drawerContent = { Drawer() }, drawerState = drawerState) {
            Box {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surface,
                    bottomBar = { BottomNav(navController) },
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                ) { _ ->
                    Column {
                        NavHost(
                            navController = navController,
                            startDestination = Destination.BottomGraph,
                            modifier = Modifier.weight(1f),
                            builder = { bottomNavGraph() }
                        )

                        Spacer(
                            Modifier.height(
                                (integerResource(R.integer.bottom_nav_height).sdp) +
                                        (WindowInsets.navigationBars.asPaddingValues()
                                            .calculateBottomPadding())
                            )
                        )
                    }
                }
            }
        }
    }
}
