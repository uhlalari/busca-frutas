package com.example.buscafruta.presentation.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.BuscaFruta.databinding.ActivityMainBinding
import com.example.buscafruta.presentation.fragment.InfoBottomSheet
import com.example.buscafruta.presentation.fragment.TechBottomSheet
import com.example.buscafruta.presentation.geofencing.NotificationHelper
import com.example.buscafruta.presentation.viewmodel.FruitTreeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: FruitTreeViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setupListeners()
        checkLocationPermission()
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

    private fun checkNearbyFruitsAndNotify() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)
                    val nearbyFruits = viewModel.getNearbyFruit(userLocation)

                    if (nearbyFruits.isNotEmpty()) {
                        val fruitName = nearbyFruits[0].nome
                        val fruitIcon = NotificationHelper().getFruitIconResource(fruitName)
                        NotificationHelper().showNotification(this, fruitName, fruitIcon)
                    }
                }
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
        } else {
            showLocationPermissionMessage()
            checkLocationPermission()
        }
    }

    private fun showLocationPermissionMessage() {
        Toast.makeText(this, "Para notificar sobre as frutas perto de você, precisamos da localização. Mas tudo bem, você ainda pode localizá-las manualmente no nosso mapa!", Toast.LENGTH_LONG).show()
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            checkNearbyFruitsAndNotify()
        } else {
            showLocationPermissionMessage()
        }
    }

    private fun checkLocationPermission() {
        when {
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                checkNearbyFruitsAndNotify()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}
