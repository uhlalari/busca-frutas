package com.example.buscafruta.domain.usecase

import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.domain.repository.FruitTreeRepository

class GetFruitTreesUseCase(private val repository: FruitTreeRepository) {

    suspend fun getAllFruitTrees(): List<FruitTree> {
        return repository.getAllFruitTrees()
    }

    suspend fun filterFruitTreesByName(fruitName: String): List<FruitTree> {
        return repository.getAllFruitTrees().filter { it.nome.equals(fruitName, ignoreCase = true) }
    }
}
