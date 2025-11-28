package com.example.planify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SesionesViewModel : ViewModel() {

    private val _sesiones = MutableLiveData<MutableList<Sesion>>(mutableListOf())
    val sesiones: LiveData<MutableList<Sesion>> get() = _sesiones

    fun agregarSesion(sesion: Sesion) {
        _sesiones.value?.add(sesion)
        _sesiones.value = _sesiones.value
    }

    fun eliminarSesion(sesion: Sesion) {
        _sesiones.value?.remove(sesion)
        _sesiones.value = _sesiones.value
    }

    fun editarSesion(original: Sesion, nueva: Sesion) {
        val lista = _sesiones.value ?: return

        val index = lista.indexOfFirst {
            it.nombre == original.nombre &&
                    it.fecha == original.fecha &&
                    it.hora == original.hora &&
                    it.duracion == original.duracion
        }

        if (index != -1) {
            lista[index] = nueva
            _sesiones.value = lista
        }
    }
}