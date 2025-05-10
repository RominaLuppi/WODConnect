package com.example.wodconnect.data


data class Reserve(
    val id: Int,
    val userId: String,
    val classId: Int,
    val reserveDate: String?,
    val reserved: Boolean
)
