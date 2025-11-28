package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleSesionActivity : AppCompatActivity() {

    private lateinit var sesion: Sesion
    private var posicion: Int = -1
    private var origen: String = "hoy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_sesion)

        sesion = Sesion(
            intent.getStringExtra("nombre") ?: "",
            intent.getStringExtra("fecha") ?: "",
            intent.getStringExtra("hora") ?: "",
            intent.getStringExtra("duracion") ?: ""
        )

        posicion = intent.getIntExtra("posicion", -1)
        origen = intent.getStringExtra("origen") ?: "hoy"

        val txtNombre = findViewById<TextView>(R.id.tvDetalleNombre)
        val txtFecha = findViewById<TextView>(R.id.tvDetalleFecha)
        val txtHora = findViewById<TextView>(R.id.tvDetalleHora)
        val txtDuracion = findViewById<TextView>(R.id.tvDetalleDuracion)

        val btnEditar = findViewById<Button>(R.id.btnEditarSesion)
        val btnEliminar = findViewById<Button>(R.id.btnEliminarSesion)

        txtNombre.text = sesion.nombre
        txtFecha.text = "Fecha: ${sesion.fecha}"
        txtHora.text = "Hora: ${sesion.hora}"
        txtDuracion.text = "Duración: ${sesion.duracion} min"

        btnEditar.setOnClickListener {
            val intent = Intent(this, AgregarSesionActivity::class.java)
            intent.putExtra("modo", "editar")
            intent.putExtra("origen", origen)
            intent.putExtra("posicion", posicion)
            intent.putExtra("nombre", sesion.nombre)
            intent.putExtra("fecha", sesion.fecha)
            intent.putExtra("hora", sesion.hora)
            intent.putExtra("duracion", sesion.duracion)
            startActivity(intent)
        }

        btnEliminar.setOnClickListener {
            if (origen == "hoy") {
                PlanifyApp.sesionesViewModelGlobal.eliminarSesion(posicion)
            } else {
                PlanifyApp.sesionesProximasViewModelGlobal.eliminarSesion(posicion)
            }
            finish()
        }
    }
}