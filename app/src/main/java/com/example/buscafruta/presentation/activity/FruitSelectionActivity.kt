package com.example.buscafruta.presentation.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.BuscaFruta.R
import com.example.BuscaFruta.databinding.ActivityFruitSelectionBinding
import com.example.BuscaFruta.databinding.BottomSheetFruitDetailsBinding
import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.domain.model.MockFruitTrees
import com.example.buscafruta.presentation.viewmodel.FruitTreeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class FruitSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitSelectionBinding
    private val viewModel: FruitTreeViewModel by viewModels()
    private val notificationReceiver = NotificationReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        registerReceiver(notificationReceiver, IntentFilter("com.example.buscafruta.NOTIFICATION"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(notificationReceiver)
    }

    private fun setupListeners() {
        binding.buttonBuscarTodas.setOnClickListener {
            navigateToMap("all")
        }

        mapOf(
            binding.iconAmora to MockFruitTrees.amora,
            binding.iconManga to MockFruitTrees.manga,
            binding.iconPitanga to MockFruitTrees.pitanga,
            binding.iconRoma to MockFruitTrees.roma,
            binding.iconAbacate to MockFruitTrees.abacate,
            binding.iconJabuticaba to MockFruitTrees.jabuticaba
        ).forEach { (iconView, fruit) ->
            iconView.setOnClickListener { showFruitDetails(fruit) }
        }
    }

    private fun showFruitDetails(fruit: FruitTree) {
        val bottomSheetBinding = BottomSheetFruitDetailsBinding.inflate(LayoutInflater.from(this))
        BottomSheetDialog(this).apply {
            bottomSheetBinding.apply {
                fruitName.text = fruit.nome
                fruitDescription.text = fruit.descricao
                fruitSeason.text = fruit.epocaDoAno.joinToString(", ")
                fruitIcon.setImageResource(getFruitIconResource(fruit.nome))

                buttonGoToMap.setOnClickListener {
                    navigateToMap(fruit.nome)
                    dismiss()
                }
            }
            setContentView(bottomSheetBinding.root)
            show()
        }
    }

    private fun navigateToMap(fruitName: String) {
        startActivity(
            Intent(this, MapsActivity::class.java).apply {
                putExtra("filterFruit", fruitName)
            }
        )
    }

    private fun getFruitIconResource(fruitName: String): Int = when (fruitName) {
        "Amora" -> R.drawable.ic_amora
        "Manga" -> R.drawable.ic_manga
        "Pitanga" -> R.drawable.ic_pitanga
        "Romã" -> R.drawable.ic_roma
        "Abacate" -> R.drawable.ic_abacate
        "Jabuticaba" -> R.drawable.ic_jabuticaba
        else -> R.drawable.capa
    }

    private inner class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getStringExtra("message")?.let { message ->
                showNotification(context, message)
            }
        }

        private fun showNotification(context: Context?, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
