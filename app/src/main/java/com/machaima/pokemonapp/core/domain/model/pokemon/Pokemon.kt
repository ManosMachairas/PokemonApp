package com.machaima.pokemonapp.core.domain.model.pokemon

import com.machaima.pokemonapp.core.domain.model.ResponseData
import kotlinx.serialization.Serializable

/**
 * Data of the pokemon to be used by the view models
 */
@Serializable
data class Pokemon(
    override val id: Int,
    val name: String,
    val stats: List<PokemonStat>,
    val photoPath: String,
    val description: String,
    val types: List<String>,
    val weight: Int,
    val height: Int
) : ResponseData