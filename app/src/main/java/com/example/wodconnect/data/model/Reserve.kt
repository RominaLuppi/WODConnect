package com.example.wodconnect.data.model

import com.google.firebase.Timestamp


data class Reserve(
    val id: Int,
    val userId: String,
    val clasesId: String,
    val reserveDate: Timestamp?,
    val reserved: Boolean
)
