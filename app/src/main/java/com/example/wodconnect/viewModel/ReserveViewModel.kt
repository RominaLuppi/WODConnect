package com.example.wodconnect.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wodconnect.data.model.Clases
import com.example.wodconnect.data.model.getDaysOfWeek
import com.example.wodconnect.data.model.horariosPorDia
import com.example.wodconnect.data.model.toLocalDate
import com.example.wodconnect.modelo.domain.repository.ClasesRepository
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel

class   ReserveViewModel @Inject constructor(
    private val clasesRepository: ClasesRepository
): ViewModel() {

    private val _allClases = MutableLiveData<List<Clases>>()
    val allClases: LiveData<List<Clases>> = _allClases

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _clasesPorDia = MutableLiveData<List<Clases>>(null)
    val clasesPorDia: LiveData<List<Clases>> = _clasesPorDia

    private val _clasesSemGeneradas = MutableLiveData<List<Clases>>()
    val clasesSemGeneradas: LiveData<List<Clases>?> = _clasesSemGeneradas

    private val _clasesReservadas = MutableLiveData<List<String>>()
    val clasesReservadas: LiveData<List<String>> = _clasesReservadas

    private val _reservasUsuario = MutableLiveData<List<String>>()
    val reservasUsuario: LiveData<List<String>> = _reservasUsuario

    val daysOfWeek = getDaysOfWeek()

    init {
        obtenerClases()
        obtenerClasesPorDia(LocalDate.now())
        cargarClasesSem()
    }

    //se obtiene la lista de clases de la semana, se guardan en Firestore y se actualiza el LiveData
    private fun cargarClasesSem(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val clases = generarClasesSem()
                clasesRepository.agregarClasesSem(clases)
                _clasesSemGeneradas.value = clases
                _errorMessage.value = null
            } catch (e: Exception){
                _errorMessage.value = "Error al cargar las clases de la semana: ${e.message}"
            } finally {
                _isLoading.value = false

            }
        }
    }
    //para obtener las clases de toda la semana con su nombre, detalle y horarios
    private suspend fun generarClasesSem() : List<Clases> {
        val dias = getDaysOfWeek()
        val plazasDisponibles = 12
        val clasesSemana = mutableListOf<Clases>()

        for (dia in dias){
            val clasesDelDia = horariosPorDia[dia.fecha.dayOfWeek] ?: emptyList()

            for(infoClase in clasesDelDia){
                val startDateTime = dia.fecha.atTime(infoClase.start)
                val endDateTime = dia.fecha.atTime(infoClase.end)

                val startInstant = startDateTime.atZone(ZoneId.of("Europe/Madrid")).toInstant()
                val endInstant = endDateTime.atZone(ZoneId.of("Europe/Madrid")).toInstant()

                val clases = Clases(
                    id = Firebase.firestore.collection("Clases").document().id, //genera un id unico
                    name = infoClase.name,
                    description = infoClase.description,
                    startTime = Timestamp(Date.from(startInstant)),
                    endTime = Timestamp(Date.from(endInstant)),
                    availablePlaces = plazasDisponibles
                )
                   clasesSemana.add(clases)
               }
            }

        return clasesSemana
    }
    //se obtienen todas las clases disponibles en Firestore
     private fun obtenerClases() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _allClases.value = clasesRepository.obtenerClases()
                _errorMessage.value = null
            } catch (e: Exception){
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    //se obtienen las clases por día desde Firestore, y se ordenan por startTime
    fun obtenerClasesPorDia(selectedDay: LocalDate){
        viewModelScope.launch {
            _isLoading.value = true
            try {
               val allClases = clasesRepository.obtenerClases()
                _clasesPorDia.value = allClases
                    .filter { it.startTime?.toLocalDate() == selectedDay }
                    .distinctBy { Triple(it.name, it.startTime, it.endTime) }
                    .sortedBy { it.startTime?.toDate() }
                _errorMessage.value = null
            } catch (e: Exception){
                _errorMessage.value = "Error al obtener las clases"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun reservarClase(clase: Clases, userId: String) {

        viewModelScope.launch {

            val userRef = Firebase.firestore.collection("Usuarios").document(userId)

            // Si el ID está vacío se genera uno temporal
            val claseId = if (clase.id.isNotEmpty()) clase.id else Firebase.firestore.collection("Reservas").document().id

            val claseConId = clase.copy(id = claseId)

            val reservaRef = userRef.collection("Reservas").document(claseId)

            try {
                val snapshot = reservaRef.get().await()
                //si la reserva existe se borra y se cancela
                if (snapshot.exists()) {
                    reservaRef.delete().await()
                    _clasesReservadas.value = _clasesReservadas.value?.filter { it != claseId }
                } else {
                    //si la reserva no existe se guarda
                    reservaRef.set(claseConId).await()
                    _clasesReservadas.value = (_clasesReservadas.value ?: emptyList()) + claseId
                }

            } catch (e: Exception) {
                _errorMessage.value = "Error al gestionar la reserva"
            }
        }
    }
    //se actualizan las reservas de un usuario ya registrado
    fun cargarClasesYReservas(userId: String, selectedDay: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val allClasesList = clasesRepository.obtenerClases()

                // Filtrar por día seleccionado
                _clasesPorDia.value = allClasesList
                    .filter { it.startTime?.toLocalDate() == selectedDay }
                    .distinctBy { Triple(it.name, it.startTime, it.endTime) }
                    .sortedBy { it.startTime?.toDate() }

                // Cargar reservas del usuario
                val snapshot = Firebase.firestore
                    .collection("Usuarios").document(userId)
                    .collection("Reservas").get().await()

                _clasesReservadas.value = snapshot.documents.mapNotNull { it.id }
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar clases y reservas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}