package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEvaluaciones = findViewById<Button>(R.id.btnEvaluaciones)
        val btnSesiones = findViewById<Button>(R.id.btnSesiones)

        btnEvaluaciones.setOnClickListener {
            val intent = Intent(this, EvaluacionesActivity::class.java)
            startActivity(intent)
        }

        btnSesiones.setOnClickListener {
            val intent = Intent(this, SesionesActivity::class.java)
            startActivity(intent)
        }
    }
}