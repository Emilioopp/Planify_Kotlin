package com.example.planify.ui.evaluaciones

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.planify.R
import java.util.*

class AgregarEvaluacionActivity : AppCompatActivity() {
    private lateinit var edtAsignatura: EditText
    private lateinit var edtTipo: EditText
    private lateinit var edtFecha: EditText
    private lateinit var btnGuardar: Button
    private var idEval = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_evaluacion)

        // Configurar layout
        edtAsignatura = findViewById(R.id.edtAsignaturaEval)
        edtTipo = findViewById(R.id.edtTipoEval)
        edtFecha = findViewById(R.id.edtFechaEval)
        btnGuardar = findViewById(R.id.btnGuardarEval)

        // Configurar titulo
        val titulo = findViewById<android.widget.TextView>(R.id.tvTituloEvalForm)

        val action = intent.getStringExtra("action") ?: "create"

        // Si es una edicion
        if (action == "edit") {
            titulo.text = "Actualizar evaluación"
            // Obtener datos de la evaluacion
            idEval = intent.getIntExtra("id", -1)
            edtAsignatura.setText(intent.getStringExtra("asignatura"))
            edtTipo.setText(intent.getStringExtra("tipo"))
            edtFecha.setText(intent.getStringExtra("fecha"))
        }

        // Configurar boton de fecha
        edtFecha.setOnClickListener { abrirDatePicker() }

        // Configurar boton de guardar
        btnGuardar.setOnClickListener {
            val result = Intent()
            result.putExtra("action", action)
            result.putExtra("id", idEval)
            result.putExtra("asignatura", edtAsignatura.text.toString())
            result.putExtra("tipo", edtTipo.text.toString())
            result.putExtra("fecha", edtFecha.text.toString())

            setResult(RESULT_OK, result)
            finish()
        }
    }

    // Configurar boton de fecha
    private fun abrirDatePicker() {
        val c = Calendar.getInstance()
        val dp = DatePickerDialog(
            this,
            { _, y, m, d ->
                val fecha = "%02d/%02d/%04d".format(d, m + 1, y)
                edtFecha.setText(fecha)
            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        )
        dp.show()
    }
}