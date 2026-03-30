package com.ecommerce.shoppy.config.utils.validator

import android.util.Log
import com.ecommerce.shoppy.R
import com.google.i18n.phonenumbers.PhoneNumberUtil

object GenericValidators {
    fun validateField(value: String, maxLength: Int? = null): Int? {
        return when {
            value.isBlank() -> R.string.field_is_required
            value.length < 2 -> R.string.atleast_two_characters_msg
            maxLength != null && value.length > maxLength -> R.string.must_be_less_than_fifty_characters
            else -> null
        }
    }

    fun validatePhoneNumber(digits: String): Boolean {
        return runCatching {
            val phoneUtil = PhoneNumberUtil.getInstance()
            val number = phoneUtil.parseAndKeepRawInput("+$digits", null)
            val region = phoneUtil.getRegionCodeForNumber(number)
            phoneUtil.isValidNumberForRegion(number, region)
        }.getOrDefault(false)
    }
}