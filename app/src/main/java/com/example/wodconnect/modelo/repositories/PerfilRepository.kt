package com.example.wodconnect.modelo.repositories


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class PerfilRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,

) {

    suspend fun guardarPerfil(name: String, email: String, fechaNac: String): Result<Unit> {
        val uid = firebaseAuth.currentUser?.uid
            ?: return Result.failure(Exception("Usuario no identificado"))

        val datosPerfil = mapOf(
            "nombre" to name,
            "email" to email,
            "fechaNacimiento" to fechaNac
        )
        return try {
            firestore.collection("Usuarios")
                .document(uid)
                .set(datosPerfil)
                .await()
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}