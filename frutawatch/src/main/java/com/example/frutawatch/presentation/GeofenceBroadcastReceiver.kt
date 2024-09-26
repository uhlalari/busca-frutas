package com.example.frutawatch.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import com.example.frutawatch.model.FruitTree
import com.example.frutawatch.model.MockFruitTrees
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.maps.model.LatLng

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent == null || geofencingEvent.hasError()) {
            return
        }

        val location = geofencingEvent.triggeringLocation ?: return

        val nearbyFruits = getNearbyFruits(location)

        val closestFruit = nearbyFruits.minByOrNull { fruit ->
            val fruitLocation = fruit.localizacoes.first()
            val fruitLatLng = LatLng(fruitLocation.latitude, fruitLocation.longitude)
            distanceBetween(location.latitude, location.longitude, fruitLatLng.latitude, fruitLatLng.longitude)
        }

        closestFruit?.let {
            NotificationHelperWatch(context).triggerNotification(it)
        }
    }

    private fun getNearbyFruits(location: Location): List<FruitTree> {
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
