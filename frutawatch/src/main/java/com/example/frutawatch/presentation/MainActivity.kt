package com.example.frutawatch.presentation

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.frutawatch.R
import com.example.frutawatch.model.FruitTree
import com.example.frutawatch.model.MockFruitTrees
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var notificationHelper: NotificationHelperWatch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        notificationHelper = NotificationHelperWatch(this)

        checkLocationPermissionAndProximity()
        createNotificationChannel()
    }

    private fun checkLocationPermissionAndProximity() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val nearbyFruits = getNearbyFruits(it)

                val closestFruit = nearbyFruits.minByOrNull { fruit ->
                    val fruitLocation = fruit.localizacoes.first()
                    val fruitLatLng = LatLng(fruitLocation.latitude, fruitLocation.longitude)
                    distanceBetween(
                        it.latitude,
                        it.longitude,
                        fruitLatLng.latitude,
                        fruitLatLng.longitude
                    )
                }

                closestFruit?.let { fruit ->
                    notificationHelper.triggerNotification(fruit)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "fruits_channel",
                "Fruits Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getNearbyFruits(location: Location): List<FruitTree> {
        return MockFruitTrees.allFruitTrees.filter { fruit ->
            fruit.localizacoes.any { fruitLocation ->
                val fruitLatLng = LatLng(fruitLocation.latitude, fruitLocation.longitude)
                distanceBetween(
                    location.latitude,
                    location.longitude,
                    fruitLatLng.latitude,
                    fruitLatLng.longitude
                ) <= 1000
            }
        }
    }

    private fun distanceBetween(
        startLat: Double, startLng: Double, endLat: Double, endLng: Double
    ): Float {
        val results = FloatArray(1)
        Location.distanceBetween(startLat, startLng, endLat, endLng, results)
        return results[0]
    }

    fun openMapActivity(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}
