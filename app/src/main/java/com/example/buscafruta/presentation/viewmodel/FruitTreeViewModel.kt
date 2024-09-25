package com.example.buscafruta.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.domain.model.MockFruitTrees
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FruitTreeViewModel : ViewModel() {

    private val _fruitTrees = MutableStateFlow(MockFruitTrees.allFruitTrees)
    val fruitTrees: StateFlow<List<FruitTree>> = _fruitTrees

    fun filterFruitTree(fruitName: String) {
        _fruitTrees.value = if (fruitName.equals("all", ignoreCase = true)) {
            MockFruitTrees.allFruitTrees
        } else {
            MockFruitTrees.allFruitTrees.filter { it.nome.equals(fruitName, ignoreCase = true) }
        }
    }

    fun resetFruitTrees() {
        _fruitTrees.value = MockFruitTrees.allFruitTrees
    }

    fun getFruitByName(fruitName: String): FruitTree? {
        return MockFruitTrees.allFruitTrees.find { it.nome.equals(fruitName, ignoreCase = true) }
    }

    fun getNearbyFruit(userLocation: LatLng): List<FruitTree> {
        return MockFruitTrees.allFruitTrees.filter { fruitTree ->
            fruitTree.localizacoes.any { location ->
                distanceBetween(userLocation, location) <= 1500
            }
        }
    }

    private fun distanceBetween(latLng1: LatLng, latLng2: LatLng): Double {
        val results = FloatArray(1)
        Location.distanceBetween(latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results)
        return results[0].toDouble()
    }

    suspend fun getFilteredFruitTrees(filter: String): StateFlow<List<FruitTree>> {
        return if (filter.equals("all", ignoreCase = true)) {
            fruitTrees
        } else {
            MutableStateFlow(MockFruitTrees.allFruitTrees.filter { it.nome.equals(filter, ignoreCase = true) })
        }
    }
}
