package com.example.planify.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.planify.R
import com.example.planify.ui.evaluaciones.EvaluacionesActivity
import com.example.planify.ui.sesiones.SesionesActivity

class MainActivity : AppCompatActivity() {
    // Configurar layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botones de navegacion
        val btnEvaluaciones = findViewById<Button>(R.id.btnEvaluaciones)
        val btnSesiones = findViewById<Button>(R.id.btnSesiones)

        // Navegacion
        btnEvaluaciones.setOnClickListener {
            val intent = Intent(this, EvaluacionesActivity::class.java)
            startActivity(intent)
        }

        // Navegacion
        btnSesiones.setOnClickListener {
            val intent = Intent(this, SesionesActivity::class.java)
            startActivity(intent)
        }
    }
}