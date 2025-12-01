package com.example.planify.ui.sesiones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.R
import com.example.planify.model.Sesion

class SesionProximaAdapter(
    private var lista: List<Sesion>,
    private val onItemClick: (Sesion) -> Unit
) : RecyclerView.Adapter<SesionProximaAdapter.ViewHolder>() {

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) { // ViewHolder
        val nombre: TextView = item.findViewById(R.id.tvNombreSesionProxima)
        val fecha: TextView = item.findViewById(R.id.tvFechaSesionProxima)
        val info: TextView = item.findViewById(R.id.tvInfoSesionProxima)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sesion_proxima, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // Actualizar vista
        val s = lista[position]

        // Mostrar datos de la sesion
        holder.nombre.text = s.nombre
        holder.fecha.text = s.fecha
        holder.info.text = "Duración: ${s.duracion} min — ${s.hora} hrs"

        holder.itemView.setOnClickListener { onItemClick(s) }
    }

    override fun getItemCount(): Int = lista.size // Cantidad de elementos

    fun update(nueva: List<Sesion>) { // Actualizar lista
        lista = nueva
        notifyDataSetChanged()
    }
}