package com.example.wodconnect.modelo

data class Workout(
    val id: String,
    val name: String,
    val mode: String,
    val equipment: List<String>,
    val exercises: List<String>,
    val createdAt: String,
    val updatedAt: String,
    val trainerTips: List<String>

)

