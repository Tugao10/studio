package com.example.samirastudio

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.samirastudio.databinding.ActivityMainBinding
import com.example.samirastudio.view.Home
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btLogin.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val cpf = binding.editCPF.text.toString()

            if (validarNome(nome) && validarCPF(cpf)) {
                navegarPraHome(nome)
            }
        }

        // Adiciona um TextWatcher para verificar o CPF em tempo real
        binding.editCPF.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validarCPF(s.toString())
            }
        })
    }

    private fun validarNome(nome: String): Boolean {
        return if (nome.isEmpty()) {
            exibirMensagem(binding.editNome, "Escreva seu nome...")
            false
        } else {
            // Remove o destaque visual quando o nome estiver correto
            binding.editNome.error = null
            true
        }
    }

    private fun validarCPF(cpf: String): Boolean {
        return if (cpf.length != 11 || !cpf.matches(Regex("\\d{11}"))) {
            exibirMensagem(binding.editCPF, "CPF inválido (deve conter 11 dígitos)")
            false
        } else {
            // Remove o destaque visual quando o CPF estiver correto
            binding.editCPF.error = null
            true
        }
    }

    private fun exibirMensagem(view: View, mensagem: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        snackbar.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        snackbar.show()
    }

    private fun navegarPraHome(nome: String) {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("nome", nome)
        startActivity(intent)
    }
}
