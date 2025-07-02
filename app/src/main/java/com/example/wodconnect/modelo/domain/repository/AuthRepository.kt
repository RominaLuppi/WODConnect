package com.example.wodconnect.modelo.domain.repository

import com.example.wodconnect.data.model.User
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
    suspend fun logout()
    suspend fun getCurrentUser(): User?
    suspend fun getUserRole(uid: String): String?
    suspend fun createUserWithDefaultRole(firebaseUser: FirebaseUser)
}