package com.example.myapplication.ui.dashboard.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.navigation.Destination
import com.example.myapplication.config.theme.Blue
import com.example.myapplication.config.theme.Brown
import com.example.myapplication.config.theme.Green
import com.example.myapplication.config.theme.Orange
import com.example.myapplication.config.theme.Purple80
import com.example.myapplication.config.theme.Yellow
import com.example.myapplication.config.utils.AppCompositionLocals.LocalDrawerStateController
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.core.model.NavigationItem
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer() {
    val navController = LocalParentNavController.current
    val drawerState = LocalDrawerStateController.current
    val scope = rememberCoroutineScope()

    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f)
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = navigationBarHeight)
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .statusBarsPadding()
                .height(100.sdp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.sdp)
                    .align(Alignment.Center)
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.sdp)
                    )
                    .border(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(12.sdp)
                    )
            ) {
                SvgImage(
                    asset = "shoppy_bag",
                    modifier = Modifier.size(25.sdp)
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(30.sdp)
                    .align(Alignment.TopEnd)
                    .background(Color.Transparent)
                    .clip(CircleShape)
                    .clickable { scope.launch { drawerState?.close() } },
            ) {
                SvgImage(
                    asset = "close",
                    color = Color.White,
                    modifier = Modifier.size(18.sdp)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(R.string.shopping).uppercase(),
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            letterSpacing = 1.ssp,
            color = MaterialTheme.colorScheme.outline,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 15.sdp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        LazyColumn {
            items(shoppingList) { item ->
                DrawerItem(
                    item = item,
                    onClick = { onItemClicked(item.route, navController, drawerState, scope) }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(R.string.support).uppercase(),
            fontSize = 11.ssp,
            lineHeight = 11.ssp,
            letterSpacing = 1.ssp,
            color = MaterialTheme.colorScheme.outline,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 15.sdp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        LazyColumn(modifier = Modifier.weight(1f, fill = true)) {
            items(supportList) { item ->
                DrawerItem(
                    item = item,
                    onClick = { onItemClicked(item.route, navController, drawerState, scope) }
                )
            }
        }

        Text(
            text = "v${BuildConfig.VERSION_NAME}",
            fontSize = 10.ssp,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End),
        )
    }
}

@Composable
fun DrawerItem(item: NavigationItem, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(50.sdp)
            .padding(horizontal = 8.sdp)
            .clip(RoundedCornerShape(15.sdp))
            .clickable { onClick.invoke() }
            .padding(horizontal = 8.sdp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.sdp)
                .background(
                    color = item.tint!!.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.sdp)
                )
        ) {
            SvgImage(
                asset = item.icon,
                color = item.tint,
                modifier = Modifier.size(18.sdp, 18.sdp)
            )
        }

        Spacer(Modifier.width(10.sdp))

        Text(
            text = stringResource(item.name),
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.width(5.sdp))

        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            tint = MaterialTheme.colorScheme.outline,
            contentDescription = null,
        )
    }
}

private fun onItemClicked(
    route: Any,
    navController: NavController?,
    drawerState: DrawerState?,
    scope: CoroutineScope
) {
    if (route == Unit || navController == null) return
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
    scope.launch { drawerState?.close() }
}

private val shoppingList = listOf(
    NavigationItem(
        name = R.string.my_orders,
        route = Destination.OrderListing,
        tint = Blue,
        icon = "box",
    ),
    NavigationItem(
        name = R.string.saved_address,
        route = Destination.AddressListing(selectionMode = false),
        tint = Green,
        icon = "location",
    ),
    NavigationItem(
        name = R.string.payment_method,
        route = Unit,
        tint = Yellow,
        icon = "card",
    ),
    NavigationItem(
        name = R.string.promo_code_and_coupon,
        route = Unit,
        tint = Orange,
        icon = "tag",
    ),
)

private val supportList = listOf(
    NavigationItem(
        name = R.string.notifications,
        route = Unit,
        tint = Purple80,
        icon = "notification",
    ),
    NavigationItem(
        name = R.string.help_and_support,
        route = Unit,
        tint = Brown,
        icon = "question",
    ),
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DrawerPreview() {
    Drawer()
}