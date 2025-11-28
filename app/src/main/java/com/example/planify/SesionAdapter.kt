package com.example.planify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SesionAdapter(
    private var lista: List<Sesion>,
    // callback con sesion y posicion
    private val onItemClick: ((Sesion, Int) -> Unit)? = null
) : RecyclerView.Adapter<SesionAdapter.SesionViewHolder>() {

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
        holder.hora.text = sesion.hora
        holder.duracion.text = sesion.duracion

        holder.itemView.setOnClickListener {
            // pasar sesión y posición
            onItemClick?.invoke(sesion, position)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun updateData(nuevaLista: List<Sesion>) {
        this.lista = nuevaLista
        notifyDataSetChanged()
    }
}
