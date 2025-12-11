package com.example.myapplication.ui.product_detail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myapplication.ui.product_detail.presentation.components.ProductDetailTopBar
import com.example.myapplication.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.utils.ComposableUtils.topShadowScope
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: Int, viewModel: ProductDetailViewModel = koinViewModel()) {
    val product = viewModel.product.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val navController = LocalParentNavController.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    LaunchedEffect(productId) {
        if (viewModel.product.value == null) viewModel.getProductDetail(productId.toString())
    }

    // TODO: Add shimmer layout here
    if (product.value == null || uiState.value.isLoading) return Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = { CircularProgressIndicator() }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProductDetailTopBar(
                scrollBehavior = scrollBehavior,
                product = product.value!!,
                onBackPressed = { navController?.popBackStack() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .zIndex(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .drawBehind { topShadowScope(topStartCorner = 16.dp, topEndCorner = 16.dp) }
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                items(100) { index ->
                    if (index == 0) Spacer(modifier = Modifier.height(10.sdp))
                    Text(
                        "$index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.sdp)
                            .height(20.sdp)
                    )
                    Spacer(modifier = Modifier.height(10.sdp))
                }
            }
        }
    }
}
