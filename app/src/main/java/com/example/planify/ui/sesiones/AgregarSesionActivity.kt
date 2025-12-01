package com.example.planify.ui.sesiones

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.planify.R
import java.util.Calendar

class AgregarSesionActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var btnFecha: Button
    private lateinit var btnHora: Button
    private lateinit var txtDuracion: EditText
    private lateinit var btnGuardar: Button
    private lateinit var titulo: TextView
    private var modo = "create"
    private var fechaElegida = ""
    private var horaElegida = ""
    private var origen = "hoy"

    override fun onCreate(savedInstanceState: Bundle?) {
        // Configurar layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_sesion)
        txtNombre = findViewById(R.id.etNombre)
        btnFecha = findViewById(R.id.etFecha)
        btnHora = findViewById(R.id.etHora)
        txtDuracion = findViewById(R.id.etDuracion)
        btnGuardar = findViewById(R.id.btnGuardarSesion)
        titulo = findViewById(R.id.tvTituloForm)
        modo = intent.getStringExtra("action") ?: "create"
        origen = intent.getStringExtra("origen") ?: "hoy"

        // Si es una edicion
        if (modo == "edit") {
            // Obtener datos de la sesion
            txtNombre.setText(intent.getStringExtra("nombre") ?: "")
            fechaElegida = intent.getStringExtra("fecha") ?: ""
            horaElegida = intent.getStringExtra("hora") ?: ""
            txtDuracion.setText(intent.getStringExtra("duracion") ?: "")
            titulo.text = "Actualizar datos de sesión"
            btnFecha.text = fechaElegida
            btnHora.text = horaElegida
        }

        // Configurar boton de fecha
        btnFecha.setOnClickListener {
            val c = Calendar.getInstance()
            val dp = DatePickerDialog(
                this,
                { _, y, m, d ->
                    fechaElegida = "%02d/%02d/%04d".format(d, m + 1, y)
                    btnFecha.text = fechaElegida
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            dp.show()
        }

        // Configurar boton de hora
        btnHora.setOnClickListener {
            val c = Calendar.getInstance()
            val tp = TimePickerDialog(
                this,
                { _, h, m ->
                    horaElegida = "%02d:%02d".format(h, m)
                    btnHora.text = horaElegida
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            )
            tp.show()
        }

        // Configurar boton de guardar
        btnGuardar.setOnClickListener {

            val result = Intent()

            result.putExtra("action", modo)
            result.putExtra("nombre", txtNombre.text.toString())
            result.putExtra("fecha", fechaElegida)
            result.putExtra("hora", horaElegida)
            result.putExtra("duracion", txtDuracion.text.toString())
            result.putExtra("origen", origen)

            if (fechaElegida.isBlank()) fechaElegida = intent.getStringExtra("fecha") ?: ""
            if (horaElegida.isBlank()) horaElegida = intent.getStringExtra("hora") ?: ""

            setResult(RESULT_OK, result)
            finish()
        }
    }
}