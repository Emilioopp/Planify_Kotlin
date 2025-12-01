package com.example.planify.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FechaUtils {
    private val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Obtener fecha actual
    fun hoy(): Date {
        return formato.parse(formato.format(Date()))!!
    }

    // Si la fecha es despues de la fecha actual
    fun esDespues(fechaStr: String, fechaComparar: Date): Boolean {
        return try {
            val fecha = formato.parse(fechaStr)
            fecha != null && fecha.after(fechaComparar)
        } catch (_: Exception) {
            false
        }
    }
    fun formatearHoy(): String {
        // Obtener fecha actual
        val hoy = Date()
        return formato.format(Date())
    }


    fun convertirFechaAKey(fecha: String): Int {
        // Dividir la fecha en partes
        val partes = fecha.split("/")

        if (partes.size != 3) return Int.MAX_VALUE

        val dia = partes[0].toIntOrNull() ?: 0
        val mes = partes[1].toIntOrNull() ?: 0
        val año = partes[2].toIntOrNull() ?: 0

        return año * 10000 + mes * 100 + dia
    }
}