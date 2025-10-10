package com.example.myapplication.utils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.models.helper.NavigationItem
import com.example.myapplication.navigation.Destinations
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun BottomNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .height(65.sdp)
            .navigationBarsPadding()
    ) {
        bottomNavItems.map { item ->
            BottomNavItem(
                item = item,
                selectedRoute = currentRoute,
                onClick = { onItemClicked(item.route, navController) },
                modifier = Modifier
                    .weight(1f, fill = true)
                    .fillMaxHeight(),
            )
        }
    }
}

@Composable
fun BottomNavItem(item: NavigationItem, selectedRoute: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val isSelected = selectedRoute == item.route
    val color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.clickable { onClick.invoke() }
    ) {
        SvgImage(
            asset = item.icon,
            modifier = Modifier.size(16.sdp),
            color = color
        )

        Text(
            text = stringResource(item.name),
            color = color,
            fontSize = 11.ssp,
            maxLines = 1,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
        )
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
        icon = "home",
    ),
    NavigationItem(
        name = R.string.favourites,
        route = Destinations.Favourites.route,
        icon = "heart",
    ),
    NavigationItem(
        name = R.string.profile,
        route = Destinations.Profile.route,
        icon = "user",
    ),
)

@Preview(showSystemUi = true)
@Composable
fun PreviewBottomBar() {
    val navController = rememberNavController()
    BottomNav(navController)
}