package com.example.wodconnect.modelo.repositories

import com.example.wodconnect.data.Clases
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

//se obtienen las clases de la BD de Firebase
class ClasesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): ClasesRepository {
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
}