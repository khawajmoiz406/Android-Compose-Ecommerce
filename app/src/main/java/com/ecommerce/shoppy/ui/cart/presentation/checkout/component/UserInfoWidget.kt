package com.ecommerce.shoppy.ui.cart.presentation.checkout.component

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.image.SvgImage
import com.ecommerce.shoppy.config.components.input.AppTextField
import com.ecommerce.shoppy.config.components.state.FieldState
import com.ecommerce.shoppy.config.theme.Green
import com.ecommerce.shoppy.config.utils.transformation.PhoneNumberVisualTransformation
import com.ecommerce.shoppy.core.model.User
import com.ecommerce.shoppy.core.pref.EncryptedSharedPref
import com.ecommerce.shoppy.core.pref.SharedPrefUtils
import com.google.gson.reflect.TypeToken
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun UserInfoWidget(fieldState: FieldState, onChanged: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    val user = SharedPrefUtils.getCurrentUser(LocalContext.current)

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
            modifier = Modifier
                .background(
                    color = Green.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
                )
                .padding(horizontal = 10.sdp, vertical = 8.sdp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(28.sdp)
                    .background(color = Green, shape = CircleShape)
            ) {
                SvgImage(
                    asset = "user",
                    color = androidx.compose.ui.graphics.Color.White,
                    modifier = Modifier.size(18.sdp, 18.sdp)
                )
            }

            Spacer(Modifier.width(10.sdp))

            Text(
                text = stringResource(R.string.contact_info),
                fontSize = 13.ssp,
                lineHeight = 13.ssp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        HorizontalDivider(thickness = 1.sdp, color = MaterialTheme.colorScheme.outlineVariant)

        Spacer(Modifier.height(10.sdp))

        Column(modifier = Modifier.padding(horizontal = 10.sdp)) {
            AppTextField(
                value = user?.email ?: "",
                enabled = false,
                height = 40.sdp,
                borderWidth = 1.sdp,
                onValueChange = { "" },
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth(),
                borderColor = MaterialTheme.colorScheme.outlineVariant,
                placeholder = stringResource(R.string.email_adrress),
                containerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
            )

            Spacer(Modifier.height(10.sdp))

            AppTextField(
                value = fieldState.value,
                height = 40.sdp,
                borderWidth = 1.sdp,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onChanged.invoke(it.filter { c -> c.isDigit() }.take(12)) },
                borderColor = MaterialTheme.colorScheme.outlineVariant,
                placeholder = stringResource(R.string.phone_number_hint),
                containerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                visualTransformation = PhoneNumberVisualTransformation(),
                onImeActionPerformed = { focusManager.clearFocus() },
                error = fieldState.error?.let { stringResource(it) }
            )

            Spacer(Modifier.height(10.sdp))

            Text(
                text = stringResource(R.string.contact_info_msg),
                fontSize = 10.ssp,
                lineHeight = 10.ssp,
                color = MaterialTheme.colorScheme.outline,
            )

            Spacer(Modifier.height(10.sdp))
        }
    }
}

@Preview
@Composable
private fun PreviewUserInfoWidget() {
    UserInfoWidget(FieldState()) {}
}