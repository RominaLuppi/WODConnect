package com.example.wodconnect.modelo.repositories

import com.example.wodconnect.data.Clases
import com.example.wodconnect.data.DaysOfWeek


interface ClasesRepository {

    suspend fun agregarClases(clases: Clases)
    suspend fun obtenerClases(): List<Clases>
    suspend fun agregarClasesSem(clases: List<Clases>)
}