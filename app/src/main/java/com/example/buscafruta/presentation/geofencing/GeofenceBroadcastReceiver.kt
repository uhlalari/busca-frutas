package com.example.buscafruta.presentation.geofencing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent?.hasError() == true) {
            Log.e("GeofenceBroadcastReceiver", "Error receiving geofence event: ${geofencingEvent.errorCode}")
            return
        }

        val geofenceTransition = geofencingEvent?.geofenceTransition
        Log.d("GeofenceBroadcastReceiver", "Geofence transition detected: $geofenceTransition")

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            val triggeringGeofences = geofencingEvent.triggeringGeofences
            Log.d("GeofenceBroadcastReceiver", "Entering geofence: ${triggeringGeofences?.map { it.requestId }}")

            val triggeringLocation = geofencingEvent.triggeringLocation

            if (triggeringLocation != null) {
                triggeringGeofences?.let { geofences ->
                    for (geofence in geofences) {
                        val fruitName = geofence.requestId
                        Log.d("GeofenceBroadcastReceiver", "Entering geofence for fruit: $fruitName")

                        val geofenceLocation = Location("").apply {
                            latitude = geofence.latitude
                            longitude = geofence.longitude
                        }

                        val distance = triggeringLocation.distanceTo(geofenceLocation)

                        if (distance <= 1500) {
                            val notificationIntent = Intent(context, NotificationReceiver::class.java).apply {
                                putExtra("fruitName", fruitName)
                            }
                            context.sendBroadcast(notificationIntent)
                        } else {
                            Log.d("GeofenceBroadcastReceiver", "Distância maior que 1.5 km para $fruitName")
                        }
                    }
                } ?: Log.e("GeofenceBroadcastReceiver", "No geofences triggered")
            } else {
                Log.e("GeofenceBroadcastReceiver", "triggeringLocation é nulo")
            }
        }
    }
}
