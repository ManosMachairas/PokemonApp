package com.machaima.pokemonapp.usecase.searchscreen.viewmodel

import android.util.Log
import com.machaima.pokemonapp.core.domain.model.DomainResponse
import com.machaima.pokemonapp.core.domain.model.pokemon.Pokemon
import com.machaima.pokemonapp.core.domain.service.PokemonServiceImpl
import com.machaima.pokemonapp.core.domain.service.resource.ResourceRepositoryImpl
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.mockStatic
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SearchScreenViewModelTest {

    @Mock
    lateinit var pokemonService: PokemonServiceImpl
    @Mock
    lateinit var resourceRepository: ResourceRepositoryImpl

    private lateinit var viewModel: SearchScreenViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = SearchScreenViewModel(pokemonService, resourceRepository)
    }

    @Before
    fun setupLogMock() {
        mockStatic(android.util.Log::class.java).use {
            whenever(Log.d(any(), any())).thenReturn(0)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when calling searchPokemon with successful result list should be updated loading should stop and showDialog should be false`() = runTest {
        val fakePokemon = Pokemon(1, "Bulbasaur", emptyList(), "", "", emptyList(), 70, 10)
        val response = DomainResponse().apply {
            hasError = false
            responseList = listOf(fakePokemon)
            hasMoreResults = true
        }

        whenever(pokemonService.getPokemon(any(), any(), eq(true))).thenReturn(response)

        viewModel.searchPokemon()
        advanceUntilIdle()

        assertEquals(1, viewModel.pokemonList.size)
        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.hasMoreResultsToLoad)
        assertFalse(viewModel.showDialog)
    }

    @Test
    fun `when calling searchPokemon with successful and no results list should not be updated and showDialog should be true`() = runTest {
        val sizeBefore = viewModel.pokemonList.size

        val response = DomainResponse().apply {
            hasError = false
            responseList = listOf<Pokemon>()
            hasMoreResults = false
        }

        whenever(pokemonService.getPokemon(any(), any(), eq(true))).thenReturn(response)
        whenever(resourceRepository.getResString(any())).thenReturn("No Pokémon found")

        viewModel.searchPokemon()
        advanceUntilIdle()

        assertEquals(sizeBefore, viewModel.pokemonList.size)
        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.showDialog)
    }

    @Test
    fun `when calling searchPokemon with unsuccessful response showDialog should be true`() = runTest {
        val response = DomainResponse().apply {
            hasError = true
            responseList = listOf<Pokemon>()
            hasMoreResults = false
        }

        whenever(pokemonService.getPokemon(any(), any(), eq(true))).thenReturn(response)
        whenever(resourceRepository.getResString(any())).thenReturn("An error occurred. Please check your internet connection and try again later.")

        viewModel.searchPokemon()
        advanceUntilIdle()

        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.showDialog)
    }

    @Test
    fun `when calling loadMorePokemon with successful response result list should be updated loading should stop and showDialog should be false`() = runTest {
        val sizeBefore = viewModel.pokemonList.size

        val fakePokemon = Pokemon(1, "Bulbasaur", emptyList(), "", "", emptyList(), 70, 10)
        val response = DomainResponse().apply {
            hasError = false
            responseList = listOf(fakePokemon)
            hasMoreResults = true
        }

        whenever(pokemonService.getPokemon(any(), any(), eq(false))).thenReturn(response)

        viewModel.hasMoreResultsToLoad = true
        viewModel.isLoading = false

        viewModel.loadMorePokemon()
        advanceUntilIdle()

        assertEquals(sizeBefore + response.responseList.size, viewModel.pokemonList.size)
        assertFalse(viewModel.isLoading)
        assertFalse(viewModel.showDialog)
    }
}
