package com.example.myapplication.ui.order.presentation.listing.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.utils.DateTimeUtils
import com.example.myapplication.core.model.Address
import com.example.myapplication.core.model.Order
import com.example.myapplication.core.model.OrderStatus
import com.example.myapplication.core.model.PaymentMethod
import com.example.myapplication.core.model.Shipping
import com.example.myapplication.core.shared.data.local.entity.OrderItem
import com.example.myapplication.core.shared.data.local.entity.Receipt
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("DefaultLocale")
@Composable
fun ItemOrder(index: Int, order: Order, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(
                top = if (index == 0) 15.sdp else 0.sdp,
                bottom = 12.sdp,
                start = 10.sdp,
                end = 10.sdp
            )
            .border(1.sdp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.sdp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.sdp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.sdp))
            .clickable { onClick.invoke() }
            .padding(10.sdp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.sdp)
                    .background(
                        color = order.orderStatus.color.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.sdp)
                    )
            ) {
                SvgImage(
                    asset = order.orderStatus.icon,
                    color = order.orderStatus.color,
                    modifier = Modifier.size(18.sdp, 18.sdp)
                )
            }

            Spacer(Modifier.width(10.sdp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = order.orderNumber,
                    fontSize = 12.ssp,
                    lineHeight = 12.ssp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(1.sdp))

                Text(
                    text = order.orderStatus.toString(),
                    fontSize = 10.ssp,
                    lineHeight = 10.ssp,
                    color = order.orderStatus.color,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${String.format("%.2f", order.receipt.totalAmount)}",
                    fontSize = 13.ssp,
                    lineHeight = 13.ssp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(Modifier.height(1.sdp))

                Text(
                    text = DateTimeUtils.convertMilliToDateTime(order.createdAt, "MMM dd, yyyy"),
                    fontSize = 10.ssp,
                    lineHeight = 10.ssp,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }

        Spacer(Modifier.height(10.sdp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceDim,
                    shape = RoundedCornerShape(10.sdp)
                )
                .padding(horizontal = 10.sdp, vertical = 12.sdp)
        ) {
            SvgImage(
                asset = "occupation",
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(12.sdp, 12.sdp)
            )

            Spacer(Modifier.width(5.sdp))

            Text(
                text = order.getProductsNames(),
                fontSize = 10.ssp,
                lineHeight = 10.ssp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(5.sdp))

            SvgImage(
                asset = "back",
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .size(14.sdp, 14.sdp)
                    .rotate(180f)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewItemOrder() {
    ItemOrder(
        0, Order(
            id = 2,
            orderNumber = "ORD-202623-19121314",
            trackingNumber = "TRK328567502684",
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
            orderStatus = OrderStatus.Pending,
            createdAt = 1772549570993,
            updatedAt = 1772549570993
        )
    ) { }
}