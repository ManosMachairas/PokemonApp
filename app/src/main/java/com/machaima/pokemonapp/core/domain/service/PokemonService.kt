package com.machaima.pokemonapp.core.domain.service

import com.machaima.pokemonapp.core.domain.model.DomainResponse

interface PokemonService {
    suspend fun getPokemon(name: String, type: String, isInitialCall: Boolean) : DomainResponse
}