package com.machaima.pokemonapp.core.domain.service.cache

import com.machaima.pokemonapp.core.domain.`object`.pokemon.Pokemon

interface CachedRepository {
    fun getPokemon(): List<Pokemon>
    fun setList(list: List<Pokemon>)
    fun resetOffset()
    fun resetList()
}