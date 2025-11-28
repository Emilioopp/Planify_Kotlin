package com.example.planify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SesionAdapterHoy(
    private var lista: List<Sesion>,
    private val onItemClick: (Sesion) -> Unit
) : RecyclerView.Adapter<SesionAdapterHoy.ViewHolder>() {

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val nombre: TextView = item.findViewById(R.id.txtNombreSesionHoy)
        val info: TextView = item.findViewById(R.id.txtInfoSesionHoy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sesion_hoy, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s = lista[position]

        holder.nombre.text = s.nombre
        holder.info.text = "Duración: ${s.duracion} min — ${s.hora} hrs"

        holder.itemView.setOnClickListener { onItemClick(s) }
    }

    override fun getItemCount(): Int = lista.size

    fun update(nueva: List<Sesion>) {
        lista = nueva
        notifyDataSetChanged()
    }
}