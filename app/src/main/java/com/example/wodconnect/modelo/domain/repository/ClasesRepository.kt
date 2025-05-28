package com.example.wodconnect.modelo.domain.repository

import com.example.wodconnect.data.model.Clases


interface ClasesRepository {

    suspend fun agregarClases(clases: Clases)
    suspend fun obtenerClases(): List<Clases>
    suspend fun agregarClasesSem(clases: List<Clases>)
}