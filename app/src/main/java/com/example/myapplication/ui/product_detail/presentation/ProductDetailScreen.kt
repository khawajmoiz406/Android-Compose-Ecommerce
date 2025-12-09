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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myapplication.ui.product_detail.presentation.components.ProductDetailTopBar
import com.example.myapplication.utils.AppCompositionLocals.LocalParentNavController
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
                    .drawBehind {
                        val shadowColor = Color.Black.copy(alpha = 0.25f)
                        val paint = Paint()
                            .asFrameworkPaint()
                            .apply {
                                color = android.graphics.Color.TRANSPARENT
                                setShadowLayer(
                                    4.dp.toPx(), // shadow blur
                                    0f, // shadow offset x
                                    (-1).dp.toPx(), // shadow offset y
                                    shadowColor.toArgb() // shadow color
                                )
                            }

                        drawIntoCanvas {
                            it.nativeCanvas.drawRoundRect(
                                0f,
                                0f,
                                size.width,
                                size.height / 2,
                                16.dp.toPx(),
                                16.dp.toPx(),
                                paint
                            )
                        }
                    }
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                items(100) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(Color.Black.copy(0.5f)),
                        content = { Text("$index", modifier = Modifier.fillMaxSize()) }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
