package com.machaima.pokemonapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.machaima.pokemonapp.usecase.detailsscreen.ui.PokemonDetailsScreen
import com.machaima.pokemonapp.usecase.searchscreen.ui.PokemonSearchScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PokemonNavGraph() {
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = ScreenEndpoints.POKEMON_SEARCH_LIST_SCREEN_ENDPOINT
    ) {
        composable(
            route = ScreenEndpoints.POKEMON_SEARCH_LIST_SCREEN_ENDPOINT,
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
            PokemonSearchScreen(navController)
        }

        composable(
            route = ScreenEndpoints.POKEMON_DETAILS_SCREEN_ENDPOINT,
            arguments = listOf(navArgument(ScreenEndpoints.POKEMON_JSON_ARGUMENT) { type = NavType.StringType}),
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {navBackStackEntry ->
            PokemonDetailsScreen(
                navController = navController
            )
        }
    }
}