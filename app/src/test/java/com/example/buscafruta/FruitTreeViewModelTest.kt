package com.example.buscafruta

import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.domain.model.MockFruitTrees
import com.example.buscafruta.presentation.viewmodel.FruitTreeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FruitTreeViewModelTest {

    private lateinit var viewModel: FruitTreeViewModel

    @Before
    fun setUp() {
        viewModel = FruitTreeViewModel()
    }

    @Test
    fun `given FruitTreeViewModel, when initialized, then assert initial state is all fruits`() = runTest {
        val emittedStates = mutableListOf<List<FruitTree>>()

        // Coletando estados emitidos
        val job = launch {
            viewModel.fruitTrees.collect { emittedStates.add(it) }
        }

        // Aguarda a execução completa
        advanceUntilIdle()

        // Finaliza o job de coleta
        job.cancel()

        // Verifica se o estado inicial é igual à lista de todas as frutas
        assertEquals(MockFruitTrees.allFruitTrees, emittedStates.first())
    }

    @Test
    fun `given FruitTreeViewModel, when filterFruitTree is called with valid fruit name, then assert correct filtered state`() = runTest {
        val emittedStates = mutableListOf<List<FruitTree>>()

        // Coletando estados emitidos
        val job = launch {
            viewModel.fruitTrees.collect { emittedStates.add(it) }
        }

        // Filtra por "Manga"
        viewModel.filterFruitTree("Manga")

        // Aguarda a execução completa após a filtragem
        advanceUntilIdle()

        // Finaliza o job de coleta
        job.cancel()

        // Verifica se a filtragem foi realizada corretamente
        assertEquals(listOf(MockFruitTrees.manga), emittedStates.last())
    }

    @Test
    fun `given FruitTreeViewModel, when filterFruitTree is called with invalid fruit name, then assert empty list state`() = runTest {
        val emittedStates = mutableListOf<List<FruitTree>>()

        // Coletando estados emitidos
        val job = launch {
            viewModel.fruitTrees.collect { emittedStates.add(it) }
        }

        // Filtra por um nome inválido
        viewModel.filterFruitTree("FrutaInvalida")

        // Aguarda a execução completa após a filtragem
        advanceUntilIdle()

        // Finaliza o job de coleta
        job.cancel()

        // Verifica se a lista está vazia após a filtragem
        assertEquals(emptyList<FruitTree>(), emittedStates.last())
    }
}
