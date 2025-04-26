package com.machaima.pokemonapp.core.domain.transformer

import com.apollographql.apollo.api.ApolloResponse
import com.machaima.pokemonapp.GetPokemonQuery
import com.machaima.pokemonapp.core.domain.`object`.DomainResponse
import com.machaima.pokemonapp.core.domain.`object`.pokemon.Pokemon
import com.machaima.pokemonapp.core.domain.`object`.pokemon.PokemonStat
import com.machaima.pokemonapp.util.EMPTY

/**
 * Transformer used in pokemon call to transform the graphQL Apollo response into DomainResponse
 */
object PokemonApolloDomainResponseTransformer {

    private const val SPRITE_ID = "front_default"

    fun transform(response: ApolloResponse<GetPokemonQuery.Data>): DomainResponse {
        val domainResponse = DomainResponse()

        if (!response.errors.isNullOrEmpty() || response.exception != null) {
            domainResponse.hasError = true
        } else {

            response.data?.let {
                val pokemonResponseList = response.data?.pokemon_v2_pokemon.orEmpty()

                val pokemonList = mutableListOf<Pokemon>()
                pokemonResponseList.forEach { pokemon ->
                    pokemonList.add(
                        getPokemon(pokemon)
                    )
                }
            }
        }

        return domainResponse

    }

    private fun getPokemon(pokemon: GetPokemonQuery.Pokemon_v2_pokemon): Pokemon {
        return Pokemon(
            pokemon.id,
            pokemon.name,
            getPokemonStats(pokemon.pokemon_v2_pokemonstats),
            getPokemonPhoto(pokemon.pokemon_v2_pokemonsprites)
        )
    }

    private fun getPokemonStats(stats: List<GetPokemonQuery.Pokemon_v2_pokemonstat>): List<PokemonStat> {
        val statsList = arrayListOf<PokemonStat>()

        stats.forEach { stat ->
            statsList.add(
                PokemonStat(
                    stat.pokemon_v2_stat?.name ?: EMPTY,
                    stat.base_stat
                )
            )
        }
        return statsList
    }

    // This is not the ideal way to extract a photo. In a real case scenario, server would contain a photo_url variable in the response
    private fun getPokemonPhoto(sprites: List<GetPokemonQuery.Pokemon_v2_pokemonsprite>): String {
        val spritesHashMap = sprites[0].sprites as? LinkedHashMap<*, *>
        return spritesHashMap?.get(SPRITE_ID) as? String ?: EMPTY
    }
}