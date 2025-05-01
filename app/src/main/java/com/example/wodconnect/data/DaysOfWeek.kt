package com.example.wodconnect.data

import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.time.DayOfWeek
import java.util.Locale


data class DaysOfWeek(
    val diaDelMes: String,
    val fecha: LocalDate
)

fun getDaysOfWeek(): List<DaysOfWeek>{
    val today = LocalDate.now()

    //se obtiene el 1º dia del proximo mes
    val firstDayNextMonth = today.plusMonths(1).withDayOfMonth(1)

    //se calcula el 1º domingo del mes siguiente
    val firstSunday = firstDayNextMonth.plusDays((7 - firstDayNextMonth.dayOfWeek.value) % 7L)

    //lista de dias desde hoy hasta el 1º domingo del proximo mes
    val daysToSunday = mutableListOf<DaysOfWeek>()
    var hoy = today

    while (hoy.isBefore(firstSunday) || hoy.isEqual(firstSunday)){
        val dayName = hoy.dayOfWeek
            .getDisplayName(TextStyle.SHORT, Locale("es"))
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val day = hoy.dayOfMonth.toString()
        val month = hoy.month.getDisplayName(TextStyle.SHORT, Locale("es"))
            .uppercase()

        daysToSunday.add(DaysOfWeek(diaDelMes = "$dayName $day $month", fecha = hoy ))
        hoy = hoy.plusDays(1)
    }
    return daysToSunday

}
