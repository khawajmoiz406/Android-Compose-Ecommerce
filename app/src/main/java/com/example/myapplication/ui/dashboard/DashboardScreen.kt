package com.example.myapplication.ui.dashboard

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.models.helper.SearchController
import com.example.myapplication.navigation.Destinations
import com.example.myapplication.navigation.bottomNavGraph
import com.example.myapplication.config.utils.AppCompositionLocals.LocalDrawerStateController
import com.example.myapplication.config.components.BottomNav
import com.example.myapplication.config.components.Drawer
import com.example.myapplication.config.components.HomeFilters
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    val focusManager = LocalFocusManager.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(drawerContent = { Drawer(navController) }, drawerState = drawerState) {
        Box {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.surface,
                bottomBar = { BottomNav(navController) },
                contentWindowInsets = WindowInsets(0,0,0),
                modifier = Modifier.pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
            ) { innerPadding ->
                CompositionLocalProvider(LocalDrawerStateController provides drawerState) {
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.BottomGraph.route,
                        modifier = Modifier.padding(innerPadding),
                        builder = { bottomNavGraph() }
                    )
                }
            }
        }
    }
}
