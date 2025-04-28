package com.machaima.pokemonapp.usecase.detailsscreen.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.machaima.pokemonapp.core.domain.model.pokemon.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var pokemon by mutableStateOf<Pokemon?>(null)

    init {
        val pokemonJson = savedStateHandle.get<String>("pokemonJson")
        pokemonJson?.let { json ->
            runCatching {
                Json.decodeFromString<Pokemon>(Uri.decode(json))
            }.onSuccess {
                pokemon = it
            }
        }
    }
}