package com.machaima.pokemonapp.usecase.searchscreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machaima.pokemonapp.R
import com.machaima.pokemonapp.core.domain.enumeration.PokemonType
import com.machaima.pokemonapp.core.domain.model.DomainResponse
import com.machaima.pokemonapp.core.domain.model.pokemon.Pokemon
import com.machaima.pokemonapp.core.domain.service.PokemonService
import com.machaima.pokemonapp.core.domain.service.resource.ResourceRepository
import com.machaima.pokemonapp.util.EMPTY
import com.machaima.pokemonapp.util.ON_USER_INPUT_CHANGED_CALL_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val pokemonService: PokemonService,
    private val resourceRepository: ResourceRepository
) : ViewModel() {

    var textFieldValue by mutableStateOf(EMPTY)
    var selectedType by mutableStateOf(PokemonType.ALL.type)
    var pokemonList = mutableStateListOf<Pokemon>()
    var isLoading by mutableStateOf(false)
    var showDialog by mutableStateOf(false)
    var dialogMessage by mutableStateOf(EMPTY)
    var hasMoreResultsToLoad by mutableStateOf(false)
    var animateToTop by mutableStateOf(false)

    private var hasInitiallySearchedAlready = false

    private var searchJob: Job? = null
    fun onTextChanged(newText: String) {
        textFieldValue = newText
        searchPokemon()
    }

    fun onTypeSelected(newType: String) {
        selectedType = newType
        searchPokemon()
    }

    fun searchPokemon() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(ON_USER_INPUT_CHANGED_CALL_DELAY)
            pokemonList.clear()
            fetchPokemon(true)
            hasInitiallySearchedAlready = true
        }
    }

    private suspend fun fetchPokemon(isInitialCall: Boolean = false) {
        animateToTop = false
        isLoading = true
        val response = pokemonService.getPokemon(textFieldValue, selectedType, isInitialCall)
        if (response.hasError) {
            showDialogError()
        } else {
            handleSuccessfulResponse(response, isInitialCall)
        }
        isLoading = false
    }

    fun loadMorePokemon() {
        viewModelScope.launch {
            if (!isLoading && hasMoreResultsToLoad) { // if already loading for more results don't try to fetch more
                fetchPokemon(false)
            }
        }
    }

    private fun handleSuccessfulResponse(response: DomainResponse, isInitialCall: Boolean) {
        if (isInitialCall && response.responseList.isEmpty()) {
            showDialogInfo()
            hasMoreResultsToLoad = false
        } else if (response.responseList.isEmpty()) {
            hasMoreResultsToLoad = false
        } else {
            pokemonList.addAll(response.responseList)
            hasMoreResultsToLoad = response.hasMoreResults
            if (isInitialCall) {
                animateToTop = true
            }
        }
    }

    fun initialSearchIfNeeded() {
        if (!hasInitiallySearchedAlready) {
            searchPokemon()
        }
    }

    private fun showDialogError() {
        showDialog = true
        dialogMessage = resourceRepository.getResString(R.string.generic_error)
    }

    private fun showDialogInfo() {
        showDialog = true
        dialogMessage = resourceRepository.getResString(R.string.no_results_message)
    }

}