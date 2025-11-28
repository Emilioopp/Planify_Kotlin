package com.example.planify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SesionesProximasActivity : AppCompatActivity() {

    private lateinit var viewModel: SesionesProximasViewModel
    private lateinit var adapter: SesionProximaAdapter
    private lateinit var recycler: RecyclerView

    private val detalleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) return@registerForActivityResult
        val data = result.data ?: return@registerForActivityResult
        val action = data.getStringExtra("action") ?: return@registerForActivityResult

        when (action) {

            "delete" -> {
                val sesion = Sesion(
                    data.getStringExtra("nombre") ?: "",
                    data.getStringExtra("fecha") ?: "",
                    data.getStringExtra("hora") ?: "",
                    data.getStringExtra("duracion") ?: ""
                )
                viewModel.eliminarSesion(sesion)
            }

            "edit" -> {
                val original = Sesion(
                    data.getStringExtra("original_nombre") ?: "",
                    data.getStringExtra("original_fecha") ?: "",
                    data.getStringExtra("original_hora") ?: "",
                    data.getStringExtra("original_duracion") ?: ""
                )
                val nueva = Sesion(
                    data.getStringExtra("nombre") ?: "",
                    data.getStringExtra("fecha") ?: "",
                    data.getStringExtra("hora") ?: "",
                    data.getStringExtra("duracion") ?: ""
                )
                viewModel.editarSesion(original, nueva)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesiones_proximas)

        recycler = findViewById(R.id.recyclerSesionesProximas)

        val app = application as PlanifyApp
        viewModel = ViewModelProvider(app).get(SesionesProximasViewModel::class.java)

        adapter = SesionProximaAdapter(listOf()) { sesion ->
            val intent = Intent(this, DetalleSesionActivity::class.java)
            intent.putExtra("nombre", sesion.nombre)
            intent.putExtra("fecha", sesion.fecha)
            intent.putExtra("hora", sesion.hora)
            intent.putExtra("duracion", sesion.duracion)
            intent.putExtra("origen", "proximas")
            detalleLauncher.launch(intent)
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        viewModel.sesionesProximas.observe(this) { lista ->
            adapter.updateData(lista)
        }
    }

    class SesionProximaAdapter(
        private var lista: List<Sesion>,
        private val onItemClick: ((Sesion) -> Unit)? = null
    ) : RecyclerView.Adapter<SesionProximaAdapter.SesionViewHolder>() {

        inner class SesionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nombre: TextView = itemView.findViewById(R.id.txtNombreSesion)
            val fecha: TextView = itemView.findViewById(R.id.txtFechaSesion)
            val hora: TextView = itemView.findViewById(R.id.txtHoraSesion)
            val duracion: TextView = itemView.findViewById(R.id.txtDuracionSesion)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SesionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sesion, parent, false)
            return SesionViewHolder(view)
        }

        override fun onBindViewHolder(holder: SesionViewHolder, position: Int) {
            val sesion = lista[position]
            holder.nombre.text = sesion.nombre
            holder.fecha.text = sesion.fecha
            holder.hora.text = sesion.hora + " hrs"
            holder.duracion.text = sesion.duracion + " min"

            holder.itemView.setOnClickListener {
                onItemClick?.invoke(sesion)
            }
        }

        override fun getItemCount(): Int = lista.size
        fun updateData(nuevaLista: List<Sesion>) {
            lista = nuevaLista
            notifyDataSetChanged()
        }
    }
}
