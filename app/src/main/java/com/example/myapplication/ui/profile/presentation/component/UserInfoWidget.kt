package com.example.myapplication.ui.profile.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.config.components.image.NetworkImage
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.theme.Blue
import com.example.myapplication.core.model.User
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun UserInfoWidget(
    user: User,
    activeOrders: Int,
    completedOrders: Int,
    onViewOrdersClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.sdp)
            )
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
                )
                .padding(10.sdp)
                .fillMaxWidth()
        ) {
            NetworkImage(
                imageUrl = user.image ?: "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(55.sdp, 55.sdp)
                    .background(
                        color = Transparent,
                        shape = RoundedCornerShape(12.sdp)
                    )
            )

            Spacer(Modifier.width(5.sdp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 10.sdp)
                    .weight(1f)
            ) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    fontSize = 14.ssp,
                    lineHeight = 14.ssp,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(Modifier.height(2.sdp))

                Text(
                    text = user.email ?: "",
                    fontSize = 12.ssp,
                    lineHeight = 12.ssp,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }

        Spacer(Modifier.height(15.sdp))

        Box(Modifier.padding(horizontal = 10.sdp)) {
            OrdersWidgets(activeOrders, completedOrders, onViewOrdersClicked)
        }

        Spacer(Modifier.height(15.sdp))
    }
}

@Composable
fun OrdersWidgets(activeOrders: Int, completedOrders: Int, onViewOrdersClicked: () -> Unit) {
    val total = stringResource(R.string.total_orders_value).replace(
        "@value", "${activeOrders + completedOrders}"
    )
    val desc = "$activeOrders ${stringResource(R.string.active).lowercase()} · $completedOrders ${
        stringResource(
            R.string.completed
        ).lowercase()
    }"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(width = 0.5.dp, color = Blue, shape = RoundedCornerShape(10.sdp))
            .background(color = Blue.copy(alpha = 0.1f), shape = RoundedCornerShape(10.sdp))
            .fillMaxWidth()
            .padding(vertical = 13.sdp, horizontal = 10.sdp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.sdp)
                .background(color = Blue.copy(alpha = 0.2f), shape = CircleShape)
        ) {
            SvgImage(
                asset = "box", color = Blue, modifier = Modifier.size(20.sdp, 20.sdp)
            )
        }

        Spacer(Modifier.width(10.sdp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = total,
                fontSize = 13.ssp,
                lineHeight = 13.ssp,
                color = Blue,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(1.sdp))

            Text(
                text = desc,
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                color = MaterialTheme.colorScheme.outline,
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.view_all),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                fontWeight = FontWeight.Medium,
                color = Blue,
            )

            Spacer(Modifier.width(3.sdp))

            SvgImage(
                asset = "back",
                color = Blue,
                modifier = Modifier
                    .size(15.sdp, 15.sdp)
                    .rotate(180f)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewUserInfoWidgetWidget() {
    UserInfoWidget(
        activeOrders = 3, completedOrders = 7, user = User(
            id = 1,
            email = "Testing123@gmail.com",
            firstName = "Testing",
            gender = "femal",
            image = "",
            lastName = "Name",
            username = "testing.name",
            accessToken = "",
            refreshToken = ""
        )
    ) {}
}