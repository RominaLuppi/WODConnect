package com.example.wodconnect.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wodconnect.data.Clases
import com.example.wodconnect.data.getDaysOfWeek
import com.example.wodconnect.data.horariosPorDia
import com.example.wodconnect.data.toLocalDate
import com.example.wodconnect.modelo.repositories.ClasesRepository
import com.google.firebase.Timestamp
import com.google.type.DateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel

class   ReserveViewModel @Inject constructor(
    private val clasesRepository: ClasesRepository
): ViewModel() {

    private val _allClases = MutableLiveData<List<Clases>>()
    val allClases: LiveData<List<Clases>> = _allClases

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _clasesPorDia = MutableLiveData<List<Clases>>()
    val clasesPorDia: LiveData<List<Clases>> = _clasesPorDia

    private val _clasesSemGeneradas = MutableLiveData<List<Clases>>()
    val clasesSemGeneradas: LiveData<List<Clases>> = _clasesSemGeneradas

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
                Log.d("ReserveViewModel", "Generadas ${clases.size} clases")
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
                    val startDateTime = dia.fecha.atTime(infoClase.third.first)
                    val endDayTime = dia.fecha.atTime(infoClase.third.second)

                   val clases = Clases(
                        name = infoClase.first,
                        description = infoClase.second,
                        startTime = Timestamp(Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant())),
                        endTime = Timestamp(Date.from(endDayTime.atZone(ZoneId.systemDefault()).toInstant())),
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
//                val allClases = _allClases.value ?: emptyList()
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




}