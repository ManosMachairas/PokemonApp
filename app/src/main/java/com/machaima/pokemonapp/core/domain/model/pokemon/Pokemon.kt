package com.machaima.pokemonapp.core.domain.model.pokemon

import com.machaima.pokemonapp.core.domain.model.ResponseData

/**
 * Data of the pokemon to be used by the view models
 */
data class Pokemon(
    override val id: Int,
    val name: String,
    val stats: List<PokemonStat>,
    val photoPath: String
) : ResponseData