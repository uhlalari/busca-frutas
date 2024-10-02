package com.example.buscafruta.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.domain.model.MockFruitTrees
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class FruitTreeViewModel : ViewModel() {

    // Estado inicial contendo todas as frutas
    private val _fruitTrees = MutableStateFlow(MockFruitTrees.allFruitTrees)
    val fruitTrees: StateFlow<List<FruitTree>> = _fruitTrees

    // Função para filtrar as frutas com base no nome da fruta e retornar um fluxo (Flow)
    fun getFilteredFruitTrees(filter: String): Flow<List<FruitTree>> {
        return flow {
            val filteredFruits = if (filter.equals("all", ignoreCase = true)) {
                _fruitTrees.value
            } else {
                _fruitTrees.value.filter { it.nome.equals(filter, ignoreCase = true) }
            }
            emit(filteredFruits)
        }
    }

    // Função para alterar o estado interno da lista de frutas filtrando-as por nome
    fun filterFruitTree(fruitName: String) {
        _fruitTrees.value = if (fruitName.equals("all", ignoreCase = true)) {
            MockFruitTrees.allFruitTrees
        } else {
            MockFruitTrees.allFruitTrees.filter { it.nome.equals(fruitName, ignoreCase = true) }
        }
    }

    // Função para resetar o estado da lista de frutas para a lista completa
    fun resetFruitTrees() {
        _fruitTrees.value = MockFruitTrees.allFruitTrees
    }

    // Função para buscar uma fruta específica pelo nome
    fun getFruitByName(fruitName: String): FruitTree? {
        return MockFruitTrees.allFruitTrees.find { it.nome.equals(fruitName, ignoreCase = true) }
    }

    // Função para buscar frutas próximas, dado a localização do usuário
    fun getNearbyFruit(userLocation: LatLng, radius: Double = 1500.0): List<FruitTree> {
        return MockFruitTrees.allFruitTrees.filter { fruitTree ->
            fruitTree.localizacoes.any { location ->
                distanceBetween(userLocation, location) <= radius
            }
        }
    }

    // Função para calcular a distância entre duas coordenadas
    private fun distanceBetween(latLng1: LatLng, latLng2: LatLng): Double {
        val results = FloatArray(1)
        Location.distanceBetween(latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results)
        return results[0].toDouble()
    }
}
