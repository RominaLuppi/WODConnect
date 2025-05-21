package com.example.wodconnect.data

import java.time.DayOfWeek
import java.time.LocalTime

data class HorariosClasesPorDia(
    val name: String,
    val description: String,
    val start: LocalTime,
    val end: LocalTime
)


val horariosPorDia: Map<DayOfWeek, List<Triple<String,String, Pair<LocalTime, LocalTime>>>> = mapOf(
    DayOfWeek.MONDAY to listOf(
        Triple("Crossfit", "Alta intensidad", LocalTime.of(8,0) to LocalTime.of(9,0)),
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(9,30) to LocalTime.of(10,30)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(11,0) to LocalTime.of(12,0)),
        Triple("HIIT", "Media intensidad", LocalTime.of(12,30) to LocalTime.of(13,30)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(14,0) to LocalTime.of(15,0)),
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(16,30) to LocalTime.of(17,30))),
    DayOfWeek.TUESDAY to listOf(
        Triple("Halterofilia","Técnica de fuerza", LocalTime.of(9,30) to LocalTime.of(10,30)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(11,0) to LocalTime.of(12,0)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(14,0) to LocalTime.of(15,0)),
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(16,30) to LocalTime.of(17,30)),
        Triple("Open box", "Entrenamiento libre", LocalTime.of(19,0) to LocalTime.of(20,0))),
    DayOfWeek.WEDNESDAY to listOf(
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(7,0) to LocalTime.of(8,0)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(8,0) to LocalTime.of(9,0)),
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(9,30) to LocalTime.of(10,30)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(11,0) to LocalTime.of(12,0)),
        Triple("HIIT", "Media intensidad", LocalTime.of(12,30) to LocalTime.of(13,30)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(14,0) to LocalTime.of(15,0)),
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(16,30) to LocalTime.of(17,30)),
        Triple("Open box", "Entrenamiento libre", LocalTime.of(19,0) to LocalTime.of(20,0))),
    DayOfWeek.THURSDAY to listOf(
        Triple("Halterofilia","Técnica de fuerza", LocalTime.of(9,30) to LocalTime.of(10,30)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(11,0) to LocalTime.of(12,0)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(14,0) to LocalTime.of(15,0)),
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(16,30) to LocalTime.of(17,30)),
        Triple("Open box", "Entrenamiento libre", LocalTime.of(19,0) to LocalTime.of(20,0))),
    DayOfWeek.FRIDAY to listOf(
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(9,30) to LocalTime.of(10,30)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(11,0) to LocalTime.of(12,0)),
        Triple("HIIT", "Media intensidad", LocalTime.of(12,30) to LocalTime.of(13,30)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(14,0) to LocalTime.of(15,0)),
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(16,30) to LocalTime.of(17,30)),
        Triple("Open box", "Entrenamiento libre", LocalTime.of(19,0) to LocalTime.of(20,0))),
    DayOfWeek.SATURDAY to listOf(
        Triple("Crossfit", "Alta intensidad", LocalTime.of(8,0) to LocalTime.of(9,0)),
        Triple("Halterofilia", "Técnica de fuerza", LocalTime.of(9,30) to LocalTime.of(10,30)),
        Triple("Crossfit", "Alta intensidad", LocalTime.of(11,0) to LocalTime.of(12,0))),
)
