package com.example.app.domain.utils

import android.util.Patterns

fun String.isEmailValid(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
