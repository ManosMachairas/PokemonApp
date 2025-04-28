package com.machaima.pokemonapp.usecase.detailsscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.machaima.pokemonapp.R
import com.machaima.pokemonapp.ui.theme.Dimens
import com.machaima.pokemonapp.usecase.detailsscreen.viewmodel.PokemonDetailsScreenViewModel
import com.machaima.pokemonapp.util.CM
import com.machaima.pokemonapp.util.EMPTY
import com.machaima.pokemonapp.util.KG
import com.machaima.pokemonapp.util.POKEMON_CARD_IMAGE_DESCRIPTION
import com.machaima.pokemonapp.util.SLASH_WITH_SPACES

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    viewModel: PokemonDetailsScreenViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()

    viewModel.pokemon?.let { pokemon ->

        Scaffold(
            topBar = {
                DetailsScreenAppBar(title = pokemon.name) {
                    navController.popBackStack()
                }
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Dimens.lazyColumnPadding)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(Dimens.lazyColumnVerticalArrangement)
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(pokemon.photoPath)
                                .placeholder(R.drawable.pokeball)
                                .error(R.drawable.ditto)
                                .build()
                        ),
                        contentDescription = POKEMON_CARD_IMAGE_DESCRIPTION,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(Dimens.detailsImageSize)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }

                InformationRow(stringResource(id = R.string.type_label), pokemon.types.joinToString(SLASH_WITH_SPACES))

                InformationRow(stringResource(id = R.string.weight_label), "${pokemon.weight} $KG")

                InformationRow(stringResource(id = R.string.height_label), "${pokemon.height} $CM")

                BaseStatsTable(baseStats = pokemon.stats)

                InformationRow(stringResource(id = R.string.description_label), pokemon.description)

            }
        }
    } ?: run {
        Scaffold(
            topBar = {
                DetailsScreenAppBar(title = EMPTY) {
                    navController.popBackStack()
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.details_screen_error))
            }
        }
    }
}