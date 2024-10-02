package com.example.buscafruta

import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.domain.model.MockFruitTrees
import com.example.buscafruta.domain.repository.FruitTreeRepository
import com.example.buscafruta.presentation.viewmodel.FruitTreeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FruitTreeViewModelTest {

    private lateinit var viewModel: FruitTreeViewModel
    private lateinit var mockRepository: FruitTreeRepository

    @Before
    fun setUp() {
        // Implementa um repositório simples que usa os dados do MockFruitTrees
        mockRepository = object : FruitTreeRepository {
            override suspend fun getAllFruitTrees(): List<FruitTree> {
                return MockFruitTrees.allFruitTrees
            }

            override suspend fun getFruitByName(fruitName: String): FruitTree? {
                return MockFruitTrees.allFruitTrees.find { it.nome.equals(fruitName, ignoreCase = true) }
            }
        }

        // Inicializa a ViewModel passando o repositório mockado
        viewModel = FruitTreeViewModel(mockRepository)
    }

    @Test
    fun `given FruitTreeViewModel, when initialized, then assert initial state is all fruits`() = runTest {
        // Coleta o estado inicial da ViewModel
        val fruitTrees = viewModel.fruitTrees.first()

        // Verifica se o estado inicial é igual à lista de todas as frutas
        assertEquals(MockFruitTrees.allFruitTrees, fruitTrees)
    }

    @Test
    fun `given FruitTreeViewModel, when filterFruitTree is called with valid fruit name, then assert correct filtered state`() = runTest {
        // Filtra por "Manga"
        viewModel.filterFruitTree("Manga")

        // Coleta o estado após a filtragem
        val filteredFruitTrees = viewModel.fruitTrees.first()

        // Verifica se o estado contém apenas a fruta filtrada "Manga"
        assertEquals(listOf(MockFruitTrees.manga), filteredFruitTrees)
    }

    @Test
    fun `given FruitTreeViewModel, when filterFruitTree is called with invalid fruit name, then assert empty list state`() = runTest {
        // Filtra por um nome inválido
        viewModel.filterFruitTree("FrutaInvalida")

        // Coleta o estado após a filtragem
        val filteredFruitTrees = viewModel.fruitTrees.first()

        // Verifica se a lista está vazia após a filtragem com um nome inválido
        assertEquals(emptyList<FruitTree>(), filteredFruitTrees)
    }
}
