package com.machaima.pokemonapp.core.domain.service.cache

import com.machaima.pokemonapp.core.domain.model.pokemon.Pokemon

interface CachedRepository {
    fun getPokemon(): List<Pokemon>
    fun setList(list: MutableList<Pokemon>)
    fun resetOffset()
    fun resetList()
    fun hasMoreResults(): Boolean
}