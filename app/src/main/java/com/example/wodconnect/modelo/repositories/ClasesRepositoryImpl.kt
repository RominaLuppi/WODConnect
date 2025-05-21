package com.example.wodconnect.modelo.repositories

import android.util.Log
import com.example.wodconnect.data.Clases
import com.example.wodconnect.data.DaysOfWeek
import com.example.wodconnect.data.getDaysOfWeek
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

//se obtienen las clases de la BD de Firebase
class ClasesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): ClasesRepository {


    override suspend fun agregarClases(clases: Clases) {
        try {
            firestore.collection("Clases").add(clases).await()
        } catch (_: Exception){
        }
    }

    override suspend fun obtenerClases(): List<Clases> = withContext(Dispatchers.IO) {
        try {
            val snapshot = firestore.collection("Clases").get().await()
            snapshot.documents.mapNotNull { document ->
                document.toObject(Clases::class.java)?.copy(id = document.id)
            }
        }catch (e: Exception){
           emptyList()
        }
    }

    override suspend fun agregarClasesSem(clases: List<Clases>) {
       try {
           val batch = firestore.batch()
           val collection = firestore.collection("Clases")
           for (clase in clases){
               val docRef = collection.document()
               batch.set(docRef,clase)
           }
           batch.commit().await()
           Log.d("ClasesRepository", "Clases subidas correctamente")
       } catch (e: Exception){
           Log.e("ClasesRepository", "Error al subir clases: ${e.message}")

       }
    }
}