package com.machaima.pokemonapp.navigation

object ScreenEndpoints {
    const val POKEMON_SEARCH_LIST_SCREEN_ENDPOINT = "pokemon_search_list"
    const val POKEMON_JSON_ARGUMENT = "pokemonJson"
    const val POKEMON_DETAILS_SCREEN = "pokemon_details"
    const val POKEMON_DETAILS_SCREEN_ENDPOINT = "pokemon_details/{${POKEMON_JSON_ARGUMENT}}"
}