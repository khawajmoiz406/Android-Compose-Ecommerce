package com.example.myapplication.ui.cart.presentation.cart.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.config.components.image.SvgImage
import com.example.myapplication.config.components.input.AppTextField
import com.example.myapplication.config.components.state.FieldState
import com.example.myapplication.config.theme.Brown
import com.example.myapplication.config.theme.Green
import com.example.myapplication.core.model.PromoCode
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun PromoCodeWidget(
    isLoading: Boolean,
    fieldState: FieldState,
    initialValue: PromoCode?,
    onPromoCodeChanged: (String) -> Unit,
    onApplyClicked: () -> Unit,
    onCancelClicked: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    
    Column(
        verticalArrangement = Arrangement.spacedBy(10.sdp),
        modifier = Modifier
            .border(width = 0.5.dp, color = Brown, shape = RoundedCornerShape(12.sdp))
            .background(color = Brown.copy(alpha = 0.1f), shape = RoundedCornerShape(12.sdp))
            .padding(10.sdp)
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.promo_code_msg),
            fontSize = 12.ssp,
            lineHeight = 12.ssp,
            fontWeight = FontWeight.Medium,
            color = Brown,
        )

        if (initialValue == null) {
            Row(verticalAlignment = Alignment.Top) {
                AppTextField(
                    value = fieldState.value,
                    onValueChange = { onPromoCodeChanged.invoke(it) },
                    placeholder = stringResource(R.string.enter_promo_code),
                    leadingIcon = "tag",
                    modifier = Modifier.weight(0.7f),
                    error = fieldState.error?.let { stringResource(it) },
                    onImeActionPerformed = { focusManager.clearFocus() },
                )

                Spacer(Modifier.width(10.sdp))

                Button(
                    onClick = { if (!isLoading) onApplyClicked.invoke() },
                    modifier = Modifier
                        .weight(0.3f)
                        .height(35.sdp),
                    shape = RoundedCornerShape(7.sdp)
                ) {
                    when (isLoading) {
                        false -> Text(
                            stringResource(R.string.apply),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        true -> CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Text(
                fontSize = 11.ssp,
                lineHeight = 11.ssp,
                color = MaterialTheme.colorScheme.outline,
                text = buildAnnotatedString {
                    append(stringResource(R.string.try_))
                    append(" ")
                    withStyle(
                        style = SpanStyle(color = Brown),
                        block = { append("SAVE20") }
                    )
                    append(" ")
                    append(stringResource(R.string.or_))
                    append(" ")
                    withStyle(
                        style = SpanStyle(color = Brown),
                        block = { append("WELCOME10") }
                    )
                },
            )

        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(50.sdp)
                    .border(width = 0.5.dp, color = Green, shape = RoundedCornerShape(12.sdp))
                    .background(color = Green.copy(alpha = 0.1f), shape = RoundedCornerShape(12.sdp))
                    .padding(horizontal = 5.sdp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(28.sdp)
                        .background(color = Green.copy(alpha = 0.2f), shape = CircleShape)
                ) {
                    SvgImage(
                        asset = "check_circle",
                        color = Green,
                        modifier = Modifier.size(18.sdp, 18.sdp)
                    )
                }

                Spacer(Modifier.width(10.sdp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = initialValue.name,
                        fontSize = 12.ssp,
                        lineHeight = 12.ssp,
                        color = Green,
                    )

                    Spacer(Modifier.height(2.sdp))

                    Text(
                        text = stringResource(R.string.promo_code_applied),
                        fontSize = 11.ssp,
                        lineHeight = 11.ssp,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(28.sdp)
                        .clickable {
                            onPromoCodeChanged.invoke("")
                            onCancelClicked.invoke()
                        },
                ) {
                    SvgImage(
                        asset = "cancel_circle",
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(18.sdp, 18.sdp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPromoCodeWidget() {
    PromoCodeWidget(false, FieldState(), null, {}, {}) { }
}