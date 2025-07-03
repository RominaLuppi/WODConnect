package com.example.wodconnect.data.repository

import com.example.wodconnect.data.model.User
import com.example.wodconnect.modelo.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email,password).await()
            val firebaseUser = result.user
            if(firebaseUser != null){
                val doc = firestore.collection("roles").document(firebaseUser.uid).get().await()
               // val role = getUserRole(firebaseUser.uid) ?: "user"
                val role = doc.getString("role") ?: "user"

                val user = User(
                        id = firebaseUser.uid,
                        email = firebaseUser.email ?: "",
                        name = firebaseUser.displayName ?: "",
                        role = role
                    )
                Result.success(user)

                } else {
                Result.failure(Exception("Usuario no encontrado"))
            }

        }catch (e: Exception){
            Result.failure(e)
        }
    }
    override suspend fun getUserRole(uid: String): String?{
        return try{
            val doc = firestore.collection("roles").document(uid).get().await()
            if (doc.exists()) doc.getString("role") else null

        } catch (e: Exception){
            null
        }
    }

    override suspend fun createUserWithDefaultRole(firebaseUser: FirebaseUser) {
        val docRef = firestore.collection("roles").document(firebaseUser.uid)
        val doc = docRef.get().await()
        if (!doc.exists()){
            val userObject = User(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = firebaseUser.displayName ?: "",
                role = "user"
            )
            docRef.set(userObject).await()
        }
    }

    override suspend fun register(email: String, password: String): Result<User>{
        return try {
            val result = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,password)
                .await()
            val user = result.user
            if(user != null){
                val userObject = User(
                    id = user.uid,
                    email = user.email ?: "",
                    name = user.displayName ?: "",
                    role = "user"
                )
                firestore.collection("roles").document(user.uid).set(userObject).await()

                Result.success(userObject)

            } else {
                Result.failure(Exception ("No se ha podido registrar al usuario"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return  firebaseUser?.let {
            val role = getUserRole(it.uid) ?: "user"
            User(
                id = it.uid,
                email = it.email ?: "",
                name = it.displayName ?: "",
                role = role
            )
        }
    }
}