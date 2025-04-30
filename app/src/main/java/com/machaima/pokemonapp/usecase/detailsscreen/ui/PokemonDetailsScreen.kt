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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.machaima.pokemonapp.R
import com.machaima.pokemonapp.core.domain.enumeration.PokemonType
import com.machaima.pokemonapp.ui.theme.Colors
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

    val systemUiController = rememberSystemUiController()

    viewModel.pokemon?.let { pokemon ->

        val backgroundColor: Color
        val textColor: Color
        if (pokemon.types.isNotEmpty()) {
            backgroundColor = PokemonType.getBackgroundColorBasedOnType(pokemon.types[0])
            textColor = PokemonType.getTextColorBasedOnType(pokemon.types[0])
        } else {
            backgroundColor = PokemonType.getBackgroundColorBasedOnType(EMPTY)
            textColor = PokemonType.getTextColorBasedOnType(EMPTY)
        }

        systemUiController.setSystemBarsColor(
            color = backgroundColor,
            darkIcons = true
        )

        Scaffold(
            topBar = {
                DetailsScreenAppBar(
                    title = pokemon.name,
                    backgroundColor = backgroundColor,
                    textColor = textColor
                ) {
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

                InformationRow(stringResource(id = R.string.type_label), pokemon.types.joinToString(SLASH_WITH_SPACES), backgroundColor, textColor)

                InformationRow(stringResource(id = R.string.weight_label), "${pokemon.weight} $KG", backgroundColor, textColor)

                InformationRow(stringResource(id = R.string.height_label), "${pokemon.height} $CM", backgroundColor, textColor)

                BaseStatsTable(baseStats = pokemon.stats, backgroundColor, textColor)

                InformationRow(stringResource(id = R.string.description_label), pokemon.description, backgroundColor, textColor)

            }
        }
    } ?: run {
        systemUiController.setSystemBarsColor(
            color = Colors.CeruleanBlue,
            darkIcons = false
        )

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