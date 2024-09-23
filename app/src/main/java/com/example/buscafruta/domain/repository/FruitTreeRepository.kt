package com.example.buscafruta.domain.repository

import com.example.buscafruta.domain.model.FruitTree

interface FruitTreeRepository {
    suspend fun getAllFruitTrees(): List<FruitTree>
}
