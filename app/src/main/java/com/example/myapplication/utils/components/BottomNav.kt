package com.example.myapplication.utils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.models.helper.NavigationItem
import com.example.myapplication.navigation.Destinations
import com.example.myapplication.navigation.NavGraph
import com.example.myapplication.utils.theme.MyApplicationTheme

@Composable
fun BottomNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    if (currentRoute !in bottomNavItems.map { it.route }) return

    NavigationBar(
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .navigationBarsPadding()
            .padding(horizontal = 25.dp)
            .clip(RoundedCornerShape(40.dp))
    ) {
        bottomNavItems.map { item ->
            val isSelected = currentRoute == item.route
            val color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary

            NavigationBarItem(
                selected = isSelected,
                alwaysShowLabel = false,
                onClick = { onItemClicked(item.route, navController) },
                icon = {
                    Image(
                        painter = painterResource(item.activeIcon),
                        contentDescription = stringResource(item.name),
                        modifier = Modifier.size(55.dp),
                        colorFilter = ColorFilter.tint(color),
                    )
                }
            )
        }
    }
}

private fun onItemClicked(route: String, navController: NavController) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

private val bottomNavItems = listOf(
    NavigationItem(
        name = R.string.home,
        route = Destinations.Home.route,
        activeIcon = R.drawable.ic_launcher_foreground,
        inactiveIcon = R.drawable.ic_launcher_foreground
    ),
    NavigationItem(
        name = R.string.favourites,
        route = Destinations.Favourites.route,
        activeIcon = R.drawable.ic_launcher_foreground,
        inactiveIcon = R.drawable.ic_launcher_foreground
    ),
    NavigationItem(
        name = R.string.profile,
        route = Destinations.Profile.route,
        activeIcon = R.drawable.ic_launcher_foreground,
        inactiveIcon = R.drawable.ic_launcher_foreground
    ),
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBottomBar() {
    val navController = rememberNavController()

    MyApplicationTheme {
        Scaffold(
            bottomBar = { BottomNav(navController) },
        ) { innerPadding -> NavGraph(navController, innerPadding) }
    }
}