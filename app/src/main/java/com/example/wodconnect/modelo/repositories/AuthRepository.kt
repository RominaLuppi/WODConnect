package com.example.wodconnect.modelo.repositories

import com.example.wodconnect.data.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun logout()
    suspend fun getCurrentUser(): User?
}