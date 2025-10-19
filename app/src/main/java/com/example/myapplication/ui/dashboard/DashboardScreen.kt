package com.example.myapplication.ui.dashboard

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.models.helper.SearchController
import com.example.myapplication.navigation.Destinations
import com.example.myapplication.navigation.bottomNavGraph
import com.example.myapplication.utils.AppCompositionLocals.LocalSearchController
import com.example.myapplication.utils.AppCompositionLocals.LocalTopAppBarScrollBehavior
import com.example.myapplication.utils.Constants.BOTTOM_NAV_ITEMS
import com.example.myapplication.utils.components.BottomNav
import com.example.myapplication.utils.components.DashboardToolbar
import com.example.myapplication.utils.components.Drawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    val focusManager = LocalFocusManager.current
    val searchController = remember { SearchController() }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val currentDestination = navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination.value?.destination?.route

    val showToolbar = BOTTOM_NAV_ITEMS.firstOrNull { it.route == currentRoute }?.showToolbar ?: false

    ModalNavigationDrawer(drawerContent = { Drawer(navController) }, drawerState = drawerState) {
        CompositionLocalProvider(LocalSearchController provides searchController) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.surface,
                topBar = { if (showToolbar) DashboardToolbar(drawerState, scrollBehavior) },
                bottomBar = { BottomNav(navController) },
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
            ) { innerPadding ->
                CompositionLocalProvider(LocalTopAppBarScrollBehavior provides scrollBehavior) {
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
