package com.example.wodconnect.modelo.repositories

import com.example.wodconnect.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>

    suspend fun register(email: String, password: String): Result<User>{
        return try {
            val result = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,password)
                .await()
            val user = result.user
            if(user != null){
                Result.success(User(id = user.uid,
                    email = user.email ?: "",
                    name = user.displayName ?: ""))
            } else {
                Result.failure(Exception ("No se ha podido registrar al usuario"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun logout(){
        Firebase.auth.signOut()
    }
    suspend fun getCurrentUser(): User?
}