package com.example.planify.ui.sesiones

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.planify.R

class DetalleSesionActivity : AppCompatActivity() {
    private var idOriginal = -1
    private var nombreOriginal = ""
    private var fechaOriginal = ""
    private var horaOriginal = ""
    private var duracionOriginal = ""
    private var origen = ""

    private val editarLauncher = registerForActivityResult( // Editar sesion
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Si no se selecciono ninguna tarea
        if (result.resultCode == RESULT_OK) {
            // Obtener los datos de la tarea
            val data = result.data ?: return@registerForActivityResult

            val resultIntent = Intent()

            // Actualizar los datos de la tarea
            resultIntent.putExtra("action", "edit")
            resultIntent.putExtra("id", idOriginal)
            resultIntent.putExtra("original_nombre", nombreOriginal)
            resultIntent.putExtra("original_fecha", fechaOriginal)
            resultIntent.putExtra("original_hora", horaOriginal)
            resultIntent.putExtra("original_duracion", duracionOriginal)

            resultIntent.putExtra("nombre", data.getStringExtra("nombre"))
            resultIntent.putExtra("fecha", data.getStringExtra("fecha"))
            resultIntent.putExtra("hora", data.getStringExtra("hora"))
            resultIntent.putExtra("duracion", data.getStringExtra("duracion"))

            resultIntent.putExtra("origen", origen)

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_sesion)

        // Obtener datos de la sesion
        idOriginal = intent.getIntExtra("id", -1)
        nombreOriginal = intent.getStringExtra("nombre") ?: ""
        fechaOriginal = intent.getStringExtra("fecha") ?: ""
        horaOriginal = intent.getStringExtra("hora") ?: ""
        duracionOriginal = intent.getStringExtra("duracion") ?: ""
        origen = intent.getStringExtra("origen") ?: ""

        val txtNombre: TextView = findViewById(R.id.tvDetalleNombre)
        val txtFecha: TextView = findViewById(R.id.tvDetalleFecha)
        val txtHora: TextView = findViewById(R.id.tvDetalleHora)
        val txtDuracion: TextView = findViewById(R.id.tvDetalleDuracion)

        val btnEditar: Button = findViewById(R.id.btnEditarSesion)
        val btnEliminar: Button = findViewById(R.id.btnEliminarSesion)

        // Mostrar datos de la sesion
        txtNombre.text = nombreOriginal
        txtFecha.text = fechaOriginal
        txtHora.text = horaOriginal
        txtDuracion.text = "$duracionOriginal min"

        // Botones de editar y eliminar
        btnEditar.setOnClickListener {
            val intent = Intent(this, AgregarSesionActivity::class.java).apply {
                putExtra("action", "edit")
                putExtra("id", idOriginal)
                putExtra("nombre", nombreOriginal)
                putExtra("fecha", fechaOriginal)
                putExtra("hora", horaOriginal)
                putExtra("duracion", duracionOriginal)
                putExtra("origen", origen)
            }
            editarLauncher.launch(intent)
        }

        btnEliminar.setOnClickListener {
            val result = Intent().apply {
                putExtra("action", "delete")
                putExtra("id", idOriginal)
                putExtra("nombre", nombreOriginal)
                putExtra("fecha", fechaOriginal)
                putExtra("hora", horaOriginal)
                putExtra("duracion", duracionOriginal)
                putExtra("origen", origen)
            }
            setResult(RESULT_OK, result)
            finish()
        }
    }
}