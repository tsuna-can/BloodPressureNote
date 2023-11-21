package com.example.bloodpressurenote.util

import com.example.bloodpressurenote.R

fun lessThanMaxLength(value: String, maxLength: Int): Boolean {
    return value.length <= maxLength
}

fun moreThanMinLength(value: String, minLength: Int): Boolean {
    return value.length >= minLength
}

fun isNumeric(value: String): Boolean {
    return value.toIntOrNull() != null
}

fun isBlank(value: String): Boolean {
    return value.isBlank()
}

fun validator(
    value: String,
    allowBlank: Boolean = false,
    isNumeric: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
): StringResource? {
    if (maxLength != null && !lessThanMaxLength(value, maxLength)) {
        return ResStringResource.create(R.string.error_more_3_digits)
    }

    if (minLength != null && !moreThanMinLength(value, minLength)) {
        return ResStringResource.create(R.string.error_more_100_digits)
    }

    if (isNumeric && !isNumeric(value)) {
        return ResStringResource.create(R.string.error_not_number)
    }

    if (!allowBlank && isBlank(value)) {
        return ResStringResource.create(R.string.error_mandatory)
    }

    return null
}
