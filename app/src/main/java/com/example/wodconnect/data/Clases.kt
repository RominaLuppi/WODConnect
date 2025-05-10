package com.example.wodconnect.data

data class Clases(
    val id: Int,
    val name: String,
    val description: String?,
    val startTime: String?,
    val endTime: String?,
    val weekDay: String?,
    val availablePlaces: Int = 0
)
