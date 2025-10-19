package com.example.myapplication.utils.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import com.example.myapplication.R
import com.example.myapplication.utils.AppCompositionLocals.LocalSearchController
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardToolbar(
    drawerState: DrawerState,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchTextChanged: ((String) -> Unit)? = null,
    onFilterClicked: (() -> Unit)? = null
) {
    val toolbarHeight = 80.sdp
    val minHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx() }
    val searchBarHeightPx = remember { mutableIntStateOf(0) }
    var maxHeightPx = minHeightPx + searchBarHeightPx.intValue
    val heightOffset = scrollBehavior.state.heightOffset

    LaunchedEffect(searchBarHeightPx) {
        if (searchBarHeightPx.intValue > 0) {
            scrollBehavior.state.heightOffsetLimit = -searchBarHeightPx.intValue.toFloat()
            maxHeightPx = minHeightPx + searchBarHeightPx.intValue
        }
    }

    val collapseProgress = if (searchBarHeightPx.intValue > 0) {
        (-heightOffset / searchBarHeightPx.intValue).coerceIn(0f, 1f)
    } else {
        0f
    }

    val opacity = (1f - (collapseProgress * 2f)).coerceIn(0f, 1f)

    Layout(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(bottomEnd = 8.sdp, bottomStart = 8.sdp)
        ),
        content = {
            Box(
                content = { Toolbar(drawerState, toolbarHeight) },
                modifier = Modifier
                    .height(toolbarHeight)
                    .fillMaxWidth()
            )

            Box(
                content = { SearchBar(onSearchTextChanged, onFilterClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { alpha = opacity }
            )
        },
    ) { measurables, constraints ->
        val toolbarPlaceable = measurables[0].measure(constraints)
        val searchBarPlaceable = measurables[1].measure(constraints)

        if (searchBarHeightPx.intValue == 0 && searchBarPlaceable.height > 0) {
            searchBarHeightPx.intValue = searchBarPlaceable.height
        }

        val finalHeight = maxOf(toolbarPlaceable.height, (maxHeightPx + heightOffset).toInt())

        layout(constraints.maxWidth, finalHeight) {
            toolbarPlaceable.place(0, 0)
            searchBarPlaceable.place(0, toolbarPlaceable.height)
        }
    }
}

@Composable
private fun Toolbar(drawerState: DrawerState, height: Dp) {
    val scope = rememberCoroutineScope()
    val statusBarHeight = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = statusBarHeight)
            .height(height)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(30.sdp)
                    .clickable { scope.launch { drawerState.open() } },
            ) {
                SvgImage(
                    asset = "menu",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.sdp)
                )
            }

            Text(
                text = stringResource(R.string.app_name),
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(30.sdp)
                    .align(Alignment.CenterVertically)
                    .clickable { },
            ) {
                SvgImage(
                    asset = "notification",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(18.sdp)
                )
            }
        }
    }
}

@Composable
private fun SearchBar(onSearchTextChanged: ((String) -> Unit)? = null, onFilterClicked: (() -> Unit)? = null) {
    val focusManager = LocalFocusManager.current
    val search = remember { mutableStateOf("") }
    val searchController = LocalSearchController.current

    rememberDebounce(
        value = search.value,
        onDebounce = {
            searchController?.str?.value = it
            onSearchTextChanged?.invoke(it)
        }
    )

    Row(
        modifier = Modifier.padding(start = 6.sdp, end = 6.sdp, bottom = 10.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            singleLine = true,
            value = search.value,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions { focusManager.clearFocus() },
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceDim, RoundedCornerShape(15.sdp))
                .padding(horizontal = 10.sdp, vertical = 8.sdp)
                .wrapContentHeight(),
            onValueChange = {
                search.value = it
                onSearchTextChanged?.invoke(it)
            },
            textStyle = TextStyle(
                fontSize = 12.ssp,
                lineHeight = 12.ssp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            decorationBox = { innerTextField ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SvgImage(
                        asset = "search",
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(16.sdp)
                    )

                    Spacer(Modifier.width(8.sdp))

                    Box(modifier = Modifier.weight(1f)) {
                        if (search.value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.search_product),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                fontSize = 12.ssp,
                                lineHeight = 12.ssp,
                            )
                        }
                        innerTextField()
                    }

                }
            }
        )

        Spacer(Modifier.width(6.sdp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.sdp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceDim)
                .align(Alignment.CenterVertically)
                .clickable { onFilterClicked?.invoke() },
        ) {
            SvgImage(
                asset = "filter",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(18.sdp)
            )
        }
    }
}