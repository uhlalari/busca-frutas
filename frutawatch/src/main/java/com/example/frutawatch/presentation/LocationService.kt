package com.example.frutawatch.presentation

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.example.frutawatch.model.MockFruitTrees
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class LocationService : Service() {

    private lateinit var geofencingClient: GeofencingClient

    override fun onCreate() {
        super.onCreate()
        geofencingClient = LocationServices.getGeofencingClient(this)

        // Verifica se a permissão foi concedida antes de adicionar geofences
        if (hasLocationPermission()) {
            addGeofencesForFruitTrees()
        }
    }

    private fun hasLocationPermission(): Boolean {
        // Verifica se as permissões necessárias foram concedidas
        val fineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val backgroundLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        return fineLocationPermission == PackageManager.PERMISSION_GRANTED && backgroundLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun addGeofencesForFruitTrees() {
        val geofenceList = mutableListOf<Geofence>()

        for (fruitTree in MockFruitTrees.allFruitTrees) {
            for (location in fruitTree.localizacoes) {
                geofenceList.add(
                    Geofence.Builder()
                        .setRequestId(fruitTree.nome) // O ID pode ser o nome da árvore
                        .setCircularRegion(
                            location.latitude,
                            location.longitude,
                            1000f  // Raio de 1km
                        )
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)  // O geofence não expira
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build()
                )
            }
        }

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(geofenceList)
            .build()

        // PendingIntent para o BroadcastReceiver
        val geofencePendingIntent = PendingIntent.getBroadcast(
            this, 0, Intent(this, GeofenceBroadcastReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )

        try {
            // Adiciona os geofences se as permissões foram concedidas
            geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)?.run {
                addOnSuccessListener {
                    // Geofences adicionados com sucesso
                }
                addOnFailureListener {
                    // Falha ao adicionar geofences
                }
            }
        } catch (e: SecurityException) {
            // Tratamento do caso em que as permissões não foram concedidas
            e.printStackTrace()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
