package com.example.myapplication.utils.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.models.response.product.Product

@Composable
fun ItemProduct(item: Product, onClick: () -> Unit) {
    Column(modifier = Modifier.clip(RoundedCornerShape(15.dp))) {
        AsyncImage(
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.thumbnail)
                .crossfade(true)
                .crossfade(500)
                .build(),
        )

        Spacer(Modifier.height(10.dp))


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewItemProduct() {

}