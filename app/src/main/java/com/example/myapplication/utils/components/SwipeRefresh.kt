package com.example.myapplication.utils.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    content: @Composable () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    // Start refresh when pulled
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            onRefresh.invoke()
        }
    }

    // End refresh when done
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            pullToRefreshState.startRefresh()
        } else {
            pullToRefreshState.endRefresh()
        }
    }

    val combinedNestedScrollConnection = remember(scrollBehavior) {
        if (scrollBehavior != null) {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val consumedByPullToRefresh =
                        pullToRefreshState.nestedScrollConnection.onPreScroll(available, source)
                    val remaining = available - consumedByPullToRefresh
                    val consumedByParent = scrollBehavior.nestedScrollConnection.onPreScroll(remaining, source)
                    return consumedByPullToRefresh + consumedByParent
                }

                override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                    val consumedByParent =
                        scrollBehavior.nestedScrollConnection.onPostScroll(consumed, available, source)
                    val remaining = available - consumedByParent
                    val consumedByPullToRefresh =
                        pullToRefreshState.nestedScrollConnection.onPostScroll(consumed, remaining, source)
                    return consumedByParent + consumedByPullToRefresh
                }

                override suspend fun onPreFling(available: Velocity): Velocity {
                    val consumedByPullToRefresh = pullToRefreshState.nestedScrollConnection.onPreFling(available)
                    val remaining = available - consumedByPullToRefresh
                    val consumedByParent = scrollBehavior.nestedScrollConnection.onPreFling(remaining)
                    return consumedByPullToRefresh + consumedByParent
                }

                override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                    val consumedByParent = scrollBehavior.nestedScrollConnection.onPostFling(consumed, available)
                    val remaining = available - consumedByParent
                    val consumedByPullToRefresh =
                        pullToRefreshState.nestedScrollConnection.onPostFling(consumed, remaining)
                    return consumedByParent + consumedByPullToRefresh
                }
            }
        } else {
            pullToRefreshState.nestedScrollConnection
        }
    }

    Box(modifier = modifier.nestedScroll(combinedNestedScrollConnection)) {
        content()

        if (pullToRefreshState.progress > 0f || pullToRefreshState.isRefreshing)
            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
    }
}