package com.example.wodconnect.data.repository

import com.example.wodconnect.data.model.User
import com.example.wodconnect.modelo.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email,password).await()
            val firebaseUser = result.user
            if(firebaseUser != null){
                Result.success(
                    User(
                        id = firebaseUser.uid,
                        email = firebaseUser.email ?: "",
                        name = firebaseUser.displayName ?: ""
                    )
                )
            }else {
                Result.failure(Exception("Usuario no encontrado"))
            }

        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return  firebaseUser?.let {
            User(
                id = it.uid,
                email = it.email ?: "",
                name = it.displayName ?: ""
            )
        }
    }
}