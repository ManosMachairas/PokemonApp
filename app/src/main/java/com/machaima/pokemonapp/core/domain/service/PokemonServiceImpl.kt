package com.machaima.pokemonapp.core.domain.service

import com.apollographql.apollo.ApolloClient
import com.machaima.pokemonapp.GetPokemonQuery
import com.machaima.pokemonapp.GetPokemonAllTypesQuery
import com.machaima.pokemonapp.core.domain.enumeration.PokemonType
import com.machaima.pokemonapp.core.domain.model.DomainResponse
import com.machaima.pokemonapp.core.domain.service.cache.CachedRepository
import com.machaima.pokemonapp.core.domain.transformer.PokemonApolloNameAndTypeDomainResponseTransformer
import com.machaima.pokemonapp.core.domain.transformer.PokemonApolloNameDomainResponseTransformer
import com.machaima.pokemonapp.util.CACHE_REPOSITORY_CALL_DELAY
import com.machaima.pokemonapp.util.surroundWithPercent
import kotlinx.coroutines.delay

/**
 * Service implementation that uses Apollo client for the graphQL queries
 */
class PokemonServiceImpl(
    private val client: ApolloClient,
    private val cachedRepository: CachedRepository
) : PokemonService {
    override suspend fun getPokemon(name: String, type: String, isInitialCall: Boolean): DomainResponse {

        return if (isInitialCall) {
            fetchFromAPI(name, type)
        } else {
            fetchFromRepository()
        }
    }

    private suspend fun fetchFromAPI(name: String, type: String): DomainResponse {
        cachedRepository.resetList()
        cachedRepository.resetOffset()

        val domainResponse = if (type == PokemonType.ALL.type) {
            fetchPokemonBasedOnName(name)
        } else {
            fetchPokemonBasedOnNameAndType(name, type)
        }

        return domainResponse
    }

    private suspend fun fetchPokemonBasedOnName(name: String): DomainResponse {
        val response = client.query(GetPokemonAllTypesQuery(
            name = name.surroundWithPercent()
        )).execute()

        val domainResponse = PokemonApolloNameDomainResponseTransformer.transform(response, cachedRepository)

        domainResponse.responseList = cachedRepository.getPokemon()
        domainResponse.hasMoreResults = cachedRepository.hasMoreResults()

        return domainResponse
    }

    private suspend fun fetchPokemonBasedOnNameAndType(name: String, type: String): DomainResponse {
        val response = client.query(GetPokemonQuery(
            name = name.surroundWithPercent(),
            type = type.surroundWithPercent()
        )).execute()

        val domainResponse = PokemonApolloNameAndTypeDomainResponseTransformer.transform(response, cachedRepository)
        domainResponse.responseList = cachedRepository.getPokemon()
        domainResponse.hasMoreResults = cachedRepository.hasMoreResults()

        return domainResponse
    }

    private suspend fun fetchFromRepository(): DomainResponse {
        delay(CACHE_REPOSITORY_CALL_DELAY)
        val domainResponse = DomainResponse().apply {
            responseList = cachedRepository.getPokemon()
        }

        return domainResponse
    }
}