package com.example.myapplication.config.utils.validator

import com.example.myapplication.R

object GenericValidators {
    fun validateField(value: String, maxLength: Int? = null): Int? {
        return when {
            value.isBlank() -> R.string.field_is_required
            value.length < 2 -> R.string.atleast_two_characters_msg
            maxLength != null && value.length > maxLength -> R.string.must_be_less_than_fifty_characters
            else -> null
        }
    }
}