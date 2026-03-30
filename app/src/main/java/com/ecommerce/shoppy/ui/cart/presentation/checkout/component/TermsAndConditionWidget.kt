package com.ecommerce.shoppy.ui.cart.presentation.checkout.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ecommerce.shoppy.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun TermsAndConditionWidget(value: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.sdp)
            )
            .padding(top = 8.sdp, bottom = 8.sdp, end = 10.sdp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick.invoke() }
            ),
        ) {
            RadioButton(
                selected = value,
                onClick = { onClick.invoke() },
            )

            Text(
                text = buildAnnotatedString {
                    val style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline
                    )
                    append(stringResource(R.string.i_agree_to))
                    withStyle(style = style) { append(" ${stringResource(R.string.terms_of_service)}") }
                    append(" ${stringResource(R.string.and)} ")
                    withStyle(style = style) { append("${stringResource(R.string.privacy_policy)}. ") }
                    append(stringResource(R.string.i_understand_msg))
                },
                fontSize = 12.ssp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTermsAndConditionWidget() {
    TermsAndConditionWidget(false) {}
}