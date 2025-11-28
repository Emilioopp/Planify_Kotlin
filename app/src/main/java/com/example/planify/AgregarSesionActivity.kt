package com.example.planify

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AgregarSesionActivity : AppCompatActivity() {

    private var modo: String = "crear"
    private var posicion: Int = -1
    private var origen: String = "hoy"

    private lateinit var txtNombre: EditText
    private lateinit var txtFecha: EditText
    private lateinit var txtHora: EditText
    private lateinit var txtDuracion: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_sesion)

        modo = intent.getStringExtra("modo") ?: "crear"
        origen = intent.getStringExtra("origen") ?: "hoy"
        posicion = intent.getIntExtra("posicion", -1)

        txtNombre = findViewById(R.id.etNombre)
        txtFecha = findViewById(R.id.etFecha)
        txtHora = findViewById(R.id.etHora)
        txtDuracion = findViewById(R.id.etDuracion)
        btnGuardar = findViewById(R.id.btnGuardarSesion)

        if (modo == "editar") {
            txtNombre.setText(intent.getStringExtra("nombre") ?: "")
            txtFecha.setText(intent.getStringExtra("fecha") ?: "")
            txtHora.setText(intent.getStringExtra("hora") ?: "")
            txtDuracion.setText(intent.getStringExtra("duracion") ?: "")
        }

        txtFecha.setOnClickListener {
            val c = Calendar.getInstance()
            val dialog = DatePickerDialog(
                this,
                { _, y, m, d -> txtFecha.setText("$d/${m + 1}/$y") },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }

        txtHora.setOnClickListener {
            val c = Calendar.getInstance()
            val dialog = TimePickerDialog(
                this,
                { _, h, min -> txtHora.setText("$h:${min.toString().padStart(2, '0')} hrs") },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            )
            dialog.show()
        }

        btnGuardar.setOnClickListener {
            val nueva = Sesion(
                txtNombre.text.toString(),
                txtFecha.text.toString(),
                txtHora.text.toString(),
                txtDuracion.text.toString()
            )

            if (modo == "crear") {
                val data = intent
                data.putExtra("nombre", nueva.nombre)
                data.putExtra("fecha", nueva.fecha)
                data.putExtra("hora", nueva.hora)
                data.putExtra("duracion", nueva.duracion)
                setResult(Activity.RESULT_OK, data)
                finish()
            } else {
                if (origen == "hoy") {
                    PlanifyApp.sesionesViewModelGlobal.editarSesion(posicion, nueva)
                } else {
                    PlanifyApp.sesionesProximasViewModelGlobal.editarSesion(posicion, nueva)
                }
                finish()
            }
        }
    }
}