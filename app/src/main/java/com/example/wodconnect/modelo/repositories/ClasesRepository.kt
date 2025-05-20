package com.example.wodconnect.modelo.repositories

import com.example.wodconnect.data.Clases

interface ClasesRepository {
    suspend fun obtenerClases(): List<Clases>
}