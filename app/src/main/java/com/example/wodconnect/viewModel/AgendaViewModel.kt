package com.example.wodconnect.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wodconnect.data.model.Clases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
    class AgendaViewModel @Inject constructor(
        private val firestore: FirebaseFirestore,
        private val auth: FirebaseAuth
    ) : ViewModel() {

        private val _clasesReservadas = MutableLiveData<List<Clases>>()
        val clasesReservadas: LiveData<List<Clases>> = _clasesReservadas

    //para recuperar las reservas realizadas por el usuario
        fun cargarReservasUsuario() {
            val userId = auth.currentUser?.uid ?: return

            firestore.collection("Usuarios")
                .document(userId)
                .collection("Reservas")
                .get()
                .addOnSuccessListener { snapshot ->
                    val clases = snapshot.mapNotNull { it.toObject(Clases::class.java) }
                    _clasesReservadas.value = clases
                }
                .addOnFailureListener {
                    _clasesReservadas.value = emptyList()
                }

        }
}
