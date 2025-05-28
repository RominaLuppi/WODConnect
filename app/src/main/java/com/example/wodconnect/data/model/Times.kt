package com.example.wodconnect.data.model

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId

//funcion para convertir Timestamp a LocalDate
fun Timestamp.toLocalDate(): LocalDate{
    return this.toDate()
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}