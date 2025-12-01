package com.example.planify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planify.model.Sesion

class SesionesViewModel : ViewModel() {
    private var ultimoId = 1
    private val _sesiones = MutableLiveData<MutableList<Sesion>>(mutableListOf())
    val sesiones: LiveData<MutableList<Sesion>> get() = _sesiones

    fun agregarSesion(sesion: Sesion) { // Agregar sesion
        sesion.id = ultimoId++

        // Agregar la sesion a la lista
        val lista = _sesiones.value ?: mutableListOf()
        lista.add(sesion)
        _sesiones.value = lista
    }

    fun eliminarSesion(original: Sesion) { // Eliminar sesion
        val lista = _sesiones.value ?: return
        val index = lista.indexOfFirst { it.id == original.id }

        // Si la sesion existe en la lista
        if (index >= 0) {
            lista.removeAt(index)
            _sesiones.value = lista
        }
    }

    fun editarSesion(original: Sesion, nueva: Sesion) { // Editar sesion
        val lista = _sesiones.value ?: return
        val index = lista.indexOfFirst { it.id == original.id }

        // Si la sesion existe en la lista
        if (index >= 0) {
            lista[index] = nueva
            _sesiones.value = lista
        }
    }
}