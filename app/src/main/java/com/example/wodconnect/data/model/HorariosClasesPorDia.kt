package com.example.wodconnect.data.model

import java.time.DayOfWeek
import java.time.LocalTime

data class HorariosClasesPorDia(
    val name: String,
    val description: String,
    val start: LocalTime,
    val end: LocalTime
)

val horariosPorDia: Map<DayOfWeek, List<HorariosClasesPorDia>> = mapOf(
    DayOfWeek.MONDAY to listOf(
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(8, 0), LocalTime.of(9, 0)),
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(9, 30), LocalTime.of(10, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(11, 0), LocalTime.of(12, 0)),
        HorariosClasesPorDia("HIIT", "Media intensidad", LocalTime.of(12, 30), LocalTime.of(13, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(14, 0), LocalTime.of(15, 0)),
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(16, 30), LocalTime.of(17, 30))
    ),
    DayOfWeek.TUESDAY to listOf(
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(9, 30), LocalTime.of(10, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(11, 0), LocalTime.of(12, 0)),
        HorariosClasesPorDia("HIIT", "Media intensidad", LocalTime.of(12, 30), LocalTime.of(13, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(14, 0), LocalTime.of(15, 0)),
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(16, 30), LocalTime.of(17, 30)),
        HorariosClasesPorDia("Open box", "Entrenamiento libre", LocalTime.of(19, 0), LocalTime.of(20, 0))
    ),
    DayOfWeek.WEDNESDAY to listOf(
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(7, 0), LocalTime.of(8, 0)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(8, 0), LocalTime.of(9, 0)),
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(9, 30), LocalTime.of(10, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(11, 0), LocalTime.of(12, 0)),
        HorariosClasesPorDia("HIIT", "Media intensidad", LocalTime.of(12, 30), LocalTime.of(13, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(14, 0), LocalTime.of(15, 0)),
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(16, 30), LocalTime.of(17, 30)),
        HorariosClasesPorDia("Open box", "Entrenamiento libre", LocalTime.of(19, 0), LocalTime.of(20, 0))
    ),
    DayOfWeek.THURSDAY to listOf(
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(9, 30), LocalTime.of(10, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(11, 0), LocalTime.of(12, 0)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(14, 0), LocalTime.of(15, 0)),
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(16, 30), LocalTime.of(17, 30)),
        HorariosClasesPorDia("Open box", "Entrenamiento libre", LocalTime.of(19, 0), LocalTime.of(20, 0)),
        HorariosClasesPorDia("HIIT", "Media intensidad", LocalTime.of(20, 15), LocalTime.of(21, 15))
    ),
    DayOfWeek.FRIDAY to listOf(
        HorariosClasesPorDia("HIIT", "Media intensidad", LocalTime.of(8, 0), LocalTime.of(9, 0)),
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(9, 30), LocalTime.of(10, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(11, 0), LocalTime.of(12, 0)),
        HorariosClasesPorDia("HIIT", "Media intensidad", LocalTime.of(12, 30), LocalTime.of(13, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(14, 0), LocalTime.of(15, 0)),
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(16, 30), LocalTime.of(17, 30)),
        HorariosClasesPorDia("Open box", "Entrenamiento libre", LocalTime.of(19, 0), LocalTime.of(20, 0))
    ),
    DayOfWeek.SATURDAY to listOf(
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(8, 0), LocalTime.of(9, 0)),
        HorariosClasesPorDia("Halterofilia", "Técnica de fuerza", LocalTime.of(9, 30), LocalTime.of(10, 30)),
        HorariosClasesPorDia("Crossfit", "Alta intensidad", LocalTime.of(11, 0), LocalTime.of(12, 0))
    )
)
