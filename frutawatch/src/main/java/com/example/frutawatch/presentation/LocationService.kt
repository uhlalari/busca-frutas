package com.example.frutawatch.presentation

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.frutawatch.model.MockFruitTrees
import com.google.android.gms.location.*

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            stopSelf()
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation ?: return
            checkProximityToFruitTree(location)
        }
    }

    private fun checkProximityToFruitTree(currentLocation: Location) {
        for (fruitTree in MockFruitTrees.allFruitTrees) {
            for (location in fruitTree.localizacoes) {
                val fruitTreeLocation = Location("").apply {
                    latitude = location.latitude
                    longitude = location.longitude
                }
                val distance = currentLocation.distanceTo(fruitTreeLocation)

                if (distance <= 1500) {
                    NotificationHelperWatch(this@LocationService).triggerNotification(fruitTree)
                    return
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
