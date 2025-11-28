package com.example.planify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SesionesProximasViewModel : ViewModel() {

    private val _sesionesProximas = MutableLiveData<MutableList<Sesion>>(mutableListOf())
    val sesionesProximas: LiveData<MutableList<Sesion>> get() = _sesionesProximas

    fun agregarSesion(sesion: Sesion) {
        _sesionesProximas.value?.add(sesion)
        _sesionesProximas.value = _sesionesProximas.value
    }

    fun eliminarSesion(sesion: Sesion) {
        _sesionesProximas.value?.remove(sesion)
        _sesionesProximas.value = _sesionesProximas.value
    }

    fun editarSesion(original: Sesion, nueva: Sesion) {
        val lista = _sesionesProximas.value ?: return

        val index = lista.indexOfFirst {
            it.nombre == original.nombre &&
                    it.fecha == original.fecha &&
                    it.hora == original.hora &&
                    it.duracion == original.duracion
        }

        if (index != -1) {
            lista[index] = nueva
            _sesionesProximas.value = lista
        }
    }
}
