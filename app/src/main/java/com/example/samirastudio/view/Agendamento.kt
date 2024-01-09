package com.example.samirastudio.view

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.samirastudio.databinding.ActivityAgendamentoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Agendamento : AppCompatActivity() {

    private lateinit var binding: ActivityAgendamentoBinding
    private val calendar: Calendar = Calendar.getInstance()
    private var data: String = ""
    private var hora: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val nome = intent.extras?.getString("nome").toString()

        val datePicker = binding.datePicker
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(year, monthOfYear, dayOfMonth)
            }
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            data = dateFormat.format(selectedCalendar.time)
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val minuto: String = if (minute < 10) "0$minute" else minute.toString()
            hora = "$hourOfDay:$minuto"
        }
        binding.timePicker.setIs24HourView(true) // 24-hour format

        binding.btAgendar.setOnClickListener {
            val selectedService = when {
                binding.servico1.isChecked -> "Cílios"
                binding.servico2.isChecked -> "Sobrancelha"
                binding.servico3.isChecked -> "Limpeza de pele"
                binding.servico4.isChecked -> "Hydra gloss"
                else -> ""
            }

            when {
                hora.isEmpty() -> mensagem(binding.root, "Preencha o horário!", "#FF0000")
                data.isEmpty() -> mensagem(binding.root, "Coloque uma data!", "#FF0000")
                selectedService.isEmpty() -> mensagem(binding.root, "Escolha um serviço", "#FF0000")
                !isWithinWorkingHours(hora) -> mensagem(
                    binding.root,
                    "Samira Studio está fechado - horário de atendimento das 08:00 às 19:00!",
                    "#FF0000"
                )
                else -> salvarAgendamento(nome, selectedService, data, hora)
            }
        }
    }

    private fun mensagem(view: View, mensagem: String, cor: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun isWithinWorkingHours(hora: String): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val horaInicio = sdf.parse("08:00")
        val horaFim = sdf.parse("19:00")
        val horaSelecionada = sdf.parse(hora)

        return horaSelecionada in horaInicio..horaFim
    }

    private fun salvarAgendamento(cliente: String, servicos: String, data: String, hora: String) {
        val db = FirebaseFirestore.getInstance()

        val dadosUsuario = hashMapOf(
            "Cliente" to cliente,
            "servicos" to servicos,
            "data" to data,
            "hora" to hora
        )

        db.collection("agendamento").document(cliente).set(dadosUsuario)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mensagem(binding.root, "Agendamento realizado com sucesso!", "#FF03DAC5")
                    val intent = Intent(this@Agendamento, Home::class.java)
                    intent.putExtra("nome", cliente)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    mensagem(binding.root, "Erro ao realizar o agendamento.", "#FF0000")
                }
            }
    }
}






