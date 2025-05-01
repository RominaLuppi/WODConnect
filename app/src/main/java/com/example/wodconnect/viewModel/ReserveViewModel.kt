package com.example.wodconnect.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wodconnect.data.Class
import com.example.wodconnect.data.getDaysOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.util.Locale



class ReserveViewModel: ViewModel() {
    val daysOfWeek = getDaysOfWeek()
    private val _clases = MutableLiveData(
        listOf(
            Class(1, "CrossFit", "Alta intensidad", "09:00", "10:00", "Lunes"),
            Class(2, "Funcional", "Entrenamiento completo", "10:00", "11:00", "Martes"),
            Class(3, "Yoga", "Estiramiento y relajación", "11:00", "12:00", "Miércoles"),
            Class(4, "CrossFit", "Alta intensidad", "12:00", "13:00", "Jueves"),
            Class(5, "Funcional", "Entrenamiento completo", "14:00", "15:00", "Lunes"),
            Class(6, "Yoga", "Estiramiento y relajación", "16:00", "17:00", "Miércoles"),
            Class(7, "CrossFit", "Alta intensidad", "09:00", "10:00", "Lunes"),
            Class(8, "Funcional", "Entrenamiento completo", "10:00", "11:00", "Martes"),
            Class(9, "Yoga", "Estiramiento y relajación", "11:00", "12:00", "Miércoles"),
            Class(10, "CrossFit", "Alta intensidad", "12:00", "13:00", "Jueves"),
            Class(11, "Funcional", "Entrenamiento completo", "14:00", "15:00", "Martes"),
            Class(12, "Yoga", "Estiramiento y relajación", "16:00", "17:00", "Sábado"),
            Class(13, "Funcional", "Entrenamiento completo", "10:00", "11:00", "Sábado"),
            Class(14, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Viernes"),
            Class(15, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Viernes"),
            Class(16, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Jueves"),
            Class(17, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Domingo"),
            Class(12, "Yoga", "Estiramiento y relajación", "16:00", "17:00", "Sábado"),
            Class(13, "Funcional", "Entrenamiento completo", "10:00", "11:00", "Jueves"),
            Class(14, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Jueves"),
            Class(15, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Jueves"),
            Class(16, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Jueves"),
            Class(17, "Yoga", "Estiramiento y relajación", "10:00", "11:00", "Domingo")
        )
    )
   // val clases: LiveData<List<Class>> = _clases

    private val _clasesForSelectedDay =MutableLiveData<List<Class>>()
    val clasesForSelectedDay: LiveData<List<Class>> = _clasesForSelectedDay

    init {
        setClasesForSelectedDay(LocalDate.now())
    }

    fun setClasesForSelectedDay(fecha: LocalDate){
        val dayOfWeek = fecha.dayOfWeek.getDisplayName(TextStyle.FULL,
            Locale("es")).lowercase()
        _clasesForSelectedDay.value = _clases.value?.filter { it.weekDay?.lowercase() == dayOfWeek } ?: emptyList()

    }

    fun reserverClass(clase: Class) {

    }

}