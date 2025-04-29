package com.machaima.pokemonapp.usecase.searchscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.machaima.pokemonapp.R
import com.machaima.pokemonapp.core.domain.model.pokemon.Pokemon
import com.machaima.pokemonapp.ui.theme.CeruleanBlue
import com.machaima.pokemonapp.ui.theme.Dimens
import com.machaima.pokemonapp.util.POKEMON_CARD_IMAGE_DESCRIPTION

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(CeruleanBlue)
            .padding(Dimens.pokemonCardPadding)
            .size(Dimens.pokemonCardSize)
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.photoPath)
                    .crossfade(true)
                    .placeholder(R.drawable.pokeball)
                    .error(R.drawable.pokeball)
                    .build()
            ),
            contentDescription = POKEMON_CARD_IMAGE_DESCRIPTION,
            modifier = Modifier
                .size(Dimens.pokemonCardImageSize)
                .clip(RoundedCornerShape(Dimens.pokemonCardImageRadius))
        )

        Spacer(modifier = Modifier.height(Dimens.pokemonCardImageMarginBottom))

        Text(
            text = pokemon.name,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = Dimens.pokemonCardTextSize
        )
    }
}

@Composable
fun PokemonCardPlaceholder() {
    Box(
        modifier = Modifier
            .background(Color.LightGray.copy(alpha = 0.5f))
            .padding(Dimens.pokemonCardPadding)
            .size(Dimens.pokemonCardSize)
    )
}