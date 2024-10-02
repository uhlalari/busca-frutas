package com.example.frutawatch.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.frutawatch.NotificationHelperWatch
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return

        if (geofencingEvent.hasError()) {
            Log.e("GeofenceBroadcastReceiver", "Geofence error: ${geofencingEvent.errorCode}")
            return
        }

        // Verifica o tipo de transição do Geofence
        val geofenceTransition = geofencingEvent.geofenceTransition

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // Notifica quando o usuário entra no raio de uma árvore frutífera
            val triggeringGeofences = geofencingEvent.triggeringGeofences

            // Verifica se a lista de geofences acionados não é nula
            if (!triggeringGeofences.isNullOrEmpty()) {
                for (geofence in triggeringGeofences) {
                    // Enviar notificação para a árvore correspondente
                    NotificationHelperWatch(context).triggerNotificationByGeofence(geofence.requestId)
                }
            }
        }
    }
}
