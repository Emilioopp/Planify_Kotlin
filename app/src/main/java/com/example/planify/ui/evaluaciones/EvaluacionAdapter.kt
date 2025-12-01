package com.example.planify.ui.evaluaciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.R
import com.example.planify.model.Evaluacion

class EvaluacionAdapter(
    private var lista: List<Evaluacion>,
    private val onClick: (Evaluacion) -> Unit
) : RecyclerView.Adapter<EvaluacionAdapter.ViewHolder>() {

    // ViewHolder
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        // Referencias a los elementos de la vista
        val tipoAsignatura: TextView = item.findViewById(R.id.tvTipoAsignaturaEval)
        val fecha: TextView = item.findViewById(R.id.tvFechaEval)
    }

    // Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_evaluacion, parent, false)
        return ViewHolder(v)
    }

    // Cantidad de elementos
    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val e = lista[position]

        // Actualizar elementos de la vista
        holder.tipoAsignatura.text = "${e.tipo} - ${e.asignatura}"
        holder.fecha.text = e.fecha

        holder.itemView.setOnClickListener { onClick(e) }
    }

    // Actualizar lista
    fun update(newList: List<Evaluacion>) {
        lista = newList
        notifyDataSetChanged()
    }
}