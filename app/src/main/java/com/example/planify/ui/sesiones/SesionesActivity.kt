package com.example.planify.ui.sesiones

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.utils.FechaUtils
import com.example.planify.PlanifyApp
import com.example.planify.R
import com.example.planify.ui.sesiones.SesionAdapter
import com.example.planify.ui.sesiones.SesionesProximasActivity
import com.example.planify.model.Sesion
import com.example.planify.viewmodel.SesionesViewModel

class SesionesActivity : AppCompatActivity() {

    private lateinit var viewModel: SesionesViewModel
    private lateinit var adapter: SesionAdapter

    private val detalleLauncher = registerForActivityResult( // Detalle sesion
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        // Si no se selecciono ninguna tarea, no hacer nada
        if (result.resultCode != RESULT_OK) return@registerForActivityResult

        // Obtener los datos de la tarea
        val data = result.data ?: return@registerForActivityResult
        val action = data.getStringExtra("action") ?: return@registerForActivityResult

        when (action) { // Actualizar lista
            // Editar sesion
            "edit" -> {
                val original = Sesion(
                    data.getIntExtra("id", -1),
                    data.getStringExtra("original_nombre") ?: "",
                    data.getStringExtra("original_fecha") ?: "",
                    data.getStringExtra("original_hora") ?: "",
                    data.getStringExtra("original_duracion") ?: ""
                )

                // Crear la sesion
                val nueva = Sesion(
                    original.id,    // <- importante!
                    data.getStringExtra("nombre") ?: "",
                    data.getStringExtra("fecha") ?: "",
                    data.getStringExtra("hora") ?: "",
                    data.getStringExtra("duracion") ?: ""
                )

                // Actualizar lista solo si el nombre no esta vacio
                if (nueva.nombre.isNotBlank()) {
                    viewModel.editarSesion(original, nueva)
                }
            }

            // Eliminar sesion
            "delete" -> {
                val original = Sesion(
                    data.getIntExtra("id", -1),
                    data.getStringExtra("original_nombre") ?: "",
                    data.getStringExtra("original_fecha") ?: "",
                    data.getStringExtra("original_hora") ?: "",
                    data.getStringExtra("original_duracion") ?: ""
                )
                viewModel.eliminarSesion(original)
            }
        }
    }

    private val agregarLauncher = registerForActivityResult( // Agregar sesion
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        // Si no se selecciono ninguna tarea, no hacer nada
        if (result.resultCode != RESULT_OK) return@registerForActivityResult
        val data = result.data ?: return@registerForActivityResult

        // Crear la sesion
        val sesion = Sesion(
            id = 0,
            nombre = data.getStringExtra("nombre") ?: "",
            fecha = data.getStringExtra("fecha") ?: "",
            hora = data.getStringExtra("hora") ?: "",
            duracion = data.getStringExtra("duracion") ?: ""
        )

        // Actualizar lista solo si el nombre no esta vacio
        if (sesion.nombre.isNotBlank()) {
            viewModel.agregarSesion(sesion)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesiones)

        // Configurar ViewModel
        val app = application as PlanifyApp
        viewModel = ViewModelProvider(app).get(SesionesViewModel::class.java)

        // Configurar RecyclerView
        val recycler: RecyclerView = findViewById(R.id.recyclerSesiones)
        val btnAgregar: Button = findViewById(R.id.btnAgregarSesion)
        val btnVerProximas: Button = findViewById(R.id.btnVerProximas)

        // Configurar adapter
        adapter = SesionAdapter(listOf()) { sesion, pos ->
            val intent = Intent(this, DetalleSesionActivity::class.java)
            intent.putExtra("id", sesion.id)
            intent.putExtra("nombre", sesion.nombre)
            intent.putExtra("fecha", sesion.fecha)
            intent.putExtra("hora", sesion.hora)
            intent.putExtra("duracion", sesion.duracion)
            intent.putExtra("origen", "hoy")
            detalleLauncher.launch(intent)
        }

        // Configurar RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        // Actualizar lista
        viewModel.sesiones.observe(this) { lista ->
            val hoy = FechaUtils.formatearHoy()
            adapter.updateData(lista.filter { it.fecha == hoy })
        }

        // Agregar sesion
        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarSesionActivity::class.java)
            intent.putExtra("action", "create")
            intent.putExtra("origen", "hoy")
            agregarLauncher.launch(intent)
        }

        // Ver sesiones proximas
        btnVerProximas.setOnClickListener {
            startActivity(Intent(this, SesionesProximasActivity::class.java))
        }
    }
}