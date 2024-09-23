package com.example.buscafruta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.domain.model.MockFruitTrees
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FruitTreeViewModel : ViewModel() {

    private val _fruitTrees = MutableStateFlow(MockFruitTrees.allFruitTrees)
    val fruitTrees: StateFlow<List<FruitTree>> = _fruitTrees

    fun filterFruitTree(fruitName: String) {
        _fruitTrees.value = MockFruitTrees.allFruitTrees.filter { it.nome == fruitName }
    }

    fun resetFruitTrees() {
        _fruitTrees.value = MockFruitTrees.allFruitTrees
    }

    fun getFruitByName(fruitName: String): FruitTree? {
        return MockFruitTrees.allFruitTrees.find { it.nome == fruitName }
    }

    suspend fun getFilteredFruitTrees(fruitName: String): StateFlow<List<FruitTree>> {
        return if (fruitName == "all") {
            fruitTrees
        } else {
            MutableStateFlow(MockFruitTrees.allFruitTrees.filter { it.nome == fruitName })
        }
    }
}
