package com.example.planify.ui.evaluaciones

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
import com.example.planify.model.Evaluacion
import com.example.planify.viewmodel.EvaluacionesViewModel

class EvaluacionesActivity : AppCompatActivity() {
    private lateinit var viewModel: EvaluacionesViewModel
    private lateinit var adapter: EvaluacionAdapter

    // Detalle evaluacion
    private val detalleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        // Si no se selecciono ninguna tarea
        if (result.resultCode != RESULT_OK) return@registerForActivityResult
        val data = result.data ?: return@registerForActivityResult

        // Actualizar lista
        when (data.getStringExtra("action")) {

            // Eliminar evaluacion
            "delete" -> {
                val id = data.getIntExtra("id", -1)
                viewModel.eliminarEvaluacion(Evaluacion(id, "", "", ""))
            }

            // Editar evaluacion
            "edit" -> {
                val intent = Intent(this, AgregarEvaluacionActivity::class.java)
                intent.putExtra("action", "edit")
                intent.putExtra("id", data.getIntExtra("id", -1))
                intent.putExtra("asignatura", data.getStringExtra("asignatura"))
                intent.putExtra("tipo", data.getStringExtra("tipo"))
                intent.putExtra("fecha", data.getStringExtra("fecha"))
                agregarLauncher.launch(intent)
            }
        }
    }

    // Agregar evaluacion
    private val agregarLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Si no se selecciono ninguna tarea
        if (result.resultCode != RESULT_OK) return@registerForActivityResult
        val data = result.data ?: return@registerForActivityResult

        // Obtener los datos de la tarea
        val action = data.getStringExtra("action") ?: return@registerForActivityResult

        // Crear la tarea
        val id = data.getIntExtra("id", -1)
        val asignatura = data.getStringExtra("asignatura") ?: ""
        val tipo = data.getStringExtra("tipo") ?: ""
        val fecha = data.getStringExtra("fecha") ?: ""

        // Actualizar lista
        val nueva = Evaluacion(id, asignatura, tipo, fecha)
        when (action) {
            "create" -> viewModel.agregarEvaluacion(nueva)
            "edit" -> viewModel.editarEvaluacion(Evaluacion(id, "", "", ""), nueva)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluaciones)

        // Configurar ViewModel
        val app = application as PlanifyApp
        viewModel = ViewModelProvider(app).get(EvaluacionesViewModel::class.java)

        // Configurar RecyclerView
        val recycler: RecyclerView = findViewById(R.id.recyclerEvaluaciones)
        val btnAgregar: Button = findViewById(R.id.btnAgregarEvaluacion)

        // Configurar adapter
        adapter = EvaluacionAdapter(listOf()) { evaluacion ->
            val intent = Intent(this, DetalleEvaluacionActivity::class.java)
            intent.putExtra("id", evaluacion.id)
            intent.putExtra("asignatura", evaluacion.asignatura)
            intent.putExtra("tipo", evaluacion.tipo)
            intent.putExtra("fecha", evaluacion.fecha)
            detalleLauncher.launch(intent)
        }

        // Configurar RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        // Actualizar lista
        viewModel.evaluaciones.observe(this) { lista ->
            adapter.update(lista)
        }

        // Agregar evaluacion
        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarEvaluacionActivity::class.java)
            intent.putExtra("action", "create")
            agregarLauncher.launch(intent)
        }
    }
}