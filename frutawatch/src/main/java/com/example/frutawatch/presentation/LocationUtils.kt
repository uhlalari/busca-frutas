package com.example.frutawatch.utils

import android.location.Location
import com.example.frutawatch.model.FruitTree
import com.example.frutawatch.model.MockFruitTrees
import com.google.android.gms.maps.model.LatLng

object LocationUtils {
    fun getNearbyFruits(location: Location, maxDistance: Float = 1000f): List<FruitTree> {
        return MockFruitTrees.allFruitTrees.filter { fruit ->
            fruit.localizacoes.any { fruitLocation ->
                val fruitTreeLocation = Location("").apply {
                    latitude = fruitLocation.latitude
                    longitude = fruitLocation.longitude
                }
                location.distanceTo(fruitTreeLocation) <= maxDistance
            }
        }
    }

    fun getClosestFruit(location: Location): FruitTree? {
        return getNearbyFruits(location).minByOrNull { fruit ->
            val fruitLocation = fruit.localizacoes.first()
            val fruitTreeLocation = Location("").apply {
                latitude = fruitLocation.latitude
                longitude = fruitLocation.longitude
            }
            location.distanceTo(fruitTreeLocation)
        }
    }
}
