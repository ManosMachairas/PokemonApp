package com.machaima.pokemonapp.core.domain.model.pokemon

import kotlinx.serialization.Serializable

@Serializable
data class PokemonStat(
    val name: String,
    val value: Int
)