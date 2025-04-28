package com.machaima.pokemonapp.core.domain.transformer

import com.apollographql.apollo.api.ApolloResponse
import com.machaima.pokemonapp.GetPokemonAllTypesQuery
import com.machaima.pokemonapp.core.domain.model.DomainResponse
import com.machaima.pokemonapp.core.domain.model.pokemon.Pokemon
import com.machaima.pokemonapp.core.domain.model.pokemon.PokemonStat
import com.machaima.pokemonapp.core.domain.service.cache.CachedRepository
import com.machaima.pokemonapp.util.EMPTY

object PokemonApolloNameDomainResponseTransformer {
    private const val SPRITE_ID = "front_default"

    fun transform(
        response: ApolloResponse<GetPokemonAllTypesQuery.Data>,
        cachedRepository: CachedRepository
    ): DomainResponse {
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
                // Here we would set the domainResponse list if it wasn't for the API limitation
                cachedRepository.setList(pokemonList)
            }
        }

        return domainResponse

    }

    private fun getPokemon(pokemon: GetPokemonAllTypesQuery.Pokemon_v2_pokemon): Pokemon {
        return Pokemon(
            pokemon.id,
            pokemon.name.uppercase(),
            getPokemonStats(pokemon.pokemon_v2_pokemonstats),
            getPokemonPhoto(pokemon.pokemon_v2_pokemonsprites),
            getPokemonDescription(pokemon.pokemon_v2_pokemonspecy),
            getPokemonTypes(pokemon.pokemon_v2_pokemontypes),
            pokemon.weight ?: 0,
            pokemon.height ?: 0
        )
    }

    private fun getPokemonStats(stats: List<GetPokemonAllTypesQuery.Pokemon_v2_pokemonstat>): List<PokemonStat> {
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
    private fun getPokemonPhoto(sprites: List<GetPokemonAllTypesQuery.Pokemon_v2_pokemonsprite>): String {
        val spritesHashMap = sprites[0].sprites as? LinkedHashMap<*, *>
        return spritesHashMap?.get(SPRITE_ID) as? String ?: EMPTY
    }

    private fun getPokemonDescription(specy: GetPokemonAllTypesQuery.Pokemon_v2_pokemonspecy?): String {
        return specy?.pokemon_v2_pokemonspeciesflavortexts?.get(0)?.flavor_text ?: EMPTY
    }

    private fun getPokemonTypes(types: List<GetPokemonAllTypesQuery.Pokemon_v2_pokemontype>): List<String> {
        return types.mapNotNull { it.pokemon_v2_type?.name }
            .filter { it != EMPTY }
            .toMutableList()
    }
}