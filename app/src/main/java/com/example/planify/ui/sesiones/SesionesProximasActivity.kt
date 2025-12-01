package com.example.planify.ui.sesiones

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.PlanifyApp
import com.example.planify.R
import com.example.planify.ui.sesiones.SesionProximaAdapter
import com.example.planify.model.Sesion
import com.example.planify.viewmodel.SesionesViewModel

class SesionesProximasActivity : AppCompatActivity() {
    private lateinit var viewModel: SesionesViewModel
    private lateinit var adapter: SesionProximaAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var btnSeleccionarFecha: Button
    private var fechaSeleccionada: String = ""

    private val detalleLauncher = registerForActivityResult( // Detalle sesion
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode != RESULT_OK) return@registerForActivityResult

        // Obtener los datos de la tarea
        val data = result.data ?: return@registerForActivityResult
        val action = data.getStringExtra("action") ?: return@registerForActivityResult


        when (action) { // Actualizar lista

            // Eliminar sesion
            "delete" -> {
                val sesion = Sesion(
                    data.getIntExtra("id", -1),
                    data.getStringExtra("nombre") ?: "",
                    data.getStringExtra("fecha") ?: "",
                    data.getStringExtra("hora") ?: "",
                    data.getStringExtra("duracion") ?: ""
                )
                viewModel.eliminarSesion(sesion)
            }

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

                // Actualizar lista
                val app = application as PlanifyApp
                app.moverSesionSiCambiaDeCategoria(original, nueva)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesiones_proximas)

        // Configurar RecyclerView
        recycler = findViewById(R.id.recyclerSesionesProximas)
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha)

        // Configurar ViewModel
        val app = application as PlanifyApp
        viewModel = ViewModelProvider(app).get(SesionesViewModel::class.java)

        // Configurar adapter
        adapter = SesionProximaAdapter(listOf()) { sesion ->
            val intent = Intent(this, DetalleSesionActivity::class.java)
            intent.putExtra("id", sesion.id)
            intent.putExtra("nombre", sesion.nombre)
            intent.putExtra("fecha", sesion.fecha)
            intent.putExtra("hora", sesion.hora)
            intent.putExtra("duracion", sesion.duracion)
            intent.putExtra("origen", "proximas")

            detalleLauncher.launch(intent)
        }

        // Configurar RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        // Actualizar lista de sesiones
        viewModel.sesiones.observe(this) { lista ->
            val filtradas = if (fechaSeleccionada.isNotEmpty()) {
                lista.filter { it.fecha == fechaSeleccionada }
            } else lista

            adapter.update(filtradas)
        }

        // Filtro por Fecha
        btnSeleccionarFecha.setOnClickListener {
            val dialog = DatePickerDialog(
                this,
                { _, year, month, day ->

                    // Formatear la fecha
                    fechaSeleccionada = "%02d/%02d/%04d".format(day, month + 1, year)

                    // Mostrar la fecha seleccionada
                    btnSeleccionarFecha.text = "Fecha seleccionada: $fechaSeleccionada"

                    // Actualizar lista con las sesiones de la fecha seleccionada
                    val filtradas = viewModel.sesiones.value
                        ?.filter { it.fecha == fechaSeleccionada }
                        ?: listOf()

                    adapter.update(filtradas)
                },
                2025, 0, 1
            )
            dialog.show()
        }
    }
}