package com.example.buscafruta.presentation.geofencing

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        if (action == Intent.ACTION_BOOT_COMPLETED || action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            if (hasLocationPermission(context)) {
                addGeofences(context)
            } else {
                Log.w("BootReceiver", "Location permission not granted")
            }
        }
    }

    private fun hasLocationPermission(context: Context): Boolean {
        val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val background = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        return fine == PackageManager.PERMISSION_GRANTED && background == PackageManager.PERMISSION_GRANTED
    }

    private fun addGeofences(context: Context) {
        val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)

        val mockFruitLocations: Map<String, List<LatLng>> = mapOf(
            "Amora" to listOf(LatLng(-19.91718, -43.97125)),
            "Manga" to listOf(LatLng(-19.92000, -43.97000)),
            "Pitanga" to listOf(LatLng(-19.91500, -43.97200)),
            "Romã" to listOf(LatLng(-19.91800, -43.96900)),
            "Abacate" to listOf(LatLng(-19.91900, -43.97300)),
            "Jabuticaba" to listOf(LatLng(-19.91600, -43.97400))
        )

        val geofenceList = mutableListOf<Geofence>()
        for ((fruitName, locations) in mockFruitLocations) {
            for (location in locations) {
                geofenceList.add(
                    Geofence.Builder()
                        .setRequestId(fruitName)
                        .setCircularRegion(location.latitude, location.longitude, 3000f)
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build()
                )
            }
        }

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(geofenceList)
            .build()

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, GeofenceBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener {
                    Log.d("BootReceiver", "Geofences registered on boot")
                }
                .addOnFailureListener { e ->
                    Log.e("BootReceiver", "Failed to register geofences on boot", e)
                }
        } catch (e: SecurityException) {
            Log.e("BootReceiver", "SecurityException: ${e.message}")
        }
    }
}
