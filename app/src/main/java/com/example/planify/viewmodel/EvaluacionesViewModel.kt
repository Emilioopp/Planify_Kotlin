package com.example.planify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planify.model.Evaluacion
import com.example.planify.utils.FechaUtils

class EvaluacionesViewModel : ViewModel() {
    private val _evaluaciones = MutableLiveData<MutableList<Evaluacion>>(mutableListOf())
    val evaluaciones: LiveData<MutableList<Evaluacion>> get() = _evaluaciones
    private var ultimoId = 1

    fun agregarEvaluacion(e: Evaluacion) { // Agregar evaluacion
        e.id = ultimoId++
        val lista = _evaluaciones.value!!
        lista.add(e)
        ordenarPorFecha(lista)
        _evaluaciones.value = lista
    }

    fun eliminarEvaluacion(e: Evaluacion) { // Eliminar evaluacion
        val lista = _evaluaciones.value!!
        lista.removeIf { it.id == e.id }
        _evaluaciones.value = lista
    }

    fun editarEvaluacion(original: Evaluacion, nueva: Evaluacion) { // Editar evaluacion
        val lista = _evaluaciones.value!!
        val index = lista.indexOfFirst { it.id == original.id }
        if (index >= 0) {
            lista[index] = nueva
            ordenarPorFecha(lista)
            _evaluaciones.value = lista
        }
    }
    private fun ordenarPorFecha(lista: MutableList<Evaluacion>) { // Ordenar por fecha
        lista.sortBy { FechaUtils.convertirFechaAKey(it.fecha) }
    }
}