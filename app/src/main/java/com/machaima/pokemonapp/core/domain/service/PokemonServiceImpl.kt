package com.machaima.pokemonapp.core.domain.service

import com.apollographql.apollo.ApolloClient
import com.machaima.pokemonapp.GetPokemonQuery
import com.machaima.pokemonapp.core.domain.`object`.DomainResponse
import com.machaima.pokemonapp.core.domain.service.cache.CachedRepository
import com.machaima.pokemonapp.core.domain.transformer.PokemonApolloDomainResponseTransformer

/**
 * Service implementation that uses Apollo client for the graphQL queries
 */
class PokemonServiceImpl(
    private val client: ApolloClient,
    private val cachedRepository: CachedRepository
) : PokemonService {
    override suspend fun getPokemon(name: String, type: String): DomainResponse {
        val response = client.query(GetPokemonQuery(
            name = name,
            type = type
        )).execute()

        return PokemonApolloDomainResponseTransformer.transform(response, cachedRepository)
    }
}