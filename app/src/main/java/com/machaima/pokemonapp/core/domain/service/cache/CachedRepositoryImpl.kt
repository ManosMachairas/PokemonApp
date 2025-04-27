package com.machaima.pokemonapp.core.domain.service.cache

import com.machaima.pokemonapp.core.domain.model.pokemon.Pokemon

/**
 * This is repository to hold the data fetched from each pokemon graphQL query and pass it paginated
 * to the view model of the search screen
 * Normally this is not a good practice to hold the data in an Array list. We should make calls with
 * limit and offset (see PokemonQueryWithPagination.graphql file), but graphQL in pokeAPI is
 * in beta version and there is a limit in the calls the user can make per hour. So for each user
 * input we make one call and keep the data here.
 */
class CachedRepositoryImpl : CachedRepository {

    private var pokemonList = mutableListOf<Pokemon>()
    private var offset: Int = 0

    override fun getPokemon(): List<Pokemon> {
        val subList = pokemonList.subList(
            offset,
            (offset + PAGE_LIMIT).coerceAtMost(pokemonList.size)
        )

        offset += PAGE_LIMIT

        return subList
    }

    override fun setList(list: MutableList<Pokemon>) {
        pokemonList = list
    }

    override fun resetOffset() {
        offset = 0
    }

    override fun resetList() {
        pokemonList.clear()
    }

    override fun hasMoreResults(): Boolean =
        pokemonList.size != 0 && offset < pokemonList.size

    companion object {
        const val PAGE_LIMIT = 10
    }
}