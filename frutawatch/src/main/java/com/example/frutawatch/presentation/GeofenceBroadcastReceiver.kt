package com.example.frutawatch.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.health.connect.datatypes.ExerciseRoute
import android.location.Location
import com.example.frutawatch.model.FruitTree
import com.example.frutawatch.model.MockFruitTrees
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.maps.model.LatLng

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            return
        }

        val location = geofencingEvent.triggeringLocation
        val nearbyFruits = getNearbyFruits(location)

        // Ordena as frutas pela distância e pega a mais próxima
        val closestFruit = nearbyFruits.minByOrNull { fruit ->
            val fruitLocation = fruit.localizacoes.first() // Pegue o primeiro ponto de localização da fruta
            val fruitLatLng = LatLng(fruitLocation.latitude, fruitLocation.longitude)
            distanceBetween(location.latitude, location.longitude, fruitLatLng.latitude, fruitLatLng.longitude)
        }

        // Verifica se existe uma fruta por perto
        if (closestFruit != null) {
            val notificationHelper = NotificationHelperWatch(context)
            notificationHelper.sendProximityNotification(closestFruit)
        }
    }

    private fun getNearbyFruits(location: ExerciseRoute.Location): List<FruitTree> {
        // Filtra as frutas que estão a até 1 km de distância
        return MockFruitTrees.allFruitTrees.filter { fruit ->
            fruit.localizacoes.any { fruitLocation ->
                val fruitLatLng = LatLng(fruitLocation.latitude, fruitLocation.longitude)
                distanceBetween(location.latitude, location.longitude, fruitLatLng.latitude, fruitLatLng.longitude) <= 1000 // 1 km
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
}
