package com.example.planify

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.planify.model.Sesion
import com.example.planify.utils.FechaUtils
import com.example.planify.viewmodel.SesionesViewModel

class PlanifyApp : Application(), ViewModelStoreOwner {
    private lateinit var appViewModelStore: ViewModelStore
    lateinit var sesionesViewModel: SesionesViewModel

    override fun onCreate() {
        super.onCreate()

        // Crear el ViewModelStore para la aplicación
        appViewModelStore = ViewModelStore()

        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)

        // Crear la instancia del ViewModel
        sesionesViewModel =
            ViewModelProvider(this, factory).get(SesionesViewModel::class.java)

        instancia = this
    }

    override val viewModelStore: ViewModelStore // Proporcionar el ViewModelStore
        get() = appViewModelStore

    companion object {
        lateinit var instancia: PlanifyApp // Instancia de la aplicación
            private set

        val sesionesViewModelGlobal: SesionesViewModel // Acceder al ViewModel desde otras clases
            get() = instancia.sesionesViewModel
    }

    fun moverSesionSiCambiaDeCategoria(original: Sesion, nueva: Sesion) {
        val hoy = FechaUtils.formatearHoy()
        val esOriginalHoy = original.fecha == hoy
        val esNuevaHoy = nueva.fecha == hoy
        val vm = sesionesViewModelGlobal

        vm.eliminarSesion(original) // Eliminar la sesion original

        vm.agregarSesion(nueva) // Agregar la sesion nueva
    }
}