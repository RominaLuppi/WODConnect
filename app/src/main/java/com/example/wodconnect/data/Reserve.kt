package com.example.wodconnect.data

import com.google.firebase.Timestamp


data class Reserve(
    val id: Int,
    val userId: String,
    val classId: String,
    val reserveDate: Timestamp?,
    val reserved: Boolean
)
