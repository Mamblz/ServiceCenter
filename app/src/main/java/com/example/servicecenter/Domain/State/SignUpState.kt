package com.example.app.domain.state


data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val city: String = "",
    val phoneNumber: String = "",
    val isEmailValid: Boolean = true
)


