package com.stackmobile.samirastudio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samirastudio.R
import com.example.samirastudio.databinding.ServicosItemBinding
import com.example.samirastudio.model.Servicos

class ServicosAdapter(private val context: Context, private val listaServicos: MutableList<Servicos>) :
    RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicosViewHolder {
        val itemBinding = ServicosItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ServicosViewHolder(itemBinding)
    }

    override fun getItemCount() = listaServicos.size

    override fun onBindViewHolder(holder: ServicosViewHolder, position: Int) {
        holder.bind(listaServicos[position])
    }

    inner class ServicosViewHolder(private val binding: ServicosItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgServico = binding.imgServico
        val txtServico = binding.textServico

        fun bind(servico: Servicos) {
            imgServico.setImageResource(servico.img ?: R.drawable.placeholder)
            // Use um recurso padrão caso img seja nula
            txtServico.text = servico.nome
        }
    }
}
