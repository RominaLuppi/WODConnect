package com.example.wodconnect.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wodconnect.data.Clases
import com.example.wodconnect.data.getDaysOfWeek
import com.example.wodconnect.modelo.Workout
import com.example.wodconnect.modelo.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel

class ReserveViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
): ViewModel() {

    val daysOfWeek = getDaysOfWeek()

    private val _clases = MutableLiveData(
        listOf(
            Clases(1, "CrossFit", "Alta intensidad", "09:00", "10:00", "Lunes"),
            Clases(2, "Funcional", "Entrenamiento completo", "10:00", "11:00", "Martes"),
            Clases(3, "Yoga", "Estiramiento y relajación", "11:00", "12:00", "Miércoles"),
            Clases(4, "CrossFit", "Alta intensidad", "12:00", "13:00", "Jueves"),
            Clases(5, "Funcional", "Entrenamiento completo", "14:00", "15:00", "Lunes"),
            Clases(6, "Yoga", "Estiramiento y relajación", "16:00", "17:00", "Miércoles"),
            Clases(7, "CrossFit", "Alta intensidad", "09:00", "10:00", "Lunes"),
            Clases(8, "Funcional", "Entrenamiento completo", "10:00", "11:00", "Martes"),
            Clases(9, "Yoga", "Estiramiento y relajación", "11:00", "12:00", "Miércoles"),
            Clases(10, "CrossFit", "Alta intensidad", "12:00", "13:00", "Jueves"),
            Clases(11, "Funcional", "Entrenamiento completo", "14:00", "15:00", "Martes"),
            Clases(12, "Yoga", "Estiramiento y relajación", "16:00", "17:00", "Sábado"),
            Clases(13, "Funcional", "Entrenamiento completo", "10:00", "11:00", "Sábado"),
            Clases(14, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Viernes"),
            Clases(15, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Viernes"),
            Clases(16, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Jueves"),
            Clases(17, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Domingo"),
            Clases(12, "Yoga", "Estiramiento y relajación", "16:00", "17:00", "Sábado"),
            Clases(13, "Funcional", "Entrenamiento completo", "10:00", "11:00", "Jueves"),
            Clases(14, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Jueves"),
            Clases(15, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Jueves"),
            Clases(16, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Jueves"),
            Clases(17, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Domingo")
        )
    )

    private val _clasesForSelectedDay =MutableLiveData<List<Clases>>()
    val clasesForSelectedDay: LiveData<List<Clases>> = _clasesForSelectedDay


    init {
        setClasesForSelectedDay(LocalDate.now())
    }

    fun setClasesForSelectedDay(fecha: LocalDate){
        val dayOfWeek = fecha.dayOfWeek.getDisplayName(TextStyle.FULL,
            Locale("es")).lowercase()
        _clasesForSelectedDay.value = _clases.value?.filter { it.weekDay?.lowercase() == dayOfWeek } ?: emptyList()

    }

    fun reserverClass(selectedClass: Clases) {

    }

}