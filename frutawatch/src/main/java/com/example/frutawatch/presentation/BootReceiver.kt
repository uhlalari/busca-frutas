package com.example.frutawatch.presentation

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.frutawatch.model.MockFruitTrees
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        if (action == Intent.ACTION_BOOT_COMPLETED || action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            if (hasLocationPermission(context)) {
                addGeofences(context)
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

        val geofenceList = mutableListOf<Geofence>()
        for (fruitTree in MockFruitTrees.allFruitTrees) {
            for (location in fruitTree.localizacoes) {
                geofenceList.add(
                    Geofence.Builder()
                        .setRequestId(fruitTree.nome)
                        .setCircularRegion(location.latitude, location.longitude, 1000f)
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
        } catch (_: SecurityException) {
        }
    }
}
