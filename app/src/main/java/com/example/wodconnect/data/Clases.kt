package com.example.wodconnect.data

import com.google.firebase.Timestamp

data class Clases(
    val id: String = "",
    val name: String = "",
    val description: String = "" ,
    val startTime: Timestamp? = Timestamp.now(),
    val endTime: Timestamp? = Timestamp.now(),
    val availablePlaces: Int = 0
)
