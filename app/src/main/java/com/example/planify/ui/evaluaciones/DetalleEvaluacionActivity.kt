package com.example.planify.ui.evaluaciones

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.planify.R

class DetalleEvaluacionActivity : AppCompatActivity() {
    private var id = -1
    private lateinit var asignatura: String
    private lateinit var tipo: String
    private lateinit var fecha: String

    // Configurar layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_evaluacion)

        // Obtener datos de la evaluacion
        id = intent.getIntExtra("id", -1)
        asignatura = intent.getStringExtra("asignatura") ?: ""
        tipo = intent.getStringExtra("tipo") ?: ""
        fecha = intent.getStringExtra("fecha") ?: ""

        val tvAsignatura = findViewById<TextView>(R.id.tvAsignaturaDet)
        val tvTipo = findViewById<TextView>(R.id.tvTipoDet)
        val tvFecha = findViewById<TextView>(R.id.tvFechaDet)

        // Mostrar datos de la evaluacion
        tvAsignatura.text = "Asignatura: $asignatura"
        tvTipo.text = "Tipo: $tipo"
        tvFecha.text = "Fecha: $fecha"

        // Boton editar
        findViewById<Button>(R.id.btnEditarEval).setOnClickListener {
            val i = Intent()
            i.putExtra("action", "edit")
            i.putExtra("id", id)
            i.putExtra("asignatura", asignatura)
            i.putExtra("tipo", tipo)
            i.putExtra("fecha", fecha)
            setResult(RESULT_OK, i)
            finish()
        }

        // Boton eliminar
        findViewById<Button>(R.id.btnEliminarEval).setOnClickListener {
            val i = Intent()
            i.putExtra("action", "delete")
            i.putExtra("id", id)
            setResult(RESULT_OK, i)
            finish()
        }
    }
}