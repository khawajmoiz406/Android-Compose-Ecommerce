package com.ecommerce.shoppy.ui.order.presentation.detail.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ecommerce.shoppy.config.components.image.SvgImage
import com.ecommerce.shoppy.config.utils.DateTimeUtils
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.core.model.Order
import com.ecommerce.shoppy.core.model.OrderStatus
import com.ecommerce.shoppy.core.model.PaymentMethod
import com.ecommerce.shoppy.core.model.Shipping
import com.ecommerce.shoppy.core.shared.data.local.entity.OrderItem
import com.ecommerce.shoppy.core.shared.data.local.entity.Receipt
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("DefaultLocale")
@Composable
fun OrderTimeline(order: Order) {
    Column(
        modifier = Modifier
            .border(1.sdp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.sdp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.sdp))
            .fillMaxWidth()
            .padding(10.sdp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(order.orderStatus.color.copy(alpha = 0.2f), RoundedCornerShape(20.sdp))
                    .padding(horizontal = 10.sdp, vertical = 4.sdp)
            ) {
                SvgImage(
                    asset = order.orderStatus.icon,
                    color = order.orderStatus.color,
                    modifier = Modifier.size(15.sdp, 15.sdp)
                )

                Spacer(Modifier.width(5.sdp))

                Text(
                    text = order.orderStatus.toString(),
                    fontSize = 11.ssp,
                    lineHeight = 11.ssp,
                    fontWeight = FontWeight.SemiBold,
                    color = order.orderStatus.color,
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = DateTimeUtils.convertMilliToDateTime(order.createdAt, "MMM dd, yyyy"),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                color = MaterialTheme.colorScheme.outline,
            )
        }

        Spacer(Modifier.height(10.sdp))

        LinearProgressIndicator(
            progress = { order.getOrderProgress().let { if (it < 1.0f) it - 0.12f else it } },
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(0.1f),
            strokeCap = StrokeCap.Round,
            modifier = Modifier
                .height(6.sdp)
                .fillMaxWidth()
        )

        Spacer(Modifier.height(15.sdp))

        Row {
            ItemTimeline(
                1,
                OrderStatus.Pending,
                order.getOrderProgress() > 0.25,
                order.createdAt,
            )
            ItemTimeline(
                2,
                OrderStatus.Confirmed,
                order.getOrderProgress() >= 0.50,
                order.createdAt,
            )
            ItemTimeline(
                3,
                OrderStatus.Shipped,
                order.getOrderProgress() >= 0.75,
                order.createdAt,
            )
            ItemTimeline(
                4,
                OrderStatus.Delivered,
                order.getOrderProgress() == 1.0f,
                order.createdAt,
            )
        }
    }
}

@Composable
private fun RowScope.ItemTimeline(
    number: Int,
    orderStatus: OrderStatus,
    completed: Boolean,
    time: Long
) {
    val bgColor =
        if (completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(25.sdp)
                .background(bgColor, CircleShape)
        ) {
            if (completed)
                SvgImage(
                    asset = "check_circle",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(15.sdp, 15.sdp)
                )
            else
                Text(
                    text = number.toString(),
                    fontSize = 11.ssp,
                    lineHeight = 11.ssp,
                    color = MaterialTheme.colorScheme.outline,
                )
        }

        Spacer(Modifier.height(5.sdp))

        Text(
            text = orderStatus.toString(),
            fontSize = 10.ssp,
            lineHeight = 10.ssp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = "${if (completed) "" else "Est."} ${
                DateTimeUtils.convertMilliToDateTime(
                    time,
                    "MMM dd"
                )
            }",
            fontSize = 8.ssp,
            lineHeight = 8.ssp,
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Preview
@Composable
private fun PreviewOrderTimeline() {
    OrderTimeline(
        Order(
            id = 2,
            orderNumber = "ORD-202623-19121314",
            trackingNumber = "TRK328567502684",
            phoneNumber = "+123456789",
            items = listOf(
                OrderItem(
                    productId = 102,
                    productName = "Apple Airpower Wireless Charger",
                    quantity = 7,
                    discount = 25.084864,
                    originalPrice = 83.57355199999999,
                    discountedPrice = 79.99,
                    subtotal = 559.93,
                    createdAt = 1772549570993,
                    updatedAt = 1772549570993
                ),
                OrderItem(
                    productId = 107,
                    productName = "Beats Flex Wireless Earphones",
                    quantity = 17,
                    discount = 48.69525900000001,
                    originalPrice = 52.854427,
                    discountedPrice = 49.99,
                    subtotal = 849.83,
                    createdAt = 1772549570993,
                    updatedAt = 1772549570993
                ),
                OrderItem(
                    productId = 154,
                    productName = "Black Sun Glasses",
                    quantity = 17,
                    discount = 25.185602,
                    originalPrice = 31.471505999999998,
                    discountedPrice = 29.99,
                    subtotal = 509.83,
                    createdAt = 1772549570993,
                    updatedAt = 1772549570993
                )
            ),
            receipt = Receipt(
                subtotal = 1919.59,
                productDiscount = 98.965725,
                promoDiscount = null,
                vat = 1.0,
                platformFees = 2.0,
                shippingFee = 50.0,
                totalAmount = 1932.59
            ),
            shippingMethod = Shipping(
                id = 3,
                name = "Overnight Shipping",
                desc = "Next business days",
                price = 50.0
            ),
            shippingAddress = Address(
                id = 1,
                type = 0,
                fullName = "Gulberg Home",
                address = "Canal park, street# 5 Gulberg 2",
                houseNo = "23-A",
                city = "Lahore",
                state = "Punjab",
                country = "Pakistan",
                zipCode = "54000"
            ),
            paymentMethod = PaymentMethod.CashOnDelivery,
            orderStatus = OrderStatus.Confirmed,
            createdAt = 1772549570993,
            updatedAt = 1772549570993
        )
    )
}