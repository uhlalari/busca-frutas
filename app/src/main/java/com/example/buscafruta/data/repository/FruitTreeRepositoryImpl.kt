package com.example.buscafruta.data.repository

import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.domain.model.MockFruitTrees
import com.example.buscafruta.domain.repository.FruitTreeRepository

class FruitTreeRepositoryImpl : FruitTreeRepository {

    override suspend fun getAllFruitTrees(): List<FruitTree> {
        return MockFruitTrees.allFruitTrees
    }

    override suspend fun getFruitByName(fruitName: String): FruitTree? {
        return MockFruitTrees.allFruitTrees.find { it.nome.equals(fruitName, ignoreCase = true) }
    }
}
