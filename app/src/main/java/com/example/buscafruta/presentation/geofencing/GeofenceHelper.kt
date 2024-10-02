package com.example.buscafruta.presentation.geofencing

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class GeofenceHelper(private val context: Context) {

    private val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
    }

    fun addGeofences(
        fruitLocations: Map<String, List<Pair<Double, Double>>>,
        radius: Float,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onFailure("Permission denied")
            return
        }

        val geofences = fruitLocations.flatMap { (fruitName, locations) ->
            locations.map { latLng ->
                Geofence.Builder()
                    .setRequestId(fruitName)
                    .setCircularRegion(latLng.first, latLng.second, radius)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build()
            }
        }

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(geofences)
            .build()

        try {
            geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
                addOnSuccessListener {
                    Log.d("GeofenceHelper", "Geofences added successfully")
                    onSuccess()
                }
                addOnFailureListener { e ->
                    val errorMessage = e.localizedMessage ?: "Geofence addition failed"
                    Log.e("GeofenceHelper", errorMessage)
                    onFailure(errorMessage)
                }
            }
        } catch (e: SecurityException) {
            onFailure("Security exception: ${e.message}")
        }
    }
}
