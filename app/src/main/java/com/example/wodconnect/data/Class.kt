package com.example.wodconnect.data


data class Class(
    val id: Int, //en la BD es serial, en koltlin es Int
    val name: String,
    val description: String?,
    val startTime: String?,
    val endTime: String?,
    val weekDay: String?,
    val availablePlaces: Int = 0
)
