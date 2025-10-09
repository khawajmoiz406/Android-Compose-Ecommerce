package com.example.myapplication.utils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.models.helper.NavigationItem
import com.example.myapplication.navigation.Destinations

@Composable
fun Drawer(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(150.dp),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.weight(1f, fill = true)) {
            itemsIndexed(drawerItemsList) { index, item ->
                DrawerItem(
                    item = item,
                    selectedRoute = currentRoute,
                    onClick = { onItemClicked(item.route, navController) }
                )
                if (index < drawerItemsList.size - 1) {
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }

        Text(
            text = "v${BuildConfig.VERSION_NAME}",
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
    }
}

@Composable
fun DrawerItem(item: NavigationItem, selectedRoute: String, onClick: () -> Unit) {
    val isSelected = selectedRoute == item.route
    val icon = if (isSelected) item.activeIcon else item.inactiveIcon
    val color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
    val bgColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color = bgColor)
            .clickable { onClick.invoke() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(color = color)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = stringResource(item.name),
                color = color,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = true)
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

private val drawerItemsList = listOf(
    NavigationItem(
        name = R.string.dashboard,
        route = Destinations.Dashboard.route,
        activeIcon = R.drawable.ic_launcher_background,
        inactiveIcon = R.drawable.ic_launcher_foreground
    ),
    NavigationItem(
        name = R.string.about_us,
        route = Destinations.AboutUs.route,
        activeIcon = R.drawable.ic_launcher_background,
        inactiveIcon = R.drawable.ic_launcher_foreground
    ),
    NavigationItem(
        name = R.string.privacy_policy,
        route = Destinations.PrivacyPolicy.route,
        activeIcon = R.drawable.ic_launcher_background,
        inactiveIcon = R.drawable.ic_launcher_foreground
    ),
    NavigationItem(
        name = R.string.settings,
        route = Destinations.Settings.route,
        activeIcon = R.drawable.ic_launcher_background,
        inactiveIcon = R.drawable.ic_launcher_foreground
    )
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DrawerPreview() {
    val navController = rememberNavController()
    Drawer(navController)
}