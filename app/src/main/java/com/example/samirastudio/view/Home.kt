package com.example.samirastudio.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samirastudio.R
import com.example.samirastudio.databinding.ActivityHomeBinding
import com.example.samirastudio.model.Servicos
import com.stackmobile.samirastudio.adapter.ServicosAdapter

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var servicosAdapter: ServicosAdapter // Declarando o adapter
    private lateinit var recyclerViewServicos: RecyclerView // Declarando a RecyclerView

    private val listaServicos: MutableList<Servicos> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val nome = intent.extras?.getString("nome")

        binding.txtNomeUsuario.text = "Bem-Vindo, $nome"
        recyclerViewServicos = binding.recyclerViewServicos // Inicializando a RecyclerView

        recyclerViewServicos.layoutManager = GridLayoutManager(this, 2)

        // Inicializando o adaptador com a lista de serviços
        servicosAdapter = ServicosAdapter(this, listaServicos)
        recyclerViewServicos.adapter = servicosAdapter

        recyclerViewServicos.adapter = servicosAdapter

        // Chama o método para preencher a lista de serviços
        getServicos()

        binding.btAgendar.setOnClickListener {
            val intent = Intent(this, Agendamento::class.java)
            intent.putExtra("nome", nome)
            startActivity(intent)
        }
    }

    private fun getServicos() {
        // Adiciona os serviços à lista
        val servico1 = Servicos(R.drawable.cilios, "CÍLIOS")
        val servico2 = Servicos(R.drawable.sombrancelha, "SOMBRANCELHA")
        val servico3 = Servicos(R.drawable.dicas, "LIMPEZA DE PELE")
        val servico4 = Servicos(R.drawable.hidragloss, "HYDRA GLOSS")

        listaServicos.add(servico1)
        listaServicos.add(servico2)
        listaServicos.add(servico3)
        listaServicos.add(servico4)

        // Notifica o adapter sobre as mudanças na lista
        servicosAdapter.notifyDataSetChanged()
    }
}
