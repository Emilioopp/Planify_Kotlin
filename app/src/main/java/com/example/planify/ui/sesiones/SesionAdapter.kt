package com.example.planify.ui.sesiones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.R
import com.example.planify.model.Sesion

class SesionAdapter(
    private var lista: List<Sesion>,
    private val onItemClick: ((Sesion, Int) -> Unit)? = null
) : RecyclerView.Adapter<SesionAdapter.SesionViewHolder>() {

    // ViewHolder
    inner class SesionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombreSesionHoy)
        val hora: TextView = itemView.findViewById(R.id.tvHoraSesionHoy)
        val duracion: TextView = itemView.findViewById(R.id.tvDuracionSesionHoy)
    }

    // Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SesionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sesion_hoy, parent, false)
        return SesionViewHolder(view)
    }

    // Actualizar lista
    override fun onBindViewHolder(holder: SesionViewHolder, position: Int) {
        val sesion = lista[position]

        holder.nombre.text = sesion.nombre
        holder.hora.text = "Duración: ${sesion.duracion} min  -  ${sesion.hora} hrs"
        holder.duracion.visibility = View.GONE

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(sesion, position)
        }
    }

    // Cantidad de elementos
    override fun getItemCount(): Int = lista.size

    fun updateData(nuevaLista: List<Sesion>) { // Actualizar lista
        this.lista = nuevaLista
        notifyDataSetChanged()
    }
}