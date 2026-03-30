package com.ecommerce.shoppy.ui.order.presentation.detail.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.image.SvgImage
import com.ecommerce.shoppy.config.theme.Green
import com.ecommerce.shoppy.config.utils.Constants
import com.ecommerce.shoppy.core.model.Address
import com.ecommerce.shoppy.core.model.Order
import com.ecommerce.shoppy.core.model.OrderStatus
import com.ecommerce.shoppy.core.model.PaymentMethod
import com.ecommerce.shoppy.core.model.Shipping
import com.ecommerce.shoppy.core.shared.data.local.entity.OrderItem
import com.ecommerce.shoppy.core.shared.data.local.entity.Receipt
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun PaymentSummary(order: Order) {

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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.sdp, vertical = 12.sdp)
        ) {
            SvgImage(
                asset = "box",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.sdp, 16.sdp)
            )

            Spacer(Modifier.width(8.sdp))

            Text(
                text = stringResource(R.string.payment_summary),
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        HorizontalDivider(thickness = 1.sdp)

        Spacer(Modifier.height(8.sdp))

        ItemsTableView(order.items)

        Spacer(Modifier.height(10.sdp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.sdp)
        ) {
            Text(
                text = stringResource(R.string.sub_total),
                fontSize = 12.ssp,
                lineHeight = 12.ssp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(0.5f)
            )

            Text(
                text = "$${String.format("%.2f", order.receipt.subtotal)}",
                fontSize = 12.ssp,
                lineHeight = 12.ssp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(0.5f)
            )
        }

        Spacer(Modifier.height(5.sdp))

        HeadingValue(
            heading = stringResource(R.string.product_discount),
            value = "-$${String.format("%.2f", order.receipt.productDiscount)}",
            valueTint = Green
        )

        order.receipt.promoDiscount?.let {
            Spacer(Modifier.height(5.sdp))

            HeadingValue(
                heading = stringResource(R.string.promo_code).replace("(@value)", ""),
                value = "-$${it}",
                valueTint = Green
            )
        }

        Spacer(Modifier.height(5.sdp))

        HeadingValue(
            heading = stringResource(R.string.shipping),
            value = if (order.receipt.shippingFee == 0.0) stringResource(R.string.free)
            else "$${String.format("%.2f", order.receipt.shippingFee)}",
            valueTint = if (order.receipt.shippingFee == 0.0) Green else null
        )

        Spacer(Modifier.height(5.sdp))

        HeadingValue(
            heading = stringResource(R.string.vat),
            value = "$${Constants.VAT}",
        )

        Spacer(Modifier.height(5.sdp))

        HeadingValue(
            heading = stringResource(R.string.platform_fee),
            value = "$${Constants.PLATFORM_FEES}",
        )

        Spacer(Modifier.height(10.sdp))

        DottedHorizontalDivider()

        Spacer(Modifier.height(10.sdp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.sdp)
        ) {
            Text(
                text = stringResource(R.string.total),
                fontSize = 14.ssp,
                lineHeight = 14.ssp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(0.3f)
            )

            Text(
                text = "$${String.format("%.2f", order.receipt.totalAmount)}",
                fontSize = 18.ssp,
                lineHeight = 18.ssp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(0.7f)
            )
        }

        Spacer(Modifier.height(10.sdp))
    }
}

@Composable
private fun HeadingValue(heading: String, value: String, valueTint: Color? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 10.sdp)
    ) {
        Text(
            text = heading,
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.6f)
        )

        Text(
            text = value,
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Medium,
            color = valueTint ?: MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.4f)
        )
    }
}

@Composable
private fun DottedHorizontalDivider(addPadding: Boolean = true) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = if (addPadding) 10.sdp else 0.sdp)
            .height(1.sdp)
    ) {
        drawLine(
            color = Color.Gray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun ItemsTableView(items: List<OrderItem>) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.sdp)
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.sdp)
            )
            .padding(10.sdp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.items),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.weight(0.6f)
            )

            Text(
                text = stringResource(R.string.qty),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.weight(0.1f)
            )

            Text(
                text = stringResource(R.string.price),
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.weight(0.3f)
            )
        }

        Spacer(Modifier.height(8.sdp))

        HorizontalDivider()

        Spacer(Modifier.height(8.sdp))

        items.mapIndexed { index, it ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        top = if (index > 0) 8.sdp else 0.sdp,
                        bottom = if (index < (items.size - 1)) 8.sdp else 0.sdp
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = it.productName,
                    fontSize = 12.ssp,
                    lineHeight = 12.ssp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(0.6f)
                )

                Text(
                    text = "${it.quantity}",
                    fontSize = 11.ssp,
                    lineHeight = 11.ssp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.weight(0.1f)
                )

                Text(
                    text = "$${String.format("%.2f", it.originalPrice)}",
                    fontSize = 12.ssp,
                    lineHeight = 12.ssp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(0.3f)
                )
            }

            if (index < (items.size - 1)) DottedHorizontalDivider(addPadding = false)
        }
    }
}

@Preview
@Composable
private fun PreviewPaymentSummary() {
    PaymentSummary(
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