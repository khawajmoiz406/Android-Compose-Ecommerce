package com.example.myapplication.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.myapplication.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardToolbar(
    drawerState: DrawerState,
    scrollBehaviour: TopAppBarScrollBehavior,
    onSearchTextChanged: ((String) -> Unit)? = null,
    onFilterClicked: (() -> Unit)? = null
) {
//    double progress = shrinkOffset / (maxExtent - minExtent);
//    double opacity = (1 - (progress * 1.5)).clamp(0, 1);
//    double fastOpacity = (1 - (progress * 3)).clamp(0, 1);
//    double translateYTeam = (-43 * progress).clamp(-43, 0);
//    double translateYScore = (-12 * progress).clamp(-12, 0);
//    double translateYTime = (0 * progress).clamp(0, 0);
//    double height = (maxExtent - (progress * (maxExtent - minExtent))).clamp(minExtent, maxExtent).ceilToDouble();

    val search = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val collapsedFraction = scrollBehaviour.state.collapsedFraction
    val alpha = 1f - collapsedFraction

    LargeTopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        scrollBehavior = scrollBehaviour,
        title = {
            Column {
                if (alpha < 0f) Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier.padding(bottom = 8.sdp)
                )

                if (alpha > 0f) SearchBar(search, onSearchTextChanged)
            }

        },
        actions = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.sdp)
                    .align(Alignment.CenterVertically)
                    .clickable { },
            ) {
                SvgImage(
                    asset = "notification",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(18.sdp)
                )
            }
        },
        navigationIcon = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(30.sdp)
                    .clickable { scope.launch { drawerState.open() } },
            ) {
                SvgImage(
                    asset = "menu",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.sdp)
                )
            }
        }
    )
}

@Composable
private fun SearchBar(search: MutableState<String>, onSearchTextChanged: ((String) -> Unit)? = null) {
    BasicTextField(
        value = search.value,
        modifier = Modifier
            .fillMaxWidth()
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
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(16.sdp)
                )
                Spacer(Modifier.width(8.sdp))
                innerTextField()
            }
        }
    )
}