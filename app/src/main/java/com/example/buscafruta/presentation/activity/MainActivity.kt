package com.example.buscafruta.presentation.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.BuscaFruta.R
import com.example.BuscaFruta.databinding.ActivityMainBinding
import com.example.buscafruta.presentation.fragment.InfoBottomSheet
import com.example.buscafruta.presentation.fragment.TechBottomSheet
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
                        val fruitIcon = getFruitIconResource(fruitName)
                        showNotification(fruitName, fruitIcon)
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

    private fun getFruitIconResource(fruitName: String): Int = when (fruitName) {
        "Amora" -> R.drawable.ic_amora
        "Manga" -> R.drawable.ic_manga
        "Pitanga" -> R.drawable.ic_pitanga
        "Romã" -> R.drawable.ic_roma
        "Abacate" -> R.drawable.ic_abacate
        "Jabuticaba" -> R.drawable.ic_jabuticaba
        else -> R.drawable.ic_frutas
    }

    private fun showNotification(fruitName: String, fruitIcon: Int) {
        val channelId = "fruit_notification_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Cria o canal de notificação se o Android for Oreo ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificações de Frutas",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val mapIntent = Intent(this, MapsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fruitName", fruitName)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            mapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val notificationTitle = "Hmm, tem $fruitName perto de você!"
        val notificationDescription = "Clique para ver no mapa."

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(fruitIcon)
            .setContentTitle(notificationTitle)
            .setContentText(notificationDescription)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(fruitName.hashCode(), notification)
    }
}
