package com.example.planify

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewModelProvider

class PlanifyApp : Application(), ViewModelStoreOwner {

    private lateinit var appViewModelStore: ViewModelStore
    lateinit var sesionesViewModel: SesionesViewModel
    lateinit var sesionesProximasViewModel: SesionesProximasViewModel

    override fun onCreate() {
        super.onCreate()

        appViewModelStore = ViewModelStore()

        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)

        sesionesViewModel =
            ViewModelProvider(this, factory).get(SesionesViewModel::class.java)

        sesionesProximasViewModel =
            ViewModelProvider(this, factory).get(SesionesProximasViewModel::class.java)

        instancia = this
    }

    override val viewModelStore: ViewModelStore
        get() = appViewModelStore

    companion object {
        lateinit var instancia: PlanifyApp
            private set

        val sesionesViewModelGlobal: SesionesViewModel
            get() = instancia.sesionesViewModel

        val sesionesProximasViewModelGlobal: SesionesProximasViewModel
            get() = instancia.sesionesProximasViewModel
    }
}