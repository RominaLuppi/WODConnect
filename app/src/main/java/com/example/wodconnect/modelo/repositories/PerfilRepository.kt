package com.example.wodconnect.modelo.repositories


import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class PerfilRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val storage: FirebaseStorage
) {

    suspend fun guardarPerfil(name: String, email: String, fechaNac: String): Result<Unit>{
        val uid = firebaseAuth.currentUser?.uid ?:
        return Result.failure(Exception ("Usuario no identificado"))

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

        } catch (e: Exception){
            Result.failure(e)
        }
    }
    //subir la foto de perfil del usuario a Firestore
  suspend fun subirFotoPerfil(uid: String, uri: Uri): Result<String> = suspendCoroutine{cont ->
        val storageRef = storage.reference.child("profile_images/$uid.jpg")

        storageRef.putFile(uri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { url ->
                cont.resume(Result.success(url.toString()))
            }
                .addOnFailureListener { e -> cont.resume(Result.failure(e)) }
        }
  }
    suspend fun actualizarFotoPerfil(uid: String, urlPhoto: String): Result<Unit>{
        return try{
            firestore.collection("Usuarios")
                .document(uid)
                .update("photoUrl",urlPhoto)
                .await()
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun obtenerUrlFotoPerfil(uid: String): Result<String?> {
        return try {
            val doc = firestore.collection("Usuarios")
                .document(uid)
                .get()
                .await()
            val urlFoto = doc.getString("photoUrl")
            Result.success(urlFoto)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarPerfilConFoto(uri: Uri, uid: String): Result<Unit> {
        val resultadoSubida = subirFotoPerfil(uid, uri)
        return if (resultadoSubida.isSuccess) {
            val urlFoto = resultadoSubida.getOrNull()!!
            actualizarFotoPerfil(uid, urlFoto)
        } else {
            Result.failure(resultadoSubida.exceptionOrNull()!!)
        }
    }


}