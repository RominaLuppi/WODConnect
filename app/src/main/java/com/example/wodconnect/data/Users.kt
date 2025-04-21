package com.example.wodconnect.data



data class Users(
    val id: String,
    val email: String,
    val password: String,
    val name: String,

    val registrationDate: String?
)
