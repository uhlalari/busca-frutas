package com.example.buscafruta.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.BuscaFruta.databinding.ActivityMainBinding
import com.example.buscafruta.presentation.fragment.InfoBottomSheet
import com.example.buscafruta.presentation.fragment.TechBottomSheet
import com.example.buscafruta.presentation.viewmodel.FruitTreeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: FruitTreeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.cardInfo.setOnClickListener {
            showInfoBottomSheet()
        }

        binding.cardFruits.setOnClickListener {
            startActivity(Intent(this, FruitSelectionActivity::class.java))
        }

        binding.cardTech.setOnClickListener {
            showTechBottomSheet()
        }
    }

    private fun showInfoBottomSheet() {
        InfoBottomSheet().show(supportFragmentManager, null)
    }

    private fun showTechBottomSheet() {
        TechBottomSheet().show(supportFragmentManager, null)
    }
}
