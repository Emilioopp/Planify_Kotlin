package com.example.planify

import java.text.SimpleDateFormat
import java.util.*

object FechaUtils {

    private val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun hoy(): Date {
        return formato.parse(formato.format(Date()))!!
    }

    fun esDespues(fechaStr: String, fechaComparar: Date): Boolean {
        return try {
            val fecha = formato.parse(fechaStr)
            fecha != null && fecha.after(fechaComparar)
        } catch (_: Exception) {
            false
        }
    }
}